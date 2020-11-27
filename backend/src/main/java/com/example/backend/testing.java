package com.example.backend;

public class testing {
    public static void main(String[] args) {
        Shape testUnit = new Rectangle(0, "rect", 10, 10, new Point(0, 0));
        testUnit.changePosition(new Point(0,0), new Point[0]);
    }
}
