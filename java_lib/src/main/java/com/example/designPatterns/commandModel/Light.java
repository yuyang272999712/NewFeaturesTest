package com.example.designPatterns.commandModel;

/**
 * 具体的类, 每一个类都有特定的方法
 */
public class Light {
    public void on() {
        System.out.println("开灯");
    }

    public void off() {
        System.out.println("关灯");
    }
}
