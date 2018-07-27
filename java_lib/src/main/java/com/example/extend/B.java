package com.example.extend;

/**
 * Created by yuyang on 2018/6/5.
 */

class B extends A{
    public String show(B obj){              //方法三
        return ("B and B");
    }
    public String show(A obj){              //方法四
        return ("B and A");
    }
}
