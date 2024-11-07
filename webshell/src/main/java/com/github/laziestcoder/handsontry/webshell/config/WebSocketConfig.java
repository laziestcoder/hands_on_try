package com.github.laziestcoder.handsontry.webshell.config;

import com.github.laziestcoder.handsontry.webshell.component.TerminalHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author TOWFIQUL ISLAM
 * @since 6/11/24
 */


@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TerminalHandler(), "/terminal")
                .setAllowedOrigins("*");
    }
}
