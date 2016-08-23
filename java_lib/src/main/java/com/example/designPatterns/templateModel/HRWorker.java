package com.example.designPatterns.templateModel;

/**
 * Created by yuyang on 16/8/23.
 */
public class HRWorker extends Worker {

    public HRWorker(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(name + "看简历-打电话-接电话");
    }

}