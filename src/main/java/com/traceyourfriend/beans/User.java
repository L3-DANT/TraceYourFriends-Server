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
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCoorX(String coorNew){this.coorX = coorNew;}

    public void setCoorY(String coorNew){this.coorY = coorNew;}

    public ArrayList<String> getAmis(){return this.amis;}

    public void setAmis(ArrayList<String> contacts){this.amis = contacts;}

    public boolean addAmi(String ami){return this.amis.add(ami);}

    public boolean removeAmi(String ami){return this.amis.remove(ami);}

    public ArrayList<String> getDemandesAmi(){return this.demandesAmi;}

    public void setDemandesAmi(ArrayList<String> demandesAmi){this.demandesAmi = demandesAmi;}

    public boolean addDemandeAmi(String demandeAmi){return this.demandesAmi.add(demandeAmi);}

    public boolean removeDemandeAmi(String demandeAmi){return this.demandesAmi.remove(demandeAmi);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User)) return false;

        User u = (User)o;

        return !(this.mail != null ? !this.mail.equals(u.mail) : u.mail != null);

    }

    @Override
    public int hashCode() {
        return this.mail != null ? this.mail.hashCode() : 0;
    }

    @Override
    public int compareTo(User u) {
        return this.mail.compareToIgnoreCase(u.getMail());
    }

    @Override
    public String toString() {
        return this.mail;
    }

    public boolean estAmi(String name){
        return amis.contains(name);
    }

    public boolean aDemande(String name){
        return demandesAmi.contains(name);
    }


}
