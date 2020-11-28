package com.example.backend;

public abstract class Shape implements Cloneable{
    private int id;
    private String type;
    private double angle = 0;
    private RGB fillColor;
    private double fillOpacity;
    private double thickness;
    private int z;

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

    public void setAngle(double angle){
        this.angle = angle;
    }
    public double getAngle(){
        return angle;
    }

    public void setFillColor(RGB fillColor){
        this.fillColor = fillColor;
    }
    public RGB getFillColor(){
        return fillColor;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public void setZ(int z){
        this.z = z;
    }
    public int getZ(){
        return z;
    }
}
