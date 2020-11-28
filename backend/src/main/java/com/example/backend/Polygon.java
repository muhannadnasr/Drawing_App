package com.example.backend;


public class Polygon extends Shape{
    Point startingPosition;
    Point[]points;

    Polygon(Integer ID, String type, Point startingPosition, Point[]points){
        super(ID, type);
        this.startingPosition = startingPosition;
        this.points = points;
    }

    private void setStartingPosition(Point p){
        this.startingPosition = p;
    }
    public Point getStartingPosition(){
        return this.startingPosition;
    }
    
    private void setPoints(Point[]points){
        this.points = points;
    }
    public Point[] getPoints(){
        return this.points;
    }
    
    public void changeSize(Double length1, Double length2, Point[]points){
        this.setPoints(points);
    }
    public void changePosition(Point startingPosition, Point[]points){
        this.setStartingPosition(startingPosition);
        this.setPoints(points);
    }
}
