// IOnNewBookArrivedListener.aidl
package com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL;

// Declare any non-default types here with import statements
import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
