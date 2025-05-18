package com.github.laziestcoder.handsontry.java_21.features;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class VirtualThreadExample {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            System.out.println(STR."=============== Test: \{i} ===============");
            virtualThread();
            Thread.sleep(1000);
            System.out.println("================");
            physicalThread();
        }

    }

    private static void virtualThread() {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> System.out.println("Running in a virtual thread: " + Thread.currentThread()));
        }
        executor.shutdown();
        long endTine = System.currentTimeMillis();
        System.out.println(STR."VT execution time: \{(endTine - startTime)} ms");
    }

    private static void physicalThread() {
        long startTime = System.currentTimeMillis();
        // Create a fixed thread pool with a limited number of physical threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> System.out.println("Running in a physical thread: " + Thread.currentThread()));
        }

        executor.shutdown();
        long endTine = System.currentTimeMillis();
        System.out.println(STR."PT execution time: \{(endTine - startTime)} ms");
    }
}
