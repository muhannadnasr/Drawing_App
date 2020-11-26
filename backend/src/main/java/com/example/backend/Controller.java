package com.example.backend;
import java.util.Stack;

public class Controller {
    private Stack<Shape> undo = new Stack<Shape>();
    private Stack<Shape> redo = new Stack<Shape>();

    public Shape performUndo(){
        redo.push(undo.pop());
        return undo.peek();
    }
    public Shape performRedo(){
        undo.push(redo.pop());
        return redo.peek();
    }
    public void addUndo(Shape shape){
        undo.push(shape);
    }
}
