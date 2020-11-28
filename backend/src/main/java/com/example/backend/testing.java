package com.example.backend;

public class testing {
    public static void main(String[] args) throws CloneNotSupportedException {
        Rectangle testUnit = new Rectangle(0, "rect", 10, 10, new Point(0, 0));
        Rectangle copy = (Rectangle) testUnit.clone();
        testUnit.changePosition(new Point(1,1), new Point[0]);
        Point corner = copy.getUpperLeftCorner();
        System.out.println(corner.getX());
        System.out.println(corner.getY());

        corner = testUnit.getUpperLeftCorner();
        System.out.println(corner.getX());
        System.out.println(corner.getY());
    }
}
