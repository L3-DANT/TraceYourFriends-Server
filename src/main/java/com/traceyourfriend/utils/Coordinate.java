package com.traceyourfriend.utils;

/**
 * Created by romann on 11/05/2016.
 */
public class Coordinate {
    private String name;
    private String coorX;
    private String coorY;

    public Coordinate(String name, String coorX, String coorY){
        this.name = name;
        this.coorX = coorX;
        this.coorY = coorY;
    }

    public String getCoorY() {
        return coorY;
    }

    public String getCoorX() {
        return coorX;
    }

    public String getName() {
        return name;
    }
}
