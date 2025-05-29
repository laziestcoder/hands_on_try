package com.github.laziestcoder.handsontry.java_21.features;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class PatternMatchingSwitchExample {

    public static void main(String[] args) {
        switchExample();
    }

    private static void switchExample() {
        Object obj = 123;

        String result = switch (obj) {
            case Integer i -> "Integer: " + i;
            case String s -> "String: " + s;
            default -> "Unknown type";
        };
        System.out.println(result);
    }
}
