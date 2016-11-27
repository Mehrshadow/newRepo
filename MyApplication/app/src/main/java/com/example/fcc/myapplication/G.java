package com.example.fcc.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import ir.parsansoft.app.ihs.mobile.classes.*;
import ir.parsansoft.app.ihs.mobile.classes.Server.ConnectionType;
import ir.parsansoft.app.ihs.mobile.classes.ServiceServer.LocalBinder;

import java.util.Calendar;

public class G extends Application {

    public static Context context;
    public static LayoutInflater inflater;
    public static Activity currentActivity;
    public static Handler HANDLER = new Handler();
    public static String DIR_APP;
    public static String DIR_DATABASE_FOLDER;
    public static String DIR_DATABASE_FILE;
    public static String DIR_ASSETS_IMAGE_FOLDER;
    public static String DIR_IMAGE_ROOM;
    public static String DIR_IMAGE_SECTION;
    public static String DIR_IMAGE_NODE;
    public static String DIR_IMAGE_WEATHER;
    public static final long SERVER_SOCKET_RETRY_TIME = 10000;
    public static ConnectionType serverConnectionStatus = ConnectionType.NOT_CONNECTED;
    public static SQLiteDatabase dbObject;
    public static Message message;
    //public static int CurrentNotifyCount = 0;
    public static UI ui;
    public static final int DEFAULT_LANGUAGE_ID = 1;
    public static int CURRENT_LANGUAGE_ID;
    public static int ViewPageCurrentItem = 0;
    public static NotificationClass notify;
    public static Database.Setting.Struct setting;
    public static Translation T;
    public static ServiceServer serviceServer;
    public static BroadcastReceiverAlarmManager broadcastReciverAlarmManager;
    boolean mBounded;
    ServiceConnection serviceConnection;


    public static boolean isMute = false;


    @Override
    public void onCreate() {
        super.onCreate();
        G.log("G is initializing ...");
        context = this.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DIR_APP = "/data/data/" + context.getPackageName();
        DIR_DATABASE_FOLDER = DIR_APP + "/db/";
        DIR_DATABASE_FILE = G.DIR_DATABASE_FOLDER + "MobileDB.sqlite";
        DIR_ASSETS_IMAGE_FOLDER = "my_images/";
        DIR_IMAGE_ROOM = DIR_ASSETS_IMAGE_FOLDER + "icon/room/";
        DIR_IMAGE_SECTION = DIR_ASSETS_IMAGE_FOLDER + "icon/section/";
        DIR_IMAGE_NODE = DIR_ASSETS_IMAGE_FOLDER + "icon/node/";
        AssetsFolder.copyDatabase();
        Database.InitializeDB();
        setting = Database.Setting.select();
        CURRENT_LANGUAGE_ID = setting.languageID;
        T = new Translation();
        FontsOverride.changeFont();
        Intent mIntent = new Intent(this, ServiceServer.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBounded = false;
                serviceServer = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBounded = true;
                LocalBinder mLocalBinder = (LocalBinder) service;
                serviceServer = mLocalBinder.getServerInstance();
            }
        };
        bindService(mIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        ui = new UI();
        notify = new NotificationClass();
        checkNotify();
        broadcastReciverAlarmManager = new BroadcastReceiverAlarmManager();
        broadcastReciverAlarmManager.SetRepeatAlarm(0, Calendar.getInstance().getTimeInMillis(), SERVER_SOCKET_RETRY_TIME);
    }

    public static void checkNotify() {
        Database.Notify.Struct[] notifys = Database.Notify.select("Seen = 0");
        if (notifys != null) {
            ui.runOnNotifyChanged(notifys.length);
        } else {
            ui.runOnNotifyChanged(0);
        }
    }

    public final static void log(String logText) {
        Log.i("LOG", logText);
    }

    public final static void logE(String logText) {
        Log.e("LOG", logText);
    }

    public final static void printStackTrace(Exception exception) {
        exception.printStackTrace();
    }

    public final static void toast(final String text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    //    public static String getSentence(int sentenceID) {
    //        Database.Translation.Struct[] sentence;
    //        sentence = Database.Translation.select("langID=" + CURRENT_LANGUAGE_ID + " AND SentenseID=" + sentenceID);
    //        if (sentence == null)
    //        {
    //            sentence = Database.Translation.select("langID=" + DEFAULT_LANGUAGE_ID + " AND SentenseID=" + sentenceID);
    //            if (sentence == null)
    //                return "NT:" + sentenceID;
    //        }
    //        return sentence[0].sentenseText;
    //    }

    public static void sendMessageToServer(String message) {
        serviceServer.sendMessageToServer(message);
    }

    public static boolean isOnline() {
        //        if (serverConnectionStatus == ConnectionType.CONNECTED)
        //            return true;
        //        else
        //            return false;
        if (serviceServer == null)
            return false;
        return serviceServer.isServerOnline();
    }

    public static void stopServerCommunication() {
        serviceServer.stopServer();
        context.stopService(new Intent(context, ServiceServer.class));
    }

}
