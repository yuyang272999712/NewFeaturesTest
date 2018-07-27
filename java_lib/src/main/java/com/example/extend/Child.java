package com.example.extend;

/**
 * Created by yuyang on 2018/6/5.
 */

public class Child extends Parent {
    public String normalStr = "Normal member of child.";
    public static String staticStr = "Static member of child.";

    public void normalMethod(){
        System.out.println("Normal method of child.");
    }

    public static void staticMethod(){
        System.out.println("Static method of child.");
    }
}
