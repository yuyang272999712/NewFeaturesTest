package com.example.designPatterns.strategyModel;

/**
 * 策略模式
 * 举例：小说中的人物，不同的人物有不同的技能，技能这个属性是可能变化的，那我们就可以将技能这个属性抽象出来，这样不同的人
 *      就可以很方便的设置技能而不必修改人物属性，技能还可以复用－被不同的人拥有
 */
public class StrategyModel {
    public static void main(String[] args){
        Role guojing = new RoleGuojing("郭靖");
        Role huangrong = new RoleHuangrong("黄蓉");
        Role qiaofeng = new RoleQiaofeng("乔峰");
        guojing.setSkill(new SkillXLSBZ());
        huangrong.setSkill(new SkillJYSG());
        qiaofeng.setSkill(new SkillXLSBZ());

        System.out.println(guojing.name+":"+guojing.getSkill());
        System.out.println(huangrong.name+":"+huangrong.getSkill());
        System.out.println(qiaofeng.name+":"+qiaofeng.getSkill());
    }
}
