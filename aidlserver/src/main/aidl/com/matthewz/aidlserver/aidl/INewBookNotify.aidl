package com.matthewz.aidlserver.aidl;

import com.matthewz.aidlserver.aidl.Book;

interface INewBookNotify {

    void onNewBookAdded(in Book book);
}