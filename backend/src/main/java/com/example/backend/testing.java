package com.example.backend;

public class testing {
    public static void main(String[] args) throws CloneNotSupportedException {
        Shape testUnit = new Ellipse(0, "rect", 10, 10, new Point(0, 0));
        Shape copy = testUnit.clone();
        testUnit.changeSize(5.0, 5.0, new Point[0]);
    }
}
