// IBookManager.aidl
package com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL;

import com.yuyang.fitsystemwindowstestdrawer.IPC.objectAIDL.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
