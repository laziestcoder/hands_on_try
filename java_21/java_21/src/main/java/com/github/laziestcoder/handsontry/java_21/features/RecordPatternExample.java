package com.github.laziestcoder.handsontry.java_21.features;

/**
 * @author TOWFIQUL ISLAM
 * @since 31/12/24
 */

public class RecordPatternExample {

    record Point(int x, int y) {}
    enum Color {RED, GREEN, BLUE}
    record ColoredPoint(Point point, Color color) {}
    record RandomPoint(ColoredPoint cp) {}

    public static void main(String[] args) {
        recordPoint();
        getRandomPointColor(new RandomPoint(new ColoredPoint(new Point(10, 5), Color.RED)));
    }

    private static void recordPoint() {
        Object obj = new Point(10, 20);

        if (obj instanceof Point(int x, int y)) {
            System.out.println(STR."Point coordinates: \{x}, \{y}");
        }

        if (obj instanceof Point point) {
            System.out.println(STR."Point coordinates: x:\{point.x}, y:\{point.y()}");
        }
    }

    public static void getRandomPointColor(RandomPoint r) {
        if(r instanceof RandomPoint(ColoredPoint cp)) {
            System.out.println(STR."ColoredPoint \{cp}");
            System.out.println(STR."color \{cp.color()}");
            System.out.println(STR."point \{cp.point()}");
            System.out.println(STR."point \{cp.point}");
            System.out.println(STR."point x:\{cp.point.x()}, y:\{cp.point.y()}");
        }
        System.out.println("===================");
        if(r instanceof RandomPoint(ColoredPoint(Point p, Color c))) {
            System.out.println(STR."color \{c}");
            System.out.println(STR."point \{p}");
            System.out.println(STR."point x:\{p.x()}, y:\{p.y}");
        }

    }
}
