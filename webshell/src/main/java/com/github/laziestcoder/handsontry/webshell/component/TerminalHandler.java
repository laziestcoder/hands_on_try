package com.github.laziestcoder.handsontry.webshell.component;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import com.pty4j.WinSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TOWFIQUL ISLAM
 * @since 6/11/24
 */

@Component
@Slf4j
public class TerminalHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<String, PtyProcess> processes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, PrintWriter> writers = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Start a new PTY process for this session
        String sessionId = session.getId();

        // Set environment variables
        Map<String, String> env = new HashMap<>(System.getenv());
        env.put("TERM", "terminal");

        // Create PTY process
        PtyProcessBuilder builder = new PtyProcessBuilder()
                .setCommand(new String[]{"/bin/bash", "-c", "cd ~; bash"})
                .setEnvironment(env)
                .setInitialColumns(80)
                .setInitialRows(24);

        PtyProcess process = builder.start();
        processes.put(sessionId, process);

        // Set up process writer
        writers.put(sessionId, new PrintWriter(new OutputStreamWriter(process.getOutputStream())));

        // Start reading output
        executor.submit(() -> handleProcessOutput(session, process));
    }

    private void handleProcessOutput(WebSocketSession session, PtyProcess process) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            char[] buffer = new char[1024];
            int read;

            while ((read = reader.read(buffer)) != -1) {
                if (session.isOpen()) {
                    String output = new String(buffer, 0, read);
                    session.sendMessage(new TextMessage(output));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("error : {}", e.getMessage(), e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String sessionId = session.getId();
        String payload = message.getPayload();

        // Handle special commands (resize, etc)
        if (payload.startsWith("RESIZE:")) {
            String[] dimensions = payload.substring(7).split(",");
            int cols = Integer.parseInt(dimensions[0]);
            int rows = Integer.parseInt(dimensions[1]);
            PtyProcess process = processes.get(sessionId);
            if (process != null) {
                process.setWinSize(new WinSize(cols, rows));
            }
            return;
        }

        // Handle regular input
        PrintWriter writer = writers.get(sessionId);
        if (writer != null) {
            writer.write(payload);
            writer.flush();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();

        // Clean up resources
        PtyProcess process = processes.remove(sessionId);
        if (process != null) {
            process.destroyForcibly();
        }

        PrintWriter writer = writers.remove(sessionId);
        if (writer != null) {
            writer.close();
        }
    }
}