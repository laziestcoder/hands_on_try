package com.github.laziestcoder.handsontry.java_21.features;


import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.Arrays;


/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class VectorApiExample {
    private static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;

    public static void main(String[] args) {
        int[] v1 = {1, 3, 5, 7, 9};
        int[] v2 = {2, 4, 6, 8, 10};
        addTwoScalarArrays(v1, v2);
        addTwoVectorArrays(v1, v2);
        addTwoVectorsWithMasks(v1, v2);
    }

    private static void addTwoScalarArrays(int[] arr1, int[] arr2) {
        System.out.print("addTwoScalarArrays: ");
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i] + arr2[i];
        }
        System.out.println(Arrays.toString(result));
    }

    private static void addTwoVectorArrays(int[] arr1, int[] arr2) {
//        int vectorLength = SPECIES.length();
//        System.out.println(vectorLength);
        // vectorLength must match with arr1 and arr2 length
        System.out.print("addTwoVectorArrays: ");
        try {
            var v1 = IntVector.fromArray(SPECIES, arr1, 0);
            var v2 = IntVector.fromArray(SPECIES, arr2, 0);
            var result = v1.add(v2);
            System.out.print("addTwoVectorArrays: ");
            System.out.println(Arrays.toString(result.toArray()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addTwoVectorsWithMasks(int[] arr1, int[] arr2) {
        System.out.print("addTwoVectorsWithMasks: ");
        int[] finalResult = new int[arr1.length];
        int i = 0;
        for (; i < SPECIES.loopBound(arr1.length); i += SPECIES.length()) {
            var mask = SPECIES.indexInRange(i, arr1.length);
            var v1 = IntVector.fromArray(SPECIES, arr1, i, mask);
            var v2 = IntVector.fromArray(SPECIES, arr2, i, mask);
            var result = v1.add(v2, mask);
            result.intoArray(finalResult, i, mask);
        }

        // tail cleanup loop
        for (; i < arr1.length; i++) {
            finalResult[i] = arr1[i] + arr2[i];
        }
        System.out.println(Arrays.toString(finalResult));
    }
}
