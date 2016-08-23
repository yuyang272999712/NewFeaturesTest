package com.example.designPatterns.adapterModel;

/**
 * 手机充电器一般都是5V左右吧，家用交流电压220V，所以手机充电需要一个适配器（降压器）
 */
public class AdapterModel {
    public static void main(String[] args){
        V5Power v5Power = new V5PowerAdapter(new V220Power());
        Mobile mobile = new Mobile();
        mobile.inputPower(v5Power);
    }
}
