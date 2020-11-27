package com.example.backend;

public abstract class Shape implements Cloneable{

    private Integer ID = null;
    private Point containerUpperLeftCorner;
    private Double containerHeight;
    private Double containerWidth;
    private String type = null;
    private double angle = 0;
    private RGB outlineColor;
    private RGB fillInColor;
    private int thickness;
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

    public void setContainerUpperLeftCorner(Point point){
        this.containerUpperLeftCorner = point;
    }

    public Point getContainerUpperLeftCorner(){
        return this.containerUpperLeftCorner;
    }

    public void setContainerHeight(Double height){
        this.containerHeight = height;
    }

    public Double getContainerHeight(){
        return this.containerHeight;
    }

    public void setContainerWidth(Double width){
        this.containerWidth = width;
    }

    public Double setContainerWidth(){
        return this.containerWidth;
    }

    public abstract void changeSize(Double length1, Double length2, Point[]points);
    public abstract void changePosition(Point startingPosition, Point[]points);
}
