package com.example.backend;

public class Shape {

    private String ID = null;
    private String type = null;
    private double angle = 0;
    private RGB outlineColor;
    private RGB fillInColor;
    private int thickness;
    private String text = null;
    private int z;

    public void setID(String ID){

    this.ID = ID;

    }
    public String getID(){

    return ID;

    }
    public void setType(String type){

    this.type = type;

    }
    public String getType(){

    return type;
    
    }
    public void setAngle(double angle){

    this.angle = angle;

    }
    public double getAngle(){

    return angle;
    
    }   
    public void setOutlineColor(RGB outlineColor){

    this.outlineColor = outlineColor;

    }
    public RGB getOutlineColor(){

    return outlineColor;
    
    }
    public void setFillInColor(RGB fillInColor){

    this.fillInColor = fillInColor;

    }
    public RGB getFillInColor(){

    return fillInColor;
    
    }
    public void setThickness(int thickness){

    this.thickness = thickness;

    }
    public int getThickness(){

    return thickness;
    
    }
    public void setText(String text){

    this.text = text;

    }
    public String getText(){

    return text;

    }
    public void setZ(int z){

    this.z = z;

    }
    public int getZ(){

    return z;
    
    }

}
