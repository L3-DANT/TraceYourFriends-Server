package com.traceyourfriend.beans;

/**
 * Created by Error_404 on 30/05/2016.
 */
public class Message {

    private String name;
    private String nameAmi;
    private boolean bool;

    public Message(String name, String nameAmi){
        this.name = name;
        this.nameAmi = nameAmi;
    }

    public Message(String name, String nameAmi, boolean bool){
        this.nameAmi = nameAmi;
        this.name = name;
        this.bool = bool;
    }

    public String getName() {
        return name;
    }

    public String getNameAmi() {
        return nameAmi;
    }

    public boolean isBool() {
        return bool;
    }
}
