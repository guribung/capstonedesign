package com.capstone.loginactivity;

public class UserItem {
    String name;
    String hp;
    String uid;
    public UserItem(){ }

    public void SetName(String name){
        this.name = name;
    }
    public void SetHp(String hp){
        this.hp = hp;
    }

    public void SetUid(String uid){
        this.uid = uid;
    }

    public String GetName(){
        return this.name;
    }
    public String GetHp(){
        return this.hp;
    }
}
