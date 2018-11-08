package com.aryan.fitnessapp_test;



public class WorkoutUser {
    String id;
    String weight;
    String height;
    String bmi;
    public WorkoutUser(){

    }
    public WorkoutUser(String id,String weight, String height, String bmi){
        this.id=id;
        this.weight=weight;
        this.height=height;
        this.bmi=bmi;
    }
    public void setId(String id){
        this.id=id;
    }
    public void setWeight(String weight){
        this.weight=weight;
    }
    public void setHeight(String height){

    }

    public String getHeight() {
        return height;
    }

    public String getBmi() {
        return bmi;
    }

    public String getWeight() {
        return weight;
    }
    public String getId(){return id;}
}
