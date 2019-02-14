package com.matthewz.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.matthewz.aidlserver.aidl.Book;
import com.matthewz.aidlserver.aidl.IBookManager;
import com.matthewz.aidlserver.aidl.INewBookNotify;
import com.matthewz.aidlserver.aidl.ITest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service{

    private static final String TAG = "BookService";
    private volatile boolean mDestroyed = false;
    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<INewBookNotify> mCallbackList = new RemoteCallbackList<>();
    private Random mRandom = new Random();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }

        @Override
        public List<Book> getAllBooks() throws RemoteException {
            return mBooks;
        }

        @Override
        public void registerNewBookNotify(INewBookNotify notify) throws RemoteException {
            mCallbackList.register(notify);
            int i = mCallbackList.beginBroadcast();
            Log.e(TAG, "registerNewBookNotify callback size : " + i);
            mCallbackList.finishBroadcast();
        }

        @Override
        public void unregisterNewBookNotify(INewBookNotify notify) throws RemoteException {
            mCallbackList.unregister(notify);
            int i = mCallbackList.beginBroadcast();
            Log.e(TAG, "unregisterNewBookNotify callback size : " + i);
            mCallbackList.finishBroadcast();
        }

        @Override
        public IBinder getMyTestBinder() throws RemoteException {
            return new ITest.Stub() {
                @Override
                public void justDoIt(String msg) throws RemoteException {
                    Toast.makeText(BookService.this, msg, Toast.LENGTH_LONG).show();
                }
            };
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //这个不管用，一直是DENIED
//        int result = checkCallingPermission("com.matthewz.aidlserver.permission.book_manage");

        //让client在绑定的时候将自己的pid和uid传过来，验证权限问题
        int pid = intent.getIntExtra("pid", -1);
        int uid = intent.getIntExtra("uid", -1);
        int result = checkPermission("com.matthewz.aidlserver.permission.book_manage", pid, uid);

        //这个一直返回GRANTED
//        int result = checkCallingOrSelfPermission("com.matthewz.aidlserver.permission.book_manage");

        if(result != PackageManager.PERMISSION_GRANTED)
            return null;    //return null，client没有任何反应
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new AddBookThread().start();
    }

    @Override
    public void onDestroy() {
        mDestroyed = true;
        super.onDestroy();
    }

    private void notifyNewBook(Book book) {
        final int total = mCallbackList.beginBroadcast();
        for(int i=0; i<total; i++) {
            INewBookNotify notify = mCallbackList.getBroadcastItem(i);
            try {
                notify.onNewBookAdded(book);
            } catch (RemoteException e) {
//                e.printStackTrace();
                Log.e(TAG, "notifyNewBook : " + e.getMessage());
            }
        }
        mCallbackList.finishBroadcast();
    }

    private class AddBookThread extends Thread {
        @Override
        public void run() {
            while (!mDestroyed) {
                SystemClock.sleep(10 * 1000);
                if(mDestroyed) break;

                int i = mBooks.size() + 1;
                Book book = new Book(i, "这是一本书 From AddBookThread ： " + i);
                mBooks.add(book);
                notifyNewBook(book);
            }
        }
    }
}
