package com.example.designPatterns.commandModel;

/**
 * 具体的类, 每一个类都有特定的方法
 */
public class Door {
    public void open() {
        System.out.println("打开门");
    }

    public void close() {
        System.out.println("关闭门");
    }
}
