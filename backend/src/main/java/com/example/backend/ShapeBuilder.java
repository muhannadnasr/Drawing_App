package com.example.backend;

import java.util.HashMap;

public class ShapeBuilder {
    private HashMap<Integer, Shape> shapes = new HashMap<Integer, Shape>();

    public Line buildLine(int id, Point startingPoint, Point endingPoint){
        return new Line(id, "line", startingPoint, endingPoint);
    }
    public MultiPointShape buildShape(int id, String type,Point upperLeftCorner, double width, double height){
        return new MultiPointShape(id, type,upperLeftCorner, width, height);
    }
    public Shape getShape(Integer ID){
        return shapes.get(ID);
    }
    public void updateShape(Integer ID, Shape shape){
        shapes.put(ID, shape);
    }
    public HashMap<Integer, Shape> getHashMap(){
        return shapes;
    }
}
