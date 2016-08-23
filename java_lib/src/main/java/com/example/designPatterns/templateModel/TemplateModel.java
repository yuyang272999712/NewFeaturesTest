package com.example.designPatterns.templateModel;

/**
 * 本公司有程序猿、测试、HR等人，下面使用模版方法模式，记录下所有人员的上班情况
 */
public class TemplateModel {
    public static void main(String[] args){
        Worker it = new ITWorker("老张");
        Worker hr = new HRWorker("迪迪");
        Worker qa = new QAWorker("老李");

        it.workOneDay();
        hr.workOneDay();
        qa.workOneDay();
    }
}
