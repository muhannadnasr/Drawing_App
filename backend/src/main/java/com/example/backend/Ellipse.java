package com.example.backend;

public class Ellipse extends Shape{
    private double radiusX ;
    private double radiusY ;
    private Point center ;

    public Ellipse (Integer ID, String type, double radiusX , double radiusY, Point center){
        super(ID, type);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.center = center;
    }

    private void setRadiusX(double r){
        this.radiusX = r;
    }
    public double getRadiusX(){
        return this.radiusX;
    }

    private void setRadiusY(double r){
        this.radiusY = r;
    }
    public double getRadiusY(){
        return this.radiusY;
    }

    private void setCenter(Point p){
        this.center = p;
    }
    private Point getCenter(){
        return this.center;
    }

    public void changeSize(Double length1, Double length2, Point[]points){
        this.setRadiusX(length1);
        this.setRadiusY(length2);
    }
    
    public void changePosition(Point startingPosition, Point[]points){
        this.setCenter(startingPosition);
    }

    public Point getStartingPosition() {
        return this.getCenter();
    }
}