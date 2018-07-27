package com.example.extend;

/**
 * Created by yuyang on 2018/6/5.
 */

public class A {
    public String show(D obj){              //方法一
        return ("A and D");
    }
    public String show(A obj){              //方法二
        return ("A and A");
    }
}
