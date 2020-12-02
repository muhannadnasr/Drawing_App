package com.example.backend;

import java.util.HashMap;
import java.util.Stack;

public class Controller{
    private Stack<String> undo = new Stack<>();
    private Stack<String> redo = new Stack<>();
    private JsonConverter jsonConverter = new JsonConverter();
    
    public String performUndo(){
        String undoed;
        System.out.println("\n" + undo.size());
        if(undo.size() != 0){
//            System.out.println("before\n" + undo.peek() + "\n");
            undoed = undo.pop();
//            System.out.println("popped");
            redo.push(undoed);
        }
        System.out.println(undo.size() + "\n");
        if(undo.size() != 0) {
//            System.out.println("after\n" + undo.peek() + "\n");
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
        System.out.println("calledMe");
        String jsonStr = jsonConverter.jsonStrFromHashMap(shapes);
        undo.push(jsonStr);
        redo.clear();
    }

    public void resetUndoRedo(){
        undo.clear();
        redo.clear();
    }
}
