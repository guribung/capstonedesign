package com.capstone.loginactivity;

public class ConnectInfo {
    String datetime;
    String docUid;
    String name;

    public void setName(String name){
        this.name = name;
    }


    public void setDatetime(String datetime){
        this.datetime = datetime;
    }
    public void setDocUid(String uid){
        this.docUid = uid;
    }
    public String getDatetime(){
        return this.datetime;
    }
    public String getDocUid(){
        return this.docUid;
    }
}
