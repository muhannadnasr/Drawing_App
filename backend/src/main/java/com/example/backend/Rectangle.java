package com.example.backend;

public class Rectangle extends Shape{
    private double height ;
    private double width ;
    private Point upperLeftCornerPoint ;

    Rectangle(Integer ID, String type, double h, double w, Point p){
        this.setType(type);
        this.setID(ID);
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
    public void changeSize(Double length1, Double length2, Point[]points){
        this.setHeight(length1);
        this.setWidth(length2);
    }
    public void changePosition(Point startingPosition, Point[]points){
        this.setUpperLeftCorner(startingPosition);
    }
}
