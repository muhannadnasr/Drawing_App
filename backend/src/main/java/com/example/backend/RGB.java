package com.example.backend;

public class RGB {

    private int red;
    private int green;
    private int blue;

    public RGB(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setRed(int red) {
        this.red = red;
    }
    public int getRed() {
        return red;
    }

    public void setGreen(int green) {
        this.green = green;
    }
    public int getGreen() {
        return green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
    public int getBlue() {
        return blue;
    }
}
