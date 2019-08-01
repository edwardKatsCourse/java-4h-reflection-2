package com.telran.example_1.model;

class Person {

    private String name;
    private Integer age;

    public Person() {
    }

    //public
    //protected = package default + children
    //package default
    //private

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    //package default
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
