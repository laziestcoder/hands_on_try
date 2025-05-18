package com.github.laziestcoder.handsontry.java_21.features;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class ThreadStopAlternativeExample {
    public static void main(String[] args) throws InterruptedException {
        threadInterrupt();
    }

    private static void threadInterrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Working...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Thread interrupted and stopped.");
        });
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }
}
