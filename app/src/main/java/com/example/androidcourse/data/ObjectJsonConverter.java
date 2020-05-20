package com.example.androidcourse.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;



public class ObjectJsonConverter {


    public static String convertObjectToJson(Object o){

        String jsonStr = null;

        ObjectMapper mapperObj = new ObjectMapper();

        try {
            // get Employee object as a json string
            jsonStr = mapperObj.writeValueAsString(o);
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }


    /*
    * s => the string to convert into -> c
    * c => the specific class
     */
    public static Object convertJsonToObject(String s, Class c){
        Object o = null;

        System.out.println(s);


        ObjectMapper mapper = new ObjectMapper();
        try {
            o = mapper.readValue(s, c);
            System.out.println(o);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return o;

    }

}
