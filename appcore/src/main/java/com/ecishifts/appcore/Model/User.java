package com.ecishifts.appcore.Model;

public class User {
    String id;
    String name;
    String mail;
    Type type;
    public User(String iD, String name, String mail, Type type) {
        this.id = iD;
        this.name = name;
        this.mail = mail;
        this.type = type;
    }
    public String getID() {
        return id;
    }
    public void setID(String iD) {
        id = iD;
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
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    
}
