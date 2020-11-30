package com.example.backend;

public class MultiPointShape extends Shape{
    private Point upperLeftCorner;
    private double width;
    private double height;
    private String outlineColor = "default_outlineColor";
    private double outlineOpacity;

    public MultiPointShape(int id, String type, Point upperLeftCorner,double width, double height) {
        super(id, type);
        this.upperLeftCorner = upperLeftCorner;
        this.width = width;
        this.height = height;
    }

    public Point getUpperLeftCorner() {
        return upperLeftCorner;
    }

    public void setUpperLeftCorner(Point upperLeftCorner) {
        this.upperLeftCorner = upperLeftCorner;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(String outlineColor) {
        this.outlineColor = outlineColor;
    }

    public double getOutlineOpacity() {
        return outlineOpacity;
    }

    public void setOutlineOpacity(double outlineOpacity) {
        this.outlineOpacity = outlineOpacity;
    }
}
