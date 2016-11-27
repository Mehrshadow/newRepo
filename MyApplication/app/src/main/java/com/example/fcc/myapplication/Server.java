package com.example.fcc.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Server {

    //private Socket socketToServer;
    private DataOutputStream outputStream;
    private Socket socketToServer;
    private BufferedReader inputStream;
    //    public NetworkType networkType;
    public ConnectionType connectionStatus; // 0 = Disconnected , 1 = Connected ,  2 = Connecting
    private boolean loop;
    ImageView img;
    private static int cclCounter = 0;
    private OnServerEvent mOnServerevent;
    Thread tr;

    Context context;

    String serverIP, exKey;
    int mobilePort, customerId, personId;

    public interface OnServerEvent {
        void onDataReceived(String Data);

        void onDisconnect();

//        void onConnect(NetworkType netType);
    }

    public enum ConnectionType {
        NOT_CONNECTED, CONNECTED, TRY_CONNECTING;
    }

    public void setOnServerDataRecieve(OnServerEvent eventListener) {
        mOnServerevent = eventListener;
    }

    public Server(String ipAdr, String mobilePort, String exKey, String customerId, String personId, Context context) {
        this.serverIP = ipAdr;
        this.mobilePort = Integer.parseInt(mobilePort);
        this.exKey = exKey;
        this.customerId = Integer.parseInt(customerId);
        this.personId = Integer.parseInt(personId);
        this.context = context;

        connectionStatus = ConnectionType.NOT_CONNECTED;
    }

    public boolean isConnected() {
        if (connectionStatus == ConnectionType.CONNECTED)
            return true;
        else
            return false;
    }

    public void connectServer() {
        log("connect to server called ... loop:" + loop + " connectionStatus:" + connectionStatus.name() + " Setting register:");
        if (!loop && connectionStatus == ConnectionType.NOT_CONNECTED) {
            tr = new Thread() { // Read Data
                @Override
                public void run() {
                    Log.d("SERVER", "Server is creating a new thread to connect !");
                    loop = true;
                    while (loop) {
                        try {
                            socketToServer = new Socket();
                            log("ConnectToServer : Try to Connect");
                            boolean isConnected = Utility.isNetworkAvailable(context);
                            if (!isConnected) {
                                loop = false;
                                break;
                            }
                            if (isConnected) {
                                log("Connecting to server : " + serverIP + " : " + mobilePort);
                                socketToServer.connect(new InetSocketAddress(serverIP, mobilePort), 5000);
                                if (socketToServer.isConnected()) {
                                    log("socket connected!");
                                }
                            }

                            inputStream = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
                            outputStream = new DataOutputStream(socketToServer.getOutputStream());

                            JSONObject jsonIntroduce = new JSONObject();
                            jsonIntroduce.put("customerId", customerId);
                            jsonIntroduce.put("personId", personId);
                            jsonIntroduce.put("exKey", exKey);

                            String introduce = "{\"customerId\":" + customerId + ",\"personId\":" + personId + ",\"exKey\":" + "\"" + exKey + "\"" + "}\n";
                            log(jsonIntroduce.toString());
                            log(introduce);

//                            String introduce = "[{\"MobileID\":" + G.setting.mobileId + ",\"CustomerID\":" + G.setting.customerId + ",\"ExKey\":" + "\"" + G.setting.exKey + "\"" + "}]\n";
                            outputStream.write(introduce.getBytes());
                            outputStream.flush();

                            String syncMsg = Message.SendMessage.makeSyncMessage();

                            StartListen();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            log("connect to server loop goes to finaly !");
                            if (connectionStatus == ConnectionType.CONNECTED) {
                                connectionStatus = ConnectionType.NOT_CONNECTED;
                                mOnServerevent.onDisconnect();
                            }
                            connectionStatus = ConnectionType.NOT_CONNECTED;
                            try {
                                if (socketToServer != null && !socketToServer.isClosed())
                                    socketToServer.close();
                                socketToServer = null;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                log("Before sleep ................!");
                                Thread.sleep(3000);
                                log("After sleep ................!");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } // End while (loop)
                }
            }; // Thread
            if (tr.isAlive()) {
                try {
                    socketToServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tr.interrupt();
            }
            tr.start();
        }
    }

    private void StartListen() {
        log("Connected");
        int retry = 0;
        while (true) {
            if (socketToServer.isClosed()) {
                break;
            }
            String message = "";
            try {
                log("socket is Waiting for data");
                message = inputStream.readLine();
                log("Read this message from socket:" + message);
                if (message == null)
                    if (retry < 3)
                        retry++;
                    else
                        throw new IOException("Data is null.Socket should close.");
                if (message != null && message.length() > 0) {
                    if (mOnServerevent != null)
                        mOnServerevent.onDataReceived(message);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                try {
                    socketToServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void stop() {
        loop = false;
        try {
            log("Stop try");
            if (socketToServer != null)
                socketToServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMessage(String data) {
        log("Sending data to server :\r\n" + data);
        if (outputStream == null) {
            log("outputStream is null");
            return false;
        }
        try {
            outputStream.write((data + "\n").getBytes("UTF-8"));
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socketToServer.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return false;
        }
    }


    private void log(String text) {
        Log.e("SERVER", text);
    }

}
