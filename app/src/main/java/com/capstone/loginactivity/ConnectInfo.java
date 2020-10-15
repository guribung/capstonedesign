package com.capstone.loginactivity;

public class ConnectInfo {
    String datetime;
    String docUid;
    String patientUid;
    String name;
    String clinicName;
    String intime;

    public void setName(String name){
        this.name = name;
    }


    public void setDatetime(String datetime){
        this.datetime = datetime;
    }
    public void setDocUid(String uid){
        this.docUid = uid;
    }
    public void setPatientUid(String uid){this.patientUid = uid;}
    public void setClinicName(String name){this.clinicName = name;}
    public void setIntime(String name){this.intime = name;}
    public String getDatetime(){
        return this.datetime;
    }
    public String getDocUid(){
        return this.docUid;
    }
    public  String getPatientUid(){return this.patientUid;}
    public String getClinicName(){return  this.clinicName;}
    public String getIntime(){return  this.intime;}
}
