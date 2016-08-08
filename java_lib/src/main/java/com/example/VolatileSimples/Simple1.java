package com.example.VolatileSimples;

/**
 * 在并发编程中，我们通常会遇到以下三个问题：原子性问题，可见性问题，有序性问题。
 * Volatile可以有效避免可见性和有序性问题
 *
 * volatile保持可见性实例：
 *  一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
 *  1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 *  2）禁止进行指令重排序。
 */
public class Simple1 {
    /**
     * 如果不加Volatile
     *    每个线程在运行过程中都有自己的工作内存，那么线程1在运行的时候，会将stop变量的值拷贝一份放在自己的工作内存当中。
     * 那么当线程2更改了stop变量的值之后，但是还没来得及写入主存当中，线程2转去做其他事情了，那么线程1由于不知道线程2对stop变量的更改，因此还会一直循环下去。
     *
     * 但是用volatile修饰之后就变得不一样了：
     *  第一：使用volatile关键字会强制将修改的值立即写入主存；
     *  第二：使用volatile关键字的话，当线程2进行修改时，会导致线程1的工作内存中缓存变量stop的缓存行无效（反映到硬件层的话，
     *      就是CPU的L1或者L2缓存中对应的缓存行无效）；
     *  第三：由于线程1的工作内存中缓存变量stop的缓存行无效，所以线程1再次读取变量stop的值时会去主存读取。
     *  那么在线程2修改stop值时（当然这里包括2个操作，修改线程2工作内存中的值，然后将修改后的值写入内存），会使得线程1的工作内存中
     * 缓存变量stop的缓存行无效，然后线程1读取时，发现自己的缓存行无效，它会等待缓存行对应的主存地址被更新之后，然后去对应的主存读取最新的值。
     *  那么线程1读取到的就是最新的正确的值。
     */

    static volatile boolean flag = false;

    public static void main(String args[]){
        new Thread("线程1"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"开始执行");
                while (!flag){
                    while (true){
                        //doSomething
                    }
                }
                System.out.println(Thread.currentThread().getName()+"执行完毕");
            }
        }.start();

        new Thread("线程2"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"改变了flag的值－－－－－");
                flag = true;
            }
        }.start();
    }
}
