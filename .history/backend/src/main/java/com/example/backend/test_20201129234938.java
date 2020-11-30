package com.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test {

    public static String obj2json(Object a) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(a);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;

    }

    public static void main(String args[]) throws Exception {
        String shosh = "hi shosh ";
        int ahly = 9;
        String output = obj2json(shosh);
        output += obj2json(ahly);
        System.out.println(output);
   }
}