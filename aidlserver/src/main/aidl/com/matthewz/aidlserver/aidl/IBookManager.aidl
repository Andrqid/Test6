package com.matthewz.aidlserver.aidl;

import com.matthewz.aidlserver.aidl.Book;
import com.matthewz.aidlserver.aidl.INewBookNotify;


interface IBookManager {
    void addBook(in Book book);

    List<Book> getAllBooks();

    void registerNewBookNotify(INewBookNotify notify);

    void unregisterNewBookNotify(INewBookNotify notify);

    IBinder getMyTestBinder();
}