package com.example.backend;

public class Ellipse extends Shape{
    private double radiusX ;
    private double radiusY ;
    private Point center ;

    Ellipse (double r1 , double r2, Point c){
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
}