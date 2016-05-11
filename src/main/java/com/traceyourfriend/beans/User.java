package com.traceyourfriend.beans;

import java.util.ArrayList;
import java.util.Hashtable;

public class User implements Comparable<User> {
    private long id;

    private String name;

    private String mail;

    private String password;

    private String coorX;

    private String coorY;

    private ArrayList<String> amis;

    private ArrayList<String> demandesAmi;

    public User(){}

    public User(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public User(String mail, String password) {
        this("",mail,password);
    }

    public User(String name, String mail, String password, String coorX, String coorY, ArrayList<String> amis, ArrayList<String> demandesAmi) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.coorX = coorX;
        this.coorY = coorY;
        this.amis = amis;
        this.demandesAmi = demandesAmi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Hashtable<String,String> getCoor(){
        Hashtable<String,String> coor = new Hashtable<>();

        coor.put("coorX", this.coorX);
        coor.put("coorY", this.coorY);

        return coor;
    }

    public void setCoorX(String coorNew){this.coorX = coorNew;}

    public void setCoorY(String coorNew){this.coorY = coorNew;}

    public ArrayList<String> getAmis(){return amis;}

    public void setAmis(ArrayList<String> contacts){this.amis = contacts;}

    public ArrayList<String> getDemandesAmi(){return demandesAmi;}

    public void setDemandesAmi(ArrayList<String> demandesAmi){this.amis = demandesAmi;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(mail != null ? !mail.equals(user.mail) : user.mail != null);

    }

    @Override
    public int hashCode() {
        return mail != null ? mail.hashCode() : 0;
    }

    @Override
    public int compareTo(User o) {
        return mail.compareToIgnoreCase(o.getMail());
    }

    @Override
    public String toString() {
        return mail;
    }

    public boolean estAmi(String name){
        for (String ami : amis){
            if (ami.equals(name)){
                return true;
            }
        }
        return false;
    }


}
