package com.example.backend;

import java.util.HashMap;

public class test {
    public static void main(String[] args) {

        HashMap<Integer, Shape> shapes = new HashMap<>();
        JsonConverter jc = new JsonConverter();
        String test = jc.jsonStrFromHashMap(shapes);
        System.out.println(test);
    }
}
