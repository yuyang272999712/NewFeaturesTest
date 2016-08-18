package com.example.designPatterns.decoratorModel;

/**
 * 装饰者模式
 */
public class DecoratorModel {
    public static void main(String[] args){
        ArmsEquip armsEquip = new ArmsEquip();
        RingEquip ringEquip = new RingEquip();

        //武器加装红蓝宝石
        IEquip armsDecoratorEquip = new RedGemDecorator(new BlueGemDecorator(armsEquip));
        //戒指加装两个红宝石一个蓝宝石
        IEquip ringDecoratorEquip = new RedGemDecorator(new RedGemDecorator(new BlueGemDecorator(ringEquip)));

        System.out.println("一个镶嵌1颗红宝石，1颗蓝宝石 的武器");
        System.out.println("攻击力  : " + armsDecoratorEquip.caculateAttack());
        System.out.println("描述 :" + armsDecoratorEquip.description());
        System.out.println("-------");

        System.out.println("一个镶嵌2颗红宝石，1颗蓝宝石 的戒指");
        System.out.println("攻击力  : " + ringDecoratorEquip.caculateAttack());
        System.out.println("描述 :" + ringDecoratorEquip.description());
        System.out.println("-------");
    }
}
