package com.matthewz.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.matthewz.aidlserver.aidl.Book;
import com.matthewz.aidlserver.aidl.IBookManager;
import com.matthewz.aidlserver.aidl.INewBookNotify;
import com.matthewz.aidlserver.aidl.ITest;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int startId = 100;
    private static final String TAG = "MainActivity";
    private IBookManager mBookManager;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if(null == service) {
                Toast.makeText(MainActivity.this, "bind failed!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e(TAG, "onServiceConnected : " + String.valueOf(Thread.currentThread().getId()));
            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(mRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected : " + String.valueOf(Thread.currentThread().getId()));
            if(null != mBookManager) {
                mBookManager.asBinder().unlinkToDeath(mRecipient, 0);
                mBookManager = null;
            }
        }
    };

    private IBinder.DeathRecipient mRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binderDied : " + String.valueOf(Thread.currentThread().getId()));
        }
    };

    private INewBookNotify mNotify = new INewBookNotify.Stub() {
        @Override
        public void onNewBookAdded(Book book) throws RemoteException {
            Log.e(TAG, "onNewBookAdded() called with: book = [" + book + "]");
            Log.e(TAG, "onNewBookAdded() called with thread = " + Thread.currentThread().getId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bind(View view) {
        //5.0以上，隐式绑定service会boom shakalaka
        Intent intent = new Intent("com.matthewz.aidlserver.action.bookService");
        intent.setPackage("com.matthewz.aidlserver");
        intent.putExtra("pid", Process.myPid());
        intent.putExtra("uid", Process.myUid());
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void unbind(View view) {
        unbindService(mConnection);
    }

    public void addBook(View view) {
        if(isServiceBinded()) {
            Book book = new Book(startId, "Book From Client : " + startId);
            startId++;
            try {
                mBookManager.addBook(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllBook(View view) {
        if(isServiceBinded()) {
            try {
                List<Book> list = mBookManager.getAllBooks();
                Log.e(TAG, "getAllBook -> " + list.getClass().getCanonicalName());
                for (Book book : list) {
                    Log.e(TAG, "getAllBook -> " + book);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerListener(View view) {
        if(isServiceBinded()) {
            try {
                mBookManager.registerNewBookNotify(mNotify);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterListener(View view) {
        if(isServiceBinded()) {
            try {
                mBookManager.unregisterNewBookNotify(mNotify);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isServiceBinded() {
        return mBookManager != null;
    }

    public void showToastFromServer(View view) {
        if(null != mBookManager) {
            try {
                IBinder myTestBinder = mBookManager.getMyTestBinder();
                ITest.Stub.asInterface(myTestBinder).justDoIt("hahahahah");
            } catch (RemoteException e) {
                Toast.makeText(this, "exception catch", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else  {
            Toast.makeText(this, "mBookManager is null", Toast.LENGTH_SHORT).show();
        }
    }
}
