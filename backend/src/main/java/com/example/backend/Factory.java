package com.example.backend;

import java.util.HashMap;

public class Factory {
    private HashMap<Integer, Shape> shapes = new HashMap<Integer, Shape>();

    public void createShape(String type, Integer ID, Double length1, Double length2, Point startingPoint, Point[]points){
        if(type.equalsIgnoreCase("ellipse")) shapes.put(ID, new Ellipse(ID, length1, length2, startingPoint));
        if(type.equalsIgnoreCase("rectangle")) shapes.put(ID, new Rectangle(ID, length1, length2, startingPoint));
        if(type.equalsIgnoreCase("polygon")) shapes.put(ID, new Polygon(ID, startingPoint, points));
        
    }
    public Shape getShape(Integer ID){
        return shapes.get(ID);
    }
    public void updateShape(Integer ID, Shape shape){
        shapes.put(ID, shape);
    }
}
