package com.example.backend;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class shehab {
    private int length;
    private int width;
    private Point corner;

    shehab() {
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

public class Json {

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

    public static String jsonStrFromHashMap(HashMap<Integer, shehab> map) {
        String jsonStr = "{" + "\"Shapes\"" + ":" + " " + "{" + "\"Shape\"" + ":" + " " + "[" + "\n";
        for (Map.Entry<Integer, shehab> set : map.entrySet()) {
            jsonStr += obj2json(set.getValue());
            jsonStr += "," + "\n";
        }
        jsonStr = jsonStr.substring(0, jsonStr.length() - 2);
        jsonStr += "\n] } \n}";
        return jsonStr;

    }

    public static void saveJson(String path, String str) {
        PrintWriter out;
        try {
            out = new PrintWriter(path + ".json");
            out.println(str);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        shehab s1 = new shehab();
        s1.setLength(10);
        s1.setWidth(15);
        s1.setCorner(new Point(1, 2));
        shehab s2 = new shehab();
        s2.setLength(20);
        s2.setWidth(25);
        s2.setCorner(new Point(2, 2));
        shehab s3 = new shehab();
        s3.setLength(30);
        s3.setWidth(35);
        s3.setCorner(new Point(3, 2));

        HashMap<Integer, shehab> map = new HashMap<Integer, shehab>();
        map.put(1, s1);
        map.put(2, s2);
        map.put(3, s3);

        String output = jsonStrFromHashMap(map);
        saveJson("Desktop", output);
    }
    
}
