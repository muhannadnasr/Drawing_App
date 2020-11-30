package com.example.backend;

import javax.xml.transform.TransformerException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class Api {
    ShapeBuilder shapeBuilder = new ShapeBuilder();
    Controller controller = new Controller();
    Xml xml = new Xml();

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

    public Shape getShape(int id) {
        return shapeBuilder.getShape(id);
    }

    @PostMapping("/createLine")
    public void createLine( @RequestParam int id, 
                            @RequestParam String startingPoint, 
                            @RequestParam String endingPoint) {

        //setting up the point object                                   
        String [] startingCoordinates = startingPoint.split(",");
        double startingX = Double.parseDouble(startingCoordinates[0]);
        double startingY = Double.parseDouble(startingCoordinates[1]);

        String [] endingCoordinates = startingPoint.split(",");
        double endingX = Double.parseDouble(endingCoordinates[0]);
        double endingY = Double.parseDouble(endingCoordinates[1]);
                  
        shapeBuilder.buildLine(id,  new Point(startingX, startingY), 
                                    new Point(endingX, endingY));
        controller.addUndo(getShape(id)); // after adding shape, we add clone of shape to undo stack
    }

    @PostMapping("/createShape")
    public void createMultiPointShape(  @RequestParam int id, @RequestParam String type, 
                                        @RequestParam String upperLeftCorner, 
                                        @RequestParam double width, @RequestParam double height) {
        
        //setting up the point object                                   
        String [] coordinates = upperLeftCorner.split(",");
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);

        shapeBuilder.buildShape(id, type, new Point(x, y), width, height);
        controller.addUndo(getShape(id));
    }
    // change shape features
    @PostMapping("/updateFillColor")
    public void changeFillColor(@RequestParam int id, @RequestParam String fillColor) {
        Shape shape = getShape(id);
        shape.setFillColor(fillColor);
        refreshShape(id, shape);
    }

    @PostMapping("/updateThickness")
    public void changeThickness(@RequestParam int id, @RequestParam Integer thickness) {
        Shape shape = getShape(id);
        shape.setThickness(thickness);
        refreshShape(id, shape);
    }
    // Change line Features
    @PostMapping("/updateLinePos")
    public void changeLinePos(@RequestParam int id, @RequestParam String startingPoint, @RequestParam String endingPoint) {
        //setting up the point object                                   
        String [] startingCoordinates = startingPoint.split(",");
        double startingX = Double.parseDouble(startingCoordinates[0]);
        double startingY = Double.parseDouble(startingCoordinates[1]);

        String [] endingCoordinates = startingPoint.split(",");
        double endingX = Double.parseDouble(endingCoordinates[0]);
        double endingY = Double.parseDouble(endingCoordinates[1]);

        Line line = (Line) getShape(id);
        line.setStartingPoint(new Point(startingX, startingY));
        line.setEndingPoint(new Point(endingX, endingY));
        refreshShape(id, line);
    }

    // Change multiPointShape fearues
    @PostMapping("updateUpperLeftCorner")
    public void changeUpperLeftCorner(int id, Point upperLeftCorner) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setUpperLeftCorner(upperLeftCorner);
        refreshShape(id, multiPointShape);
    }

    @PostMapping("/updateSize")
    public void changeSize(int id, double width, double height) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setWidth(width);
        multiPointShape.setHeight(height);
        refreshShape(id, multiPointShape);
    }

    @PostMapping("updateOutlineColor")
    public void changeOutlineColor(@RequestParam int id, @RequestParam String outlineColor) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setOutlineColor(outlineColor);
        refreshShape(id, multiPointShape);
    }

    @PostMapping("updateFillOpacity")
    public void changeFillOpacity(@RequestParam int id, @RequestParam double opacity) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setFillOpacity(opacity);
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
