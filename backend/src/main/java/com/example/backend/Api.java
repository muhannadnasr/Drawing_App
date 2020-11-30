package com.example.backend;

import java.util.HashMap;

import javax.xml.transform.TransformerException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class Api {
    ShapeBuilder shapeBuilder = new ShapeBuilder();
    Controller controller = new Controller();
    Xml xml = new Xml();

    // URL mappings for methods will be added later

    // Creation and undo/redo
    public Shape performUndo() {
        Shape shape = controller.performUndo();
        Integer ID = shape.getId();
        shapeBuilder.updateShape(ID, shape);
        return shape;
    }

    public Shape performRedo() {
        Shape shape = controller.performRedo();
        Integer ID = shape.getId();
        shapeBuilder.updateShape(ID, shape);
        return shape;
    }

    public Shape getShape(Integer ID) {
        return shapeBuilder.getShape(ID);
    }

    public void createLine(int id, Point startingPoint, Point endingPoint) {
        shapeBuilder.buildLine(id, startingPoint, endingPoint);
        controller.addUndo(getShape(id)); // after adding shape, we add clone of shape to undo stack
    }

    // Changing features of shapes
    public void changeAngle(Integer ID, Double angle) {
        Shape shape = getShape(ID);
        shape.setAngle(angle);
        refreshShape(ID, shape);
    }

    public void changeFillSpecs(int id, String fillColor, double opacity) {
        Shape shape = getShape(id);
        shape.setFillColor(fillColor);
        shape.setFillOpacity(opacity);
        refreshShape(id, shape);
    }

    public void changeThickness(Integer ID, Integer thickness) {
        Shape shape = getShape(ID);
        shape.setThickness(thickness);
        refreshShape(ID, shape);
    }

    public void changeZ(Integer ID, Integer z) {
        Shape shape = getShape(ID);
        shape.setZ(z);
        refreshShape(ID, shape);
    }

    // Change line Features
    public void changeLineStartingPoint(int id, Point startingPoint) {
        Line line = (Line) getShape(id);
        line.setStartingPoint(startingPoint);
        refreshShape(id, line);
    }

    public void changeLineEndingPoint(int id, Point endingPoint) {
        Line line = (Line) getShape(id);
        line.setEndingPoint(endingPoint);
        refreshShape(id, line);
    }

    // Change multiPointShape fearues
    public void changeUpperLeftCorner(int id, Point upperLeftCorner) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setUpperLeftCorner(upperLeftCorner);
        refreshShape(id, multiPointShape);
    }

    public void changeSize(int id, double width, double height) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setWidth(width);
        multiPointShape.setHeight(height);
        refreshShape(id, multiPointShape);
    }

    public void changeOutlineSpecs(int id, String outlineColor, double opacity) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setOutlineColor(outlineColor);
        multiPointShape.setOutlineOpacity(opacity);
        refreshShape(id, multiPointShape);
    }

    // Utilities
    public void SaveXml(String location) {
        try {
            xml.javaToXml(shapeBuilder.getHashMap(), location);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    // Doesn't need URL Mapping
    public void refreshShape(Integer ID, Shape shape){
        shapeBuilder.updateShape(ID, shape); // after editing shape, we update the shape in the map
        controller.addUndo(shape); // after editing shape, we add clone of shape to undo stack
    }

}
