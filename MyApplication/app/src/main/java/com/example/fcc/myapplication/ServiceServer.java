package com.example.fcc.myapplication;

import ir.parsansoft.app.ihs.mobile.G;
import ir.parsansoft.app.ihs.mobile.classes.Server.ConnectionType;
import ir.parsansoft.app.ihs.mobile.classes.Server.OnServerEvent;
import ir.parsansoft.app.ihs.mobile.classes.Utility.NetworkType;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ServiceServer extends Service {
    Server  server;
    IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
//        G.toast("Run ServiceServer");
        server = new Server();
        server.connectServer();
        server.setOnServerDataRecieve(new OnServerEvent() {
            @Override
            public void onDataReceived(String Data) {
                // /////Parse Message///////////////////
                Message.ReceiveMessage.getMessage(Data);
                //G.toast(Data);
            }

            @Override
            public void onDisconnect() {
                G.serverConnectionStatus = ConnectionType.NOT_CONNECTED;
                G.ui.runOnServerStatusChanged(NetworkType.NO_NETWORK);
            }

            @Override
            public void onConnect(NetworkType netType) {
                G.serverConnectionStatus = ConnectionType.CONNECTED;
                G.ui.runOnServerStatusChanged(netType);
            }
        });
    }

    public class LocalBinder extends Binder {
        public ServiceServer getServerInstance() {
            return ServiceServer.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void sendMessageToServer(String message) {
        server.sendMessage(message);
    }

    public void startServer() {
        server.connectServer();
    }

    public void stopServer() {
        server.stop();
    }
    public boolean isServerOnline() {
        if (server.connectionStatus == ConnectionType.CONNECTED)
            return true;
        return false;
    }
}
