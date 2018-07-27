package com.example.extend;

/**
 * Created by yuyang on 2018/6/5.
 */

public class Main {
    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child.normalStr);
        System.out.println(Child.staticStr);
        child.normalMethod();
        Child.staticMethod();

        System.out.println("-------------------------------------------------");

        Parent child1 = new Child();
        System.out.println(child1.normalStr);
        System.out.println(Parent.staticStr);
        child1.normalMethod();
        Parent.staticMethod();

        System.out.println("*************************************************");

        /*
            分析输出A a1 = new A();这是普通的创建对象，故a1拥有调用方法一和方法二的能力。那么究竟调用哪个方法呢，
        这里面涉及方法的重载。其实，在编译的时候，编译器已经进行了前期绑定，即把show()；方法与类中的方法主体2进行
        绑定。就是说，在编译时，系统就已经知道应该调用哪个方法，即使你有方法的重载。
            故 a1.show(b)会与方法二绑定；a1.show(c)会与方法二绑定；a1.show(d)会与方法一绑定。且都在编译时完成
        绑定
            但A a2 = new B();就涉及了多态 了，B实现了向上转型。创建了一个父类引用，指向子类对象。这样的做法很常见，
        因为这样不仅增加了灵活性（父类引用可以随时指向任意子类对象），也提高了扩展性。但要知道的是，向上转型的缺点，
        就是不能调用子类中有而父类没有的方法。
            故A a2 = new B();中，方法四对方法二进行了重写，所以a2拥有调用方法一和方法四的能力，而方法三不能调用
        （由上一段可知）。所以，在编译时， a2.show(b)会与方法四绑定； a2.show(c)会与方法四绑定；
        a2.show(d)会与方法一绑定
            B b = new B();这是普通的创建子类对象，B继承于A，且方法四重写了方法二，所以b拥有调用方法一，方法三，
        方法四的能力。所以b.show(b)会与方法三绑定，b.show(c)会与方法三绑定，b.show(d)会与方法一绑定
        * */
        A a1 = new A();
        A a2 = new B();
        B b = new B();
        C c = new C();
        D d = new D();
        System.out.println("1--" + a1.show(b));
        System.out.println("2--" + a1.show(c));
        System.out.println("3--" + a1.show(d));
        System.out.println("4--" + a2.show(b));
        System.out.println("5--" + a2.show(c));
        System.out.println("6--" + a2.show(d));
        System.out.println("7--" + b.show(b));
        System.out.println("8--" + b.show(c));
        System.out.println("9--" + b.show(d));
    }
}
