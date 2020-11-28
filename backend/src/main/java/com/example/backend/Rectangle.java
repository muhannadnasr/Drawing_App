package com.example.backend;

public class Rectangle extends Shape{
    private double height ;
    private double width ;
    private Point upperLeftCornerPoint ;

    Rectangle(Integer ID, String type, double h, double w, Point upperLeftCorner){
        super(ID, type);
        this.height = h;
        this.width = w ;
        this.upperLeftCornerPoint = upperLeftCorner;
    }

    private void setHeight(double h){
        this.height = h ;
    }
    public double getHeight(){
        return this.height;
    }

    
    private void setWidth(double w){
        this.width = w ;
    }
    public double getWidth(){
        return this.width;
    }

    private void setUpperLeftCorner(Point p){
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
