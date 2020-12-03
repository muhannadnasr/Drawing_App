package com.example.backend;

import java.util.HashMap;
import java.util.Stack;

public class Controller{
    private Stack<String> undo = new Stack<>();
    private Stack<String> redo = new Stack<>();
    private JsonConverter jsonConverter = JsonConverter.getInstance();
    private static Controller instance;

    private Controller(){}

    public static Controller getInstance(){
        if(instance == null) instance = new Controller();
        return instance;
    }
    
    public String performUndo(){
        String undoed;
        System.out.println("\n" + undo.size());
        if(undo.size() != 0){
            undoed = undo.pop();
            redo.push(undoed);
        }
        System.out.println(undo.size() + "\n");
        if(undo.size() != 0) {
            return undo.peek();
        }
        else return null;
        
    }
    public String performRedo(){
        String redoed;
        redoed = null;
        if(redo.size() != 0){
            redoed = redo.pop();
            undo.push(redoed);
        }
        return redoed;
    }

    public void addUndo(HashMap<Integer, Shape> shapes){
        String jsonStr = jsonConverter.jsonStrFromHashMap(shapes);
        undo.push(jsonStr);
        redo.clear();
    }

    public void resetUndoRedo(){
        undo.clear();
        redo.clear();
    }
}
