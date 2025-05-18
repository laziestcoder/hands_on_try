package com.github.laziestcoder.handsontry.java_21.features;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class ScopedValueExample {
    private static final ScopedValue<String> SCOPED_USER = ScopedValue.newInstance();

    public static void main(String[] args) {
        scopedValue();
    }

    private static void scopedValue() {
        ScopedValue.where(SCOPED_USER, "CAPP").run(() -> {
            System.out.println("User: " + SCOPED_USER.get());
        });
    }
}
