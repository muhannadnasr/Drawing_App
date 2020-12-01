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

    //testing
    @PostMapping("reset")
    public void reset(){
        shapeBuilder.resetShapesMap();
        controller.resetUndoRedo();
    }
    @GetMapping("test")
    public String test(){
        return jsonConverter.jsonStrFromHashMap(shapeBuilder.getHashMap());
    }
    // Creation and undo/redo
    @GetMapping("/undo")
    public String performUndo() throws CloneNotSupportedException {
        HashMap<Integer, Shape> undoMap = controller.performUndo();

        if (undoMap == null) return "empty";
        HashMap<Integer, Shape> current = new HashMap<Integer, Shape>();
        for(HashMap.Entry<Integer, Shape> mapElement : undoMap.entrySet()){
            Integer ID = mapElement.getKey();
            Shape shapeClone = (undoMap.get(ID)).clone();
            current.put(ID, shapeClone);
        }
        shapeBuilder.setHashMap(current);
        return jsonConverter.jsonStrFromHashMap(undoMap);
    }

    @GetMapping("/redo")
    public String performRedo() throws CloneNotSupportedException {
        HashMap<Integer, Shape> redoMap = controller.performRedo();

        if (redoMap == null) return "empty";
        HashMap<Integer, Shape> current = new HashMap<Integer, Shape>();
        for(HashMap.Entry<Integer, Shape> mapElement : redoMap.entrySet()){
            Integer ID = mapElement.getKey();
            Shape shapeClone = (redoMap.get(ID)).clone();
            current.put(ID, shapeClone);
        }
        shapeBuilder.setHashMap(current);
        return jsonConverter.jsonStrFromHashMap(redoMap);
    }

    @PostMapping("/createLine")
    public void createLine( @RequestParam int id, 
                            @RequestParam String startingPoint, 
                            @RequestParam String endingPoint) throws CloneNotSupportedException {

        shapeBuilder.buildLine(id, getPointCoordinates(startingPoint), getPointCoordinates(endingPoint));
        controller.addUndo(shapeBuilder.getHashMap()); // after adding shape, we add clone of map to undo stack
    }

    @PostMapping("/createShape")
    public void createMultiPointShape(  @RequestParam int id, @RequestParam String type, 
                                        @RequestParam String upperLeftCorner, 
                                        @RequestParam double width, @RequestParam double height) throws CloneNotSupportedException {

        shapeBuilder.buildShape(id, type, getPointCoordinates(upperLeftCorner), width, height);
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
    public void changeThickness(@RequestParam int id, @RequestParam int thickness)
            throws CloneNotSupportedException {
        Shape shape = getShape(id);
        shape.setThickness(thickness);
        refreshShape(id, shape);
    }
    // Change line Features
    @PostMapping("/updateLinePos")
    public void changeLinePos(@RequestParam int id, @RequestParam String startingPoint, @RequestParam String endingPoint)
            throws CloneNotSupportedException {

        Line line = (Line) getShape(id);
        line.setStartingPoint(getPointCoordinates(startingPoint));
        line.setEndingPoint(getPointCoordinates(endingPoint));
        refreshShape(id, line);
    }

    // Change multiPointShape fearues
    @PostMapping("/updateShapePosAndSize")
    public void changeShapePosAndSize(  @RequestParam int id, @RequestParam String upperLeftCorner,
                                        @RequestParam double width, @RequestParam double height) throws CloneNotSupportedException {

        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setUpperLeftCorner(getPointCoordinates(upperLeftCorner));
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
    // copy and paste
    @GetMapping("/getShapeData")
    public String getShapeData(@RequestParam int id){
        Shape shape = getShape(id);
        return jsonConverter.shapeToJsonString(shape);
    }
    // deleting shape from hashmap
    @PostMapping("/delete")
    public void deleteShape(@RequestParam int id) throws CloneNotSupportedException {
        shapeBuilder.deleteShape(id);
        controller.addUndo(shapeBuilder.getHashMap());
    }

    @PostMapping("/createShapeCopy")
    public void createShapeCopy(@RequestParam int id, @RequestParam String type,
                                @RequestParam String upperLeftCorner,
                                @RequestParam double width, @RequestParam double height,
                                @RequestParam String fillColor, @RequestParam double fillOpacity,
                                @RequestParam int thickness, @RequestParam String outlineColor) throws CloneNotSupportedException {

        shapeBuilder.buildShape(id, type, getPointCoordinates(upperLeftCorner), width, height);
        MultiPointShape shape = (MultiPointShape) getShape(id);
        shape.setFillColor(fillColor);
        shape.setFillOpacity(fillOpacity);
        shape.setOutlineColor(outlineColor);
        shape.setThickness(thickness);
        controller.addUndo(shapeBuilder.getHashMap());
    }

    @PostMapping("/createLineCopy")
    public void createLineCopy(@RequestParam int id,
                               @RequestParam String startingPoint, @RequestParam String endingPoint,
                               @RequestParam String fillColor, @RequestParam int thickness) throws CloneNotSupportedException {

        shapeBuilder.buildLine(id, getPointCoordinates(startingPoint), getPointCoordinates(endingPoint));
        Line line = (Line) getShape(id);
        line.setFillColor(fillColor);
        line.setThickness(thickness);
        controller.addUndo(shapeBuilder.getHashMap());
    }

    // Doesn't need URL Mapping
    private void refreshShape(Integer ID, Shape shape) throws CloneNotSupportedException {
        shapeBuilder.updateShape(ID, shape); // after editing shape, we update the shape in the map
        controller.addUndo(shapeBuilder.getHashMap()); // after editing shape, we add clone of shape to undo stack
    }

    private Shape getShape(int id) {
        return shapeBuilder.getShape(id);
    }

    private Point getPointCoordinates(String point){
        String [] startingCoordinates = point.split(",");
        double x = Double.parseDouble(startingCoordinates[0]);
        double y = Double.parseDouble(startingCoordinates[1]);
        return new Point(x, y);
    }

}
