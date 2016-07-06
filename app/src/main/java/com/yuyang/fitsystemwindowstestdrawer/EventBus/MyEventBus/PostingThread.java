package com.yuyang.fitsystemwindowstestdrawer.EventBus.MyEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前线程中的变量，存储了一个事件队列以及事件的状态；
 */
public class PostingThread {
    List<Object> mEventQueue = new ArrayList<Object>();
    boolean isMainThread;
    boolean isPosting;
}
