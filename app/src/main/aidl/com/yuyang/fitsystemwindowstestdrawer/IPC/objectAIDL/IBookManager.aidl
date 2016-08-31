// IBookManager.aidl
package com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL;

import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.Book;
import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
