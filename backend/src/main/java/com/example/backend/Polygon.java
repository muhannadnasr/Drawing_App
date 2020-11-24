package com.example.backend;

import java.util.List;

public class Polygon extends Shape{
    Point startingPosition ;
    List <Point> points ;

    Polygon(Point p){
        this.startingPosition = p;
        this.points = null;
    }

    public void setStartingPosition(Point p){
        this.startingPosition = p;
    }

    public Point getStartingPosition(){
        return this.startingPosition;
    }
    
    public void setPoints(Point p){
        this.points.add(p);
    }

    public List<Point> getPoints(){
        return this.points;
    }
}
