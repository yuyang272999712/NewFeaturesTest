package com.yuyang.fitsystemwindowstestdrawer.eventBus.myEventBusSimple;

import java.util.List;

/**
 * 1、如果我多个方法参数都一样，post一个此参数，会多个方法调用；而此时我想调用指定的方法怎么办？
 * 2、项目中会有很多地方去接收List参数，而List<T>中的泛型是不一致的，所以也可能post(List）时，
 *  会调用很多方法，造成出错。
 * TODO yuyang 推荐大家在使用EventBus的时候，创建一个事件类，把你的每一个参数（或者可能发生冲突的参数），封装成一个类：
 */
public class EventBeans {
    /** 列表加载事件 */
    public static class ItemListEvent {
        private List<Item> items;

        public ItemListEvent(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }
}
