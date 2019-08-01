package com.telran.example_1;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz = Class.forName("com.telran.example_1.model.Person");

        //newInstance -> for public access
//        Object personInstance = clazz.newInstance();

        Constructor defaultConstructor = clazz.getDeclaredConstructor();
        defaultConstructor.setAccessible(true);

        Object personInstance = defaultConstructor.newInstance();


        String setName = "setName";
        String setAge = "setAge";
        String toString = "toString";

        Method setNameMethod = clazz.getMethod(setName, String.class);
        setNameMethod.setAccessible(true);

        Method setAgeMethod = clazz.getMethod(setAge, Integer.class);
        setAgeMethod.setAccessible(true);

        setNameMethod.invoke(personInstance, "Peter");
        setAgeMethod.invoke(personInstance, 46);

        Method toStringMethod = clazz.getMethod(toString);
        toStringMethod.setAccessible(true);

        Object toStringReturnValue = toStringMethod.invoke(personInstance);

        System.out.println(toStringReturnValue);
    }
}

