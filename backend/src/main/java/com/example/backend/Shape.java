package com.example.backend;

public abstract class Shape implements Cloneable{

    private Integer ID = null;
    private Point containerUpperLeftCorner;
    private double containerHeight;
    private double containerWidth;
    private String type = null;
    private double angle = 0;
    private RGB fillInColor;
    private double fillOpacity;
    private RGB outlineColor;
    private int outlineThickness;
    private double outlineOpacity;
    private String text = null;
    private int z;

    public Shape(Integer ID, String type){
        this.ID = ID;
        this.type = type;
    }

    @Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

    public void setID(Integer ID){
        this.ID = ID;
    }
    public Integer getID(){
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

    public double getFillOpacity() { return fillOpacity; }

    public void setFillOpacity(double fillOpacity) { this.fillOpacity = fillOpacity; }

    public void setOutlineThickness(int outlineThickness){
        this.outlineThickness = outlineThickness;
    }
    public int getOutlineThickness(){
        return outlineThickness;
    }

    public double getOutlineOpacity() { return outlineOpacity; }
    public void setOutlineOpacity(double outlineOpacity) { this.outlineOpacity = outlineOpacity; }

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

    public void setContainerUpperLeftCorner(Point point){
        this.containerUpperLeftCorner = point;
    }
    public Point getContainerUpperLeftCorner(){
        return this.containerUpperLeftCorner;
    }

    public void setContainerHeight(Double height){
        this.containerHeight = height;
    }
    public double getContainerHeight(){
        return this.containerHeight;
    }

    public void setContainerWidth(Double width){
        this.containerWidth = width;
    }
    public double getContainerWidth(){
        return this.containerWidth;
    }

    public abstract void changeSize(Double length1, Double length2, Point[]points);
    public abstract void changePosition(Point startingPosition, Point[]points);
}
