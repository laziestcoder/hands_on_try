package com.github.laziestcoder.handsontry.java_21.features;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class StringTemplateExample {
    public static void main(String[] args) {
        StringConCat();
    }

    // for using STR you should use --enable-preview
    private static void StringConCat() {
        String jdk17 = "JDK 17";
        String jdk21 = "JDK 21";
        String oldFeature = "Bye " + jdk17;
        String newFeature = STR."Welcome \{jdk21}";
        System.out.println(oldFeature);
        System.out.println(newFeature);
    }
}
