package com.example.backend;

import java.util.HashMap;
import java.util.Stack;

public class Controller{
    private Stack<HashMap<Integer, Shape>> undo = new Stack<>();
    private Stack<HashMap<Integer, Shape>> redo = new Stack<>();
    
    public HashMap<Integer, Shape> performUndo(){
        HashMap<Integer, Shape> undoed;
        if(undo.size() != 0){
            undoed = undo.pop();
            redo.push(undoed);
        }
        if(undo.size() != 0) return undo.peek();
        else return null;
        
    }
    public HashMap<Integer, Shape> performRedo(){
        HashMap<Integer, Shape> redoed;
        redoed = null;
        if(redo.size() != 0){
            redoed = redo.pop();
            undo.push(redoed);
        }
        return redoed;
    }

    public void addUndo(HashMap<Integer, Shape> shapes) throws CloneNotSupportedException {
        HashMap<Integer, Shape> shapesClone = new HashMap<>();

        // Cloning each shape then placing in new HashMap
        for(HashMap.Entry<Integer, Shape> mapElement : shapes.entrySet()){
            Integer ID = mapElement.getKey();
            Shape shapeClone = (shapes.get(ID)).clone();
            shapesClone.put(ID, shapeClone);
        }
        undo.push(shapesClone);
        redo.clear();
    }

    public void resetUndoRedo(){
        undo.clear();
        redo.clear();
    }
}
