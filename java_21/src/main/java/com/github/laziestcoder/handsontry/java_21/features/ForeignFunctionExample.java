package com.github.laziestcoder.handsontry.java_21.features;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class ForeignFunctionExample {
    public static void main(String[] args) {
        foreignFunction();
        foreignFunctionMemoryArena();
    }

    private static void foreignFunction() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        // Write data to the buffer
        buffer.put((byte) 42);

        // Read data from the buffer
        buffer.flip(); // Switch to read mode
        System.out.println(buffer.get());
    }

    private static void foreignFunctionMemoryArena() {
        /*
        * A confined memory arena is created.
        * Native memory for an integer is allocated within the arena.
        * An integer value (123) is written to the allocated memory.
        * The value is read back from memory and printed.
        * The arena is automatically closed, releasing the memory.
        */
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(1024); // Allocate 1 KB of native memory

            // Write data to the segment
            segment.set(ValueLayout.JAVA_BYTE, 0, (byte) 42);

            // Read data from the segment
            byte value = segment.get(ValueLayout.JAVA_BYTE, 0);
            System.out.println("Value: " + value);
        }
    }
}
