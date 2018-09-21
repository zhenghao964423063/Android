package com.example.asus.materialtext;

/**
 * Created by ASUS on 2018/9/1.
 */

public class Fruit {
    private String name;
    private int imageId;
    public  Fruit(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
