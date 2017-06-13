package com.essejose.bind_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

/**
 * Created by essejose on 12/06/2017.
 */

public class BoundService extends Service{

    private  static String LOG_TAG = "BoundService";
    private IBinder mBinder = new MyBinder();
    private Chronometer mChrometer;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG,"in onCreate");
        mChrometer = new Chronometer(this);
        mChrometer.setBase(SystemClock.elapsedRealtime());
        mChrometer.start();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG,"in onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG,"in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG,"in onUnbind");
        return  true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG,"in onDestroy");
        mChrometer.stop();
    }

    public  String getTimestamp(){
        int n = 3600000;
        int n2 = 60000;
        long elapsedMillis = SystemClock.elapsedRealtime()
                - mChrometer.getBase();
        int hours = (int) (elapsedMillis / n);
        int minutes = (int) (elapsedMillis -
                hours * n ) / n2;
        int seconds = (int) (elapsedMillis - hours *
                    n - minutes * n2) / 1000;
        int millis  = (int) ( elapsedMillis - hours * n -
                    minutes * n2 - seconds * 1000);

        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }
    public  class  MyBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }
}
