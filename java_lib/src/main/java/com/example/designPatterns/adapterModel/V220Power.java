package com.example.designPatterns.adapterModel;

/**
 * 家用220V交流电
 */
public class V220Power {
    public int provideV220Power(){
        System.out.println("提供220V交流电");
        return 220;
    }
}
