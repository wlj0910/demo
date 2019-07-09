package com.company;

public class Animal implements Talking{
    private String name;
    private int age;
    public Animal(String name,int age){
        this.name=name;
        this.age=age;
    }
    @Override
    public void say(){
        System.out.println(name+":我会说话哦！");
    }
}
