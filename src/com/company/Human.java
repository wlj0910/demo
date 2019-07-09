package com.company;

public class Human extends Animal{
    private String country;
    public Human(String name,int age,String country){
        super(name,age);
        this.country=country;
    }
    //没有重写，则继承父类。
    @Override
   public void say() {
        //多态
        System.out.println("This Human from: "+country);
    }
}
