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

    //testing
    @PostMapping("reset")
    public void reset(){
        shapeBuilder.resetShapesMap();
        controller.resetUndoRedo();
    }
    // Creation and undo/redo
    @GetMapping("/undo")
    public String performUndo(){
        String undoMap = controller.performUndo();

        if (undoMap == null) return "empty";
        shapeBuilder.resetShapesMap();
        return undoMap;
    }

    @GetMapping("/redo")
    public String performRedo(){
        String redoMap = controller.performRedo();

        if (redoMap == null) return "empty";
        shapeBuilder.resetShapesMap();
        return redoMap;
    }

    @PostMapping("/createLine")
    public void createLine( @RequestParam int id, 
                            @RequestParam String startingPoint, 
                            @RequestParam String endingPoint){

        shapeBuilder.buildLine(id, getPointCoordinates(startingPoint), getPointCoordinates(endingPoint));
        controller.addUndo(shapeBuilder.getHashMap()); // after adding shape, we add clone of map to undo stack
    }

    @PostMapping("/createShape")
    public void createMultiPointShape(  @RequestParam int id, @RequestParam String type, 
                                        @RequestParam String upperLeftCorner, 
                                        @RequestParam double width, @RequestParam double height){

        shapeBuilder.buildShape(id, type, getPointCoordinates(upperLeftCorner), width, height);
        controller.addUndo(shapeBuilder.getHashMap());
    }
    // change shape features
    @PostMapping("/updateFillColor")
    public void changeFillColor(@RequestParam int id, @RequestParam String fillColor){
        Shape shape = getShape(id);
        shape.setFillColor(fillColor);
        refreshShape(id, shape);
    }

    @PostMapping("/updateThickness")
    public void changeThickness(@RequestParam int id, @RequestParam int thickness){
        Shape shape = getShape(id);
        shape.setThickness(thickness);
        refreshShape(id, shape);
    }
    // Change line Features
    @PostMapping("/updateLinePos")
    public void changeLinePos(@RequestParam int id, @RequestParam String startingPoint, @RequestParam String endingPoint){

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
    public void changeOutlineColor(@RequestParam int id, @RequestParam String outlineColor){
        MultiPointShape multiPointShape = (MultiPointShape) getShape(id);
        multiPointShape.setOutlineColor(outlineColor);
        refreshShape(id, multiPointShape);
    }

    @PostMapping("updateFillOpacity")
    public void changeFillOpacity(@RequestParam int id, @RequestParam double opacity){
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
    public void deleteShape(@RequestParam int id){
        shapeBuilder.deleteShape(id);
        controller.addUndo(shapeBuilder.getHashMap());
    }

    @PostMapping("/createShapeCopy")
    public void createShapeCopy(@RequestParam int id, @RequestParam String type,
                                @RequestParam String upperLeftCorner,
                                @RequestParam double width, @RequestParam double height,
                                @RequestParam String fillColor, @RequestParam double fillOpacity,
                                @RequestParam int thickness, @RequestParam String outlineColor,
                                @RequestParam boolean addUndo){

        shapeBuilder.buildShape(id, type, getPointCoordinates(upperLeftCorner), width, height);
        MultiPointShape shape = (MultiPointShape) getShape(id);
        shape.setFillColor(fillColor);
        shape.setFillOpacity(fillOpacity);
        shape.setOutlineColor(outlineColor);
        shape.setThickness(thickness);
        if(addUndo) {
            controller.addUndo(shapeBuilder.getHashMap());
        }
    }

    @PostMapping("/createLineCopy")
    public void createLineCopy(@RequestParam int id,
                               @RequestParam String startingPoint, @RequestParam String endingPoint,
                               @RequestParam String fillColor, @RequestParam int thickness,
                               @RequestParam boolean addUndo){

        shapeBuilder.buildLine(id, getPointCoordinates(startingPoint), getPointCoordinates(endingPoint));
        Line line = (Line) getShape(id);
        line.setFillColor(fillColor);
        line.setThickness(thickness);
        if(addUndo) {
            controller.addUndo(shapeBuilder.getHashMap());
        }
    }

    // Doesn't need URL Mapping
    private void refreshShape(Integer ID, Shape shape){
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
