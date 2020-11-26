package com.example.backend;

public class Ellipse extends Shape{
    private double radiusX ;
    private double radiusY ;
    private Point center ;

    Ellipse (Integer ID, String type, double r1 , double r2, Point c){
        this.setID(ID);
        this.setType(type);
        this.radiusX = r1;
        this.radiusY = r2;
        this.center = c;
    }   

    public void setRadiusX(double r){
        this.radiusX = r;
    }
    public double getRadiusX(){
        return this.radiusX;
    }

    public void setRadiusY(double r){
        this.radiusY = r;
    }
    public double getRadiusY(){
        return this.radiusY;
    }
    public void setCenter(Point p){
        this.center = p;
    }
    public Point getCenter(){
        return this.center;
    }
    public void changeSize(Double length1, Double length2, Point[]points){
        this.setRadiusX(length1);
        this.setRadiusY(length2);
    }
    public void changePosition(Point startingPosition, Point[]points){
        this.setCenter(startingPosition);
    }
}