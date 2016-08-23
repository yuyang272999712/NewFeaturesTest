package com.example.designPatterns.adapterModel;

/**
 * 适配器，把220V电压变成5V
 */
public class V5PowerAdapter implements V5Power {
    private V220Power v220Power;

    public V5PowerAdapter(V220Power v220Power){
        this.v220Power = v220Power;
    }

    @Override
    public int provideV5Power() {
        int power = v220Power.provideV220Power();
        System.out.println("适配器：将220V交流转换为5V直流电");
        return power/44;
    }
}
