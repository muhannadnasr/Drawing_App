package com.example.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class Api {
    Factory factory = new Factory();
    Controller controller = new Controller();

    // URL mappings for methods will be added later

    // Creation and undo/redo
    public Shape performUndo(){
        Shape shape = controller.performUndo();
        Integer ID = shape.getID();
        factory.updateShape(ID, shape);
        return shape;
    }
    public Shape performRedo(){
        Shape shape = controller.performRedo();
        Integer ID = shape.getID();
        factory.updateShape(ID, shape);
        return shape;
    }
    public Shape getShape(Integer ID){
        return factory.getShape(ID);
    }
    public void createShape(String type, Integer ID, Double length1, Double length2, Point startingPoint, Point[]points){
        factory.createShape(type, ID, length1, length2, startingPoint, points);
        controller.addUndo(getShape(ID)); // after adding shape, we add clone of shape to undo stack
    }
    
    // Changing features of shapes
    public void changeSize(Integer ID, Double length1, Double length2, Point[]points){
        Shape shape = getShape(ID);
        shape.changeSize(length1, length2, points);
        refreshShape(ID, shape);
    }
    public void changeAngle(Integer ID, Double angle){
        Shape shape = getShape(ID);
        shape.setAngle(angle);
        refreshShape(ID, shape);
    }
    public void changePosition(Integer ID, Point startingPosition, Point[]points){
        Shape shape = getShape(ID);
        shape.changePosition(startingPosition, points);
        refreshShape(ID, shape);
    }
    public void changeFillInColor(Integer ID, RGB color){
        Shape shape = getShape(ID);
        shape.setFillInColor(color);
        refreshShape(ID, shape);
    }
    public void changeOutlineColor(Integer ID, RGB color){
        Shape shape = getShape(ID);
        shape.setOutlineColor(color);
        refreshShape(ID, shape);   
    }
    public void changeThickness(Integer ID, Integer thickness){
        Shape shape = getShape(ID);
        shape.setThickness(thickness);
        refreshShape(ID, shape);
    }
    public void changeText(Integer ID, String text){
        Shape shape = getShape(ID);
        shape.setText(text);
        refreshShape(ID, shape);
    }
    public void changeZ(Integer ID, Integer z){
        Shape shape = getShape(ID);
        shape.setZ(z);
        refreshShape(ID, shape);
    }
    public void changeContainerAttributes(Integer ID, Point upperLeftCorner, Double height, Double width){
        Shape shape = getShape(ID);
        shape.setContainerUpperLeftCorner(upperLeftCorner);
        shape.setContainerHeight(height);
        shape.setContainerWidth(width);
        refreshShape(ID, shape);
    }

    // Doesn't need URL Mapping
    public void refreshShape(Integer ID, Shape shape){
        factory.updateShape(ID, shape); // after editing shape, we update the shape in the map   
        controller.addUndo(shape); // after editing shape, we add clone of shape to undo stack
    }

}
