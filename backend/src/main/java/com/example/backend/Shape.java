package com.example.backend;

public abstract class Shape implements Cloneable{
    private int id;
    private String type;
    private String fillColor = "default_FillColor";
    private double thickness = 0.0;

    public Shape(int id, String type){
        this.id = id;
        this.type = type;
    }

    @Override
	protected Shape clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }

    public Integer getId(){
        return this.id;
    }

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }

    public void setFillColor(String fillColor){
        this.fillColor = fillColor;
    }
    public String getFillColor(){
        return fillColor;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }
}
