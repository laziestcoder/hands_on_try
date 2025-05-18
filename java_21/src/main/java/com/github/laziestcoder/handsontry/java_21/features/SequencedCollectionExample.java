package com.github.laziestcoder.handsontry.java_21.features;


import java.util.*;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class SequencedCollectionExample {
    public static void main(String[] args) {
        sequentialCollection();
    }

    private static void sequentialCollection() {
        LinkedHashSet<String> names = new LinkedHashSet<>(List.of("CustomerApp", "AgentApp", "MerchantApp"));
        System.out.println("names: " + names);
        System.out.println(names.getFirst()); // CustomerApp
        System.out.println(names.getLast());  // MerchantApp

        System.out.println("names reversed: " + names.reversed());
    }
}
