package com.example.backend;

import javax.xml.transform.TransformerException;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class Api {
    ShapeBuilder shapeBuilder = new ShapeBuilder();
    Controller controller = new Controller();
    JsonConverter jsonConverter = new JsonConverter();
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

        String [] endingCoordinates = endingPoint.split(",");
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

        String [] endingCoordinates = endingPoint.split(",");
        double endingX = Double.parseDouble(endingCoordinates[0]);
        double endingY = Double.parseDouble(endingCoordinates[1]);

        Line line = (Line) getShape(id);
        line.setStartingPoint(new Point(startingX, startingY));
        line.setEndingPoint(new Point(endingX, endingY));
        refreshShape(id, line);
    }

    // Change multiPointShape fearues
    @PostMapping("/updateShapePosAndSize")
    public void changeShapePosAndSize(  @RequestParam int id, @RequestParam String upperLeftCorner,
                                        @RequestParam double width, @RequestParam double height) {
        
        //setting up the point object                                   
        String [] coordinates = upperLeftCorner.split(",");
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);

        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setUpperLeftCorner(new Point(x, y));
        multiPointShape.setWidth(width);
        multiPointShape.setHeight(height);
        refreshShape(id, multiPointShape);
    }

    @PostMapping("/updateOutlineColor")
    public void changeOutlineColor(@RequestParam int id, @RequestParam String outlineColor) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setOutlineColor(outlineColor);
        refreshShape(id, multiPointShape);
    }

    @PostMapping("/updateFillOpacity")
    public void changeFillOpacity(@RequestParam int id, @RequestParam double opacity) {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setFillOpacity(opacity);
        refreshShape(id, multiPointShape);
    }
    // Save
    public void SaveXml(String location) {
        try {
            xml.javaToXml(shapeBuilder.getHashMap(), location);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    // get data of a shape
    @GetMapping("/getShapeData")
    public String getShapeData(@RequestParam int id){
        Shape shape = getShape(id);
        return jsonConverter.ShapeToJsonString(shape);
    }

    // Doesn't need URL Mapping
    public void refreshShape(Integer ID, Shape shape){
        shapeBuilder.updateShape(ID, shape); // after editing shape, we update the shape in the map
        controller.addUndo(shape); // after editing shape, we add clone of shape to undo stack
    }

}
