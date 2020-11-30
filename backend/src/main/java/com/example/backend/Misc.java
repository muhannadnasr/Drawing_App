package com.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class square {
    private int length;
    private int width;
    private Point corner;
    square(){
        this.length = 0;
        this.width = 0;
        this.corner = null;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Point getCorner() {
        return corner;
    }

    public void setCorner(Point corner) {
        this.corner = corner;
    }
}

class test {
    public static String obj2json(Object a) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static void main(String[] args) throws Exception {
            square sq = new square();
            sq.setLength(10);
            sq.setWidth(15);
            sq.setCorner(new Point(1, 2));
            String output = obj2json(sq);
            System.out.println(output);
   }
}