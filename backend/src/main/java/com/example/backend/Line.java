package com.example.backend;

public class Line extends Shape{
    private Point startingPoint;
    private Point endingPoint;

    public Line(int id, String type, Point startingPoint, Point endingPoint) {
        super(id, type);
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }
    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Point getEndingPoint() {
        return endingPoint;
    }
    public void setEndingPoint(Point endingPoint) {
        this.endingPoint = endingPoint;
    }
}
