package com.capstone.loginactivity;

public class User {
    String email;
    String member;
    String name;
    String uid;
    String phone;
    public User(){}

    public String getName(){
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
}
