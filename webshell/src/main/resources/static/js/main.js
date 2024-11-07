const term = new Terminal({
    cursorBlink: true,
    macOptionIsMeta: true,
    scrollback: 1000,
    fontSize: 14,
    fontFamily: 'Menlo, Monaco, "Courier New", monospace',
    allowProposedApi: true,  // Enable proposed API features
    windowsMode: false,      // Disable Windows-specific behaviors
    theme: {
        background: '#000000',
        foreground: '#ffffff',
        cursor: '#ffffff',
        selection: 'rgba(255, 255, 255, 0.3)',
        black: '#000000',
        red: '#e06c75',
        green: '#98c379',
        yellow: '#d19a66',
        blue: '#61afef',
        magenta: '#c678dd',
        cyan: '#56b6c2',
        white: '#abb2bf',
        brightBlack: '#5c6370',
        brightRed: '#e06c75',
        brightGreen: '#98c379',
        brightYellow: '#d19a66',
        brightBlue: '#61afef',
        brightMagenta: '#c678dd',
        brightCyan: '#56b6c2',
        brightWhite: '#ffffff'
    }
});

// Enable better key handling
Terminal.applyAddon(fit);

term.open(document.getElementById('terminal-container'));
term.fit();

// Connect to WebSocket
const ws = new WebSocket('ws://localhost:8080/terminal');

ws.onopen = () => {
    // Send initial terminal size
    const dimensions = term.proposeGeometry();
    ws.send(`RESIZE:${dimensions.cols},${dimensions.rows}`);
    term.write('\x1b[32mConnected to terminal.\x1b[0m\r\n');
};

// Handle binary data properly
ws.binaryType = 'arraybuffer';
ws.onmessage = (event) => {
    if (typeof event.data === 'string') {
        term.write(event.data);
    } else {
        // Handle binary data
        const decoder = new TextDecoder('utf-8');
        term.write(decoder.decode(event.data));
    }
};

ws.onclose = () => {
    term.write('\x1b[31m\r\nConnection closed\x1b[0m\r\n');
};

ws.onerror = (error) => {
    term.write('\x1b[31m\r\nWebSocket error occurred\x1b[0m\r\n');
};

// Handle terminal input
term.onData(data => {
    if (ws.readyState === WebSocket.OPEN) {
        // Convert special characters if needed
        let processedData = data;
        if (data === '\r') {
            processedData = '\r\n';
        }
        ws.send(processedData);
    }
});

// Handle terminal resize
term.onResize(size => {
    if (ws.readyState === WebSocket.OPEN) {
        ws.send(`RESIZE:${size.cols},${size.rows}`);
    }
});

// Handle window resize
let resizeTimeout;
window.addEventListener('resize', () => {
    clearTimeout(resizeTimeout);
    resizeTimeout = setTimeout(() => {
        term.fit();
    }, 250);
});

// Handle visibility change
document.addEventListener('visibilitychange', () => {
    if (document.visibilityState === 'visible') {
        term.fit();
    }
});

term.attachCustomKeyEventHandler((event) => {
    // Allow all keyboard shortcuts
    if (event.ctrlKey || event.metaKey || event.altKey) {
        return true;
    }

    // Handle special keys for vim
    if (event.key === 'Escape' ||
        event.key === 'Backspace' ||
        event.key === 'Delete' ||
        event.key === 'Enter' ||
        event.key === 'Tab') {
        return true;
    }

    // Handle arrow keys
    if (event.key.startsWith('Arrow')) {
        return true;
    }

    return true;
});