package com.example.backend;

import java.util.HashMap;

public class ShapeBuilder {
    private HashMap<Integer, Shape> shapes = new HashMap<Integer, Shape>();

    public void buildLine(int id, Point startingPoint, Point endingPoint){
        Shape newLine =  new Line(id, "line", startingPoint, endingPoint);
        updateShape(id, newLine);
    }
    public void buildShape(int id, String type,Point upperLeftCorner, double width, double height){
        Shape newShape =  new MultiPointShape(id, type,upperLeftCorner, width, height);
        updateShape(id, newShape);
    }
    public Shape getShape(int id){
        return shapes.get(id);
    }
    public void updateShape(int id, Shape shape){
        shapes.put(id, shape);
    }
    public HashMap<Integer, Shape> getHashMap(){
        return shapes;
    }
    public void setHashMap(HashMap<Integer, Shape> shapes){
        this.shapes = shapes;
    }
}
