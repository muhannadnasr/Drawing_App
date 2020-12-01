package com.example.backend;

import java.util.HashMap;

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
    public HashMap<Integer, Shape> performUndo() throws CloneNotSupportedException {
        HashMap<Integer, Shape> current = new HashMap<Integer, Shape>();
        HashMap<Integer, Shape> undoMap = performUndo();
        if(undoMap != null){
            for(HashMap.Entry<Integer, Shape> mapElement : undoMap.entrySet()){
                Integer ID = (Integer)mapElement.getKey();
                Shape shapeClone = (undoMap.get(ID)).clone();
                current.put(ID, shapeClone);
            }
        }
        shapeBuilder.setHashMap(current);
        return undoMap;
    }

    public HashMap<Integer, Shape> performRedo() throws CloneNotSupportedException {
        HashMap<Integer, Shape> current = new HashMap<Integer, Shape>();
        HashMap<Integer, Shape> redoMap = performRedo();
        if(redoMap != null){
            for(HashMap.Entry<Integer, Shape> mapElement : redoMap.entrySet()){
                Integer ID = (Integer)mapElement.getKey();
                Shape shapeClone = (redoMap.get(ID)).clone();
                current.put(ID, shapeClone);
            }
        }
        shapeBuilder.setHashMap(current);
        return redoMap;
    }


    public Shape getShape(int id) {
        return shapeBuilder.getShape(id);
    }

    @PostMapping("/createLine")
    public void createLine( @RequestParam int id, 
                            @RequestParam String startingPoint, 
                            @RequestParam String endingPoint) throws CloneNotSupportedException {

        //setting up the point object                                   
        String [] startingCoordinates = startingPoint.split(",");
        double startingX = Double.parseDouble(startingCoordinates[0]);
        double startingY = Double.parseDouble(startingCoordinates[1]);

        String [] endingCoordinates = endingPoint.split(",");
        double endingX = Double.parseDouble(endingCoordinates[0]);
        double endingY = Double.parseDouble(endingCoordinates[1]);
                  
        shapeBuilder.buildLine(id,  new Point(startingX, startingY), 
                                    new Point(endingX, endingY));
        controller.addUndo(shapeBuilder.getHashMap()); // after adding shape, we add clone of map to undo stack
    }

    @PostMapping("/createShape")
    public void createMultiPointShape(  @RequestParam int id, @RequestParam String type, 
                                        @RequestParam String upperLeftCorner, 
                                        @RequestParam double width, @RequestParam double height) throws CloneNotSupportedException {
        
        //setting up the point object                                   
        String [] coordinates = upperLeftCorner.split(",");
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);

        shapeBuilder.buildShape(id, type, new Point(x, y), width, height);
        controller.addUndo(shapeBuilder.getHashMap());
    }
    // change shape features
    @PostMapping("/updateFillColor")
    public void changeFillColor(@RequestParam int id, @RequestParam String fillColor)
            throws CloneNotSupportedException {
        Shape shape = getShape(id);
        shape.setFillColor(fillColor);
        refreshShape(id, shape);
    }

    @PostMapping("/updateThickness")
    public void changeThickness(@RequestParam int id, @RequestParam Integer thickness)
            throws CloneNotSupportedException {
        Shape shape = getShape(id);
        shape.setThickness(thickness);
        refreshShape(id, shape);
    }
    // Change line Features
    @PostMapping("/updateLinePos")
    public void changeLinePos(@RequestParam int id, @RequestParam String startingPoint, @RequestParam String endingPoint)
            throws CloneNotSupportedException {
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
                                        @RequestParam double width, @RequestParam double height) throws CloneNotSupportedException {
        
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

    @PostMapping("updateOutlineColor")
    public void changeOutlineColor(@RequestParam int id, @RequestParam String outlineColor)
            throws CloneNotSupportedException {
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setOutlineColor(outlineColor);
        refreshShape(id, multiPointShape);
    }

    @PostMapping("updateFillOpacity")
    public void changeFillOpacity(@RequestParam int id, @RequestParam double opacity)
            throws CloneNotSupportedException {
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
    public void refreshShape(Integer ID, Shape shape) throws CloneNotSupportedException {
        shapeBuilder.updateShape(ID, shape); // after editing shape, we update the shape in the map
        controller.addUndo(shapeBuilder.getHashMap()); // after editing shape, we add clone of shape to undo stack
    }

}
