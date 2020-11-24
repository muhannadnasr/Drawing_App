package com.example.backend;

public class Rectangle {
    private double height ;
    private double width ;
    private Point upperLeftCornerPoint ;

    Rectangle(double h, double width, Point p){
        this.height = h;
        this.width = w ;
        this.upperLeftCornerPoint = p;
    }

    public void setHeight(double h){
        this.height = h ;
    }

    public double getHeight(){
        return this.height;
    }

    
    public void setWidth(double w){
        this.width = w ;
    }

    public double getWidth(){
        return this.width;
    }
    public void setUpperLeftCorner(Point p){
        this.upperLeftCornerPoint = p ;
    }

    public Point getUpperLeftCorner(){
        return this.upperLeftCornerPoint;
    }
}
