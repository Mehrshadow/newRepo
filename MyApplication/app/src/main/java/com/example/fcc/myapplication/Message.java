package com.example.fcc.myapplication;

import android.app.Activity;
import android.content.Intent;
import ir.parsansoft.app.ihs.mobile.ActivityWizard;
import ir.parsansoft.app.ihs.mobile.DialogClass;
import ir.parsansoft.app.ihs.mobile.DialogClass.onOkListener;
import ir.parsansoft.app.ihs.mobile.G;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {

    enum MessageType {
        SwitchStatus,
        ScenarioStatus,
        Setting,
        SwitchData,
        ScenarioData,
        NodeData,
        SectionData,
        MobileData,
        RoomData,
        Notify,
        SyncData,
        RefreshData
    }

    enum MessageAction {
        Delete,
        Update,
        Insert,
        Failed
    }

    public static onScenarioActiveChanged mOnScenarioActiveChanged;
    public static onSectionChanged        mOnSectionChanged;
    public static onRefreshData           mOnRefreshData;

    public static class SendMessage {
        public static boolean setMessage(final String action, final Database.Scenario.Struct obj) {
            try {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String messageToSend = makeJsonTSendServer(action, obj);
                        G.sendMessageToServer(messageToSend);
                    }
                });
                thread.start();
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }

        public static boolean setMessage(final String action, final Database.Switch.Struct obj) {
            try {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String messageToSend = makeJsonTSendServer(action, obj);
                        G.sendMessageToServer(messageToSend);
                    }
                });
                thread.start();
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        private static String makeJsonTSendServer(String action, Database.Scenario.Struct scenario) {
            if (action == "Update") {
                JSONArray rootArray = new JSONArray();
                JSONObject rootObject = new JSONObject();
                try {
                    rootObject.put("MessageID ", 0);
                    rootObject.put("Action", "Update");
                    rootObject.put("Type", "ScenarioStatus");

                    rootObject.put("Date", formatDate());
                    JSONArray reciverArray = new JSONArray();
                    reciverArray.put(G.setting.mobileId);
                    JSONArray array = new JSONArray();
                    JSONObject jObject = new JSONObject();
                    if (scenario.run)
                        jObject.put("Active", 2);
                    else if (scenario.status == 1)
                        jObject.put("Active", 1);
                    else
                        jObject.put("Active", 0);
                    jObject.put("ID", scenario.iD);
                    array.put(jObject);
                    rootObject.put("ScenarioStatus", array);
                    rootObject.put("RecieverIDs", reciverArray);
                    rootArray.put(rootObject);
                    return rootArray.toString();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }
        private static String makeJsonTSendServer(String action, Database.Switch.Struct obj) {
            if (action == "Update") {
                JSONArray rootArray = new JSONArray();
                JSONObject rootObject = new JSONObject();
                try {
                    rootObject.put("MessageID ", 0);
                    rootObject.put("Action", "Update");
                    rootObject.put("Type", "SwitchStatus");
                    rootObject.put("Date", formatDate());
                    JSONArray reciverArray = new JSONArray();
                    reciverArray.put(G.setting.mobileId);
                    JSONArray array = new JSONArray();
                    JSONObject object = new JSONObject();
                    object.put("Value", obj.value);
                    object.put("ID", obj.iD);
                    array.put(object);
                    rootObject.put("SwitchStatus", array);
                    rootObject.put("RecieverIDs", reciverArray);
                    rootArray.put(rootObject);
                    return rootArray.toString();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }
        public static String makeSyncMessage() {
            JSONArray array = new JSONArray();
            JSONObject json = new JSONObject();
            try {
                json.put("MessageID ", 0);
                json.put("Type", "SyncData");
                json.put("Action", "Update");
                json.put("Date", formatDate());
                JSONArray reciverArray = new JSONArray();
                reciverArray.put(G.setting.mobileId);
                json.put("RecieverIDs", reciverArray);
                JSONObject obj = new JSONObject();
                obj.put("LastMessageID", G.setting.lastMessageID);
                obj.put("AppVerCode", Utility.getApplicationVersion());
                obj.put("LanguageID", G.setting.languageID);
                JSONArray objArray = new JSONArray();
                objArray.put(obj);
                json.put("SyncData", objArray);
                array.put(json);
                String message = array.toString();
                return message + "\n";
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        public static String makeGPSMessage() {
            JSONArray array = new JSONArray();
            JSONObject json = new JSONObject();
            try {
                json.put("MessageID ", 0);
                json.put("Type", "GPSAnnounce");
                json.put("Action", "Update");
                json.put("Date", formatDate());
                JSONArray reciverArray = new JSONArray();
                reciverArray.put(G.setting.mobileId);
                json.put("RecieverIDs", reciverArray);
                JSONObject obj = new JSONObject();
                obj.put("MobileID", G.setting.mobileId);
                obj.put("Latitude", G.setting.lat);
                obj.put("Longitude", G.setting.log);
                JSONArray objArray = new JSONArray();
                objArray.put(obj);
                json.put("GPSAnnounce", objArray);
                array.put(json);
                String message = array.toString();
                return message + "\n";
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        public static String makeRefreshData() {
            JSONArray array = new JSONArray();
            JSONObject json = new JSONObject();
            try {
                json.put("MessageID ", 0);
                json.put("Type", "RefreshData");
                json.put("Action", "Update");
                json.put("Date", formatDate());
                JSONArray reciverArray = new JSONArray();
                reciverArray.put(G.setting.mobileId);
                json.put("RecieverIDs", reciverArray);
                JSONObject obj = new JSONObject();
                json.put("Type", "RefreshData");
                json.put("MobileName", G.setting.customerName);
                json.put("ExKey", G.setting.exKey);
                JSONArray objArray = new JSONArray();
                objArray.put(obj);
                json.put("RefreshData", objArray);
                array.put(json);
                String message = array.toString();
                return message + "\n";
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

    }

    public static class ReceiveMessage {

        public static void getMessage(String message) {
            try {
                G.log("new message from server :\n" + message);
                MessageType type;
                MessageAction action;
                JSONArray rootArray = new JSONArray(message);
                JSONObject rootObject = rootArray.getJSONObject(0);
                type = MessageType.valueOf(rootObject.getString("Type"));
                action = MessageAction.valueOf(rootObject.getString("Action"));
                int messageID = rootObject.getInt("MessageID");
                G.setting.lastMessageID = messageID;
                Database.Setting.edit(G.setting);
                JSONArray array = rootObject.getJSONArray(rootObject.getString("Type"));
                JSONObject object = null;
                try {
                    object = array.getJSONObject(0);
                }
                catch (Exception e) {}

                switch (type) {
                    case SwitchStatus:
                        Switch(action, type, array);
                        break;
                    case ScenarioStatus:
                        Scenario(action, type, array);
                        break;
                    case Setting:
                        Setting(action, type, object);
                        break;
                    case SwitchData:
                        Switch(action, type, array);
                        break;
                    case ScenarioData:
                        Scenario(action, type, array);
                        break;
                    case NodeData:
                        Node(action, type, array);
                        break;
                    case SectionData:
                        Section(action, type, array);
                        break;
                    case RoomData:
                        Room(action, type, array);
                        break;
                    case SyncData:
                        SyncData(action, object);
                        break;
                    case Notify:
                        NotifyReceive(action, array, rootObject.getString("Date"));
                        break;
                    case MobileData:
                        MobileData(action, object);
                        break;
                    case RefreshData:
                        RefreshDataReceive(array);
                        break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        private static void RefreshDataReceive(JSONArray jsonA) {
            try {
                JSONObject json = jsonA.getJSONObject(0);
                JSONArray jsonSection = json.getJSONArray("Sections");
                JSONArray jsonRoom = json.getJSONArray("Rooms");
                JSONArray jsonNode = json.getJSONArray("Nodes");
                JSONArray jsonSwitch = json.getJSONArray("Switches");
                JSONArray jsonSenario = json.getJSONArray("Scenarios");
                JSONArray jsonSetting = json.getJSONArray("Setting");

                Database.Section.delete();
                Database.Room.delete();
                Database.Node.delete();
                Database.Switch.delete();
                Database.Scenario.delete();
                //Database.Notify.delete();

                for (int i = 0; i < jsonSection.length(); i++) {
                    JSONObject objSection = jsonSection.getJSONObject(i);
                    Database.Section.Struct obj = new Database.Section.Struct();
                    obj.iD = objSection.getInt("ID");
                    obj.name = objSection.getString("Name");
                    obj.sort = objSection.getInt("Sort");
                    obj.Icon = objSection.getString("Icon");
                    Database.Section.insert(obj);
                }
                G.log("ActivityRegister : Table Section Update");
                for (int i = 0; i < jsonRoom.length(); i++) {
                    JSONObject objRoom = jsonRoom.getJSONObject(i);
                    Database.Room.Struct obj = new Database.Room.Struct();
                    obj.iD = objRoom.getInt("ID");
                    obj.sectionID = objRoom.getInt("SectionID");
                    obj.name = objRoom.getString("Name");
                    obj.sort = objRoom.getInt("Sort");
                    obj.icon = objRoom.getString("Icon");
                    Database.Room.insert(obj);
                }
                G.log("ActivityRegister : Table Room Update");
                for (int i = 0; i < jsonNode.length(); i++) {
                    JSONObject objNode = jsonNode.getJSONObject(i);
                    Database.Node.Struct obj = new Database.Node.Struct();
                    obj.iD = objNode.getInt("ID");
                    obj.name = objNode.getString("Name");
                    obj.icon = objNode.getString("Icon");
                    obj.roomID = objNode.getInt("RoomID");
                    obj.nodeTypeID = objNode.getInt("NodeType");
                    obj.status = objNode.getInt("Status");
                    obj.isBookmark = objNode.getBoolean("IsFavorite");
                    Database.Node.insert(obj);
                }
                G.log("ActivityRegister : Table Node Update");
                for (int i = 0; i < jsonSwitch.length(); i++) {
                    JSONObject objSwitch = jsonSwitch.getJSONObject(i);
                    Database.Switch.Struct obj = new Database.Switch.Struct();
                    obj.iD = objSwitch.getInt("ID");
                    obj.name = objSwitch.getString("Name");
                    obj.value = objSwitch.getDouble("Value");
                    obj.code = objSwitch.getInt("Code");
                    obj.nodeID = objSwitch.getInt("NodeID");
                    Database.Switch.insert(obj);
                }
                G.log("ActivityRegister : Table Switch Update");
                for (int i = 0; i < jsonSenario.length(); i++) {
                    JSONObject objSenario = jsonSenario.getJSONObject(i);
                    Database.Scenario.Struct obj = new Database.Scenario.Struct();
                    obj.iD = objSenario.getInt("ID");
                    obj.name = objSenario.getString("Name");
                    obj.status = objSenario.getInt("Active");
                    String gpsString = objSenario.getString("GPS_Params");
                    if (gpsString.trim().length() != 0)
                    {
                        JSONObject gps = new JSONObject(gpsString);
                        obj.latitude = gps.getDouble("Latitude");
                        obj.longitude = gps.getDouble("Longitude");
                        obj.distance = gps.getDouble("Radius");
                    }
                    else
                    {
                        obj.latitude = 0;
                        obj.longitude = 0;
                        obj.distance = 0;
                    }
                    //obj.isStarted = objSenario.getBoolean("IsStarted");
                    obj.des = objSenario.getString("Des");
                    obj.description = objSenario.getString("DetailsDescription");
                    obj.result = objSenario.getString("DetailsResults");
                    obj.condition = objSenario.getString("DetailsConditions");
                    Database.Scenario.insert(obj);
                }
                G.log("ActivityRegister : Table Senario Update");
                for (int i = 0; i < jsonSetting.length(); i++) {
                    JSONObject objSetting = jsonSetting.getJSONObject(i);
                    G.setting.serverIP = objSetting.getString("ServerIP");
                    G.setting.customerId = objSetting.getInt("CustomerID");
                    G.setting.wiFiSSID = objSetting.getString("WiFiSSID");
                    G.setting.wiFiMac = objSetting.getString("WiFiMac");
                    G.setting.centerPort = objSetting.getInt("CenterPort");
                    G.setting.serverPort = objSetting.getInt("ServerPort");
                    G.setting.lastMessageID = objSetting.getInt("LastMessageID");
                    G.setting.languageID = G.CURRENT_LANGUAGE_ID;
                    G.setting.customerName = objSetting.getString("CustomerName");
                    G.setting.centerIP = objSetting.getString("CenterIP");
                    G.log("CenterIP:" + G.setting.centerIP);
                    Database.Setting.edit(G.setting);
                }
                G.log("ActivityRegister : Table Setting Update");
                G.ui.runRefreshAll();
            }
            catch (JSONException e) {
                e.printStackTrace();
                G.toast("ERROR : SYNCDATA");
            }
            if (mOnRefreshData != null)
                mOnRefreshData.onRefreshDataReceive();
        }
        private static void NotifyReceive(MessageAction action, JSONArray array, String date) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    Database.Notify.Struct notify = new Database.Notify.Struct();
                    notify.title = obj.getString("NotifyTitle");
                    notify.text = obj.getString("NotifyText");
                    notify.ring = obj.getInt("Ring");
                    notify.date = date;
                    Database.Notify.insert(notify);
                    G.log("Notify Insert");
                    G.checkNotify();
                    if (notify.ring == 2)
                        G.notify.raiseCustom(notify.title,notify.text,G.isMute);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        public static boolean Switch(MessageAction action, MessageType type, JSONArray array) {
            switch (type) {
                case SwitchStatus:
                    try {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            int SwitchId = obj.getInt("ID");
                            double Value = obj.getDouble("Value");
                            Database.Switch.Struct sw = Database.Switch.select(SwitchId);
                            if (sw != null) {
                                Database.Switch.edit(SwitchId, Value);
                                G.log("Update Switch");
                                G.ui.runOnNodeChanged(sw.nodeID);
                            }
                        }
                        return true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                case SwitchData:
                    try {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            int SwitchID = Integer.parseInt(obj.getString("ID"));
                            String SwitchName = obj.getString("Name");
                            int SwitchCode = Integer.parseInt(obj.getString("Code"));
                            double SwitchValue = Double.parseDouble(obj.getString("Value"));
                            int SwitchNodeID = Integer.parseInt(obj.getString("NodeID"));
                            switch (action) {
                                case Update:
                                    Database.Switch.edit(SwitchID, SwitchCode, SwitchName, SwitchValue, SwitchNodeID);
                                    G.log("Update Switch");
                                    break;
                                case Delete:
                                    G.log("Delete Switch - Query result :" + Database.Switch.delete(SwitchID));
                                    break;
                                case Insert:
                                    Database.Switch.insert(SwitchID, SwitchCode, SwitchName, SwitchValue, SwitchNodeID);
                                    G.log("Insert Switch");
                                    break;
                            }
                            G.ui.runOnNodeChanged(SwitchNodeID);
                        }
                        return true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
            }
            return false;
        }
        public static boolean Scenario(MessageAction action, MessageType type, JSONArray array) {
            switch (type) {
                case ScenarioStatus:
                    try {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            int ScenarioId = (obj.getInt("ID"));
                            int Status = obj.getInt("Active");
                            Database.Scenario.edit(ScenarioId, Status);
                        }
                        G.log("Update Senario");
                        if (mOnScenarioActiveChanged != null) {
                            mOnScenarioActiveChanged.onActiveChanged();
                        }
                        return true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }

                case ScenarioData:
                    try {

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            int ScenarioID = obj.getInt("ID");
                            int ScenarioStatus = obj.getInt("Active");
                            String ScenarioName = obj.getString("Name");
                            String ScenarioDes = obj.getString("Des");
                            String ScenarioDescription = obj.getString("DetailsDescription");
                            String ScenarioResult = obj.getString("DetailsResults");
                            String ScenarioCondition = obj.getString("DetailsConditions");
                            String gpsString = obj.getString("GPS_Params");
                            double latitude = 0;
                            double longitude = 0;
                            double distance = 0;

                            if (gpsString.trim().length() != 0)
                            {
                                JSONObject gps = new JSONObject(gpsString);
                                latitude = gps.getDouble("Latitude");
                                longitude = gps.getDouble("Longitude");
                                distance = gps.getDouble("Radius");
                            }
                            switch (action) {
                                case Update:
                                    Database.Scenario.edit(ScenarioID, ScenarioName, ScenarioStatus, latitude, longitude, distance, ScenarioDescription, ScenarioCondition, ScenarioResult, ScenarioDes);
                                    G.log("Update Senario");
                                    break;
                                case Delete:
                                    Database.Scenario.delete(ScenarioID);
                                    G.log("Delete Senario");
                                    break;
                                case Insert:
                                    Database.Scenario.insert(ScenarioID, ScenarioName, ScenarioStatus, latitude, longitude, distance, ScenarioDescription, ScenarioCondition, ScenarioResult, ScenarioDes);
                                    G.log("Insert Senario");
                                    break;
                            }
                        }
                        if (mOnScenarioActiveChanged != null) {
                            mOnScenarioActiveChanged.onActiveChanged();
                            G.toast("Raise Active Changed");
                        }
                        return true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
            }
            return false;
        }
        public static boolean Setting(MessageAction action, MessageType type, JSONObject obj) {
            try {

                String ServerIP = obj.getString("ServerIP");
                int ServerPort = Integer.parseInt(obj.getString("ServerPort"));
                String CenterIP = obj.getString("CenterIP");
                int CenterPort = Integer.parseInt(obj.getString("CenterPort"));
                String WiFiSSID = obj.getString("WiFiSSID");
                String WiFiMac = obj.getString("WiFiMac");
                switch (action) {
                    case Update:
                        G.setting.serverIP = ServerIP;
                        G.setting.serverPort = ServerPort;
                        G.setting.centerIP = CenterIP;
                        G.setting.centerPort = CenterPort;
                        G.setting.wiFiSSID = WiFiSSID;
                        G.setting.wiFiMac = WiFiMac;
                        Database.Setting.edit(G.setting);
                        G.log("Settings Update");
                        break;
                }
                return true;
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        public static boolean Node(MessageAction action, MessageType type, JSONArray array) {
            try {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    int NodeID = Integer.parseInt(obj.getString("ID"));
                    String NodeName = obj.getString("Name");
                    String NodeIcon = obj.getString("Icon");
                    int NodeStatus = Integer.parseInt(obj.getString("Status"));
                    int NodeRoomID = Integer.parseInt(obj.getString("RoomID"));
                    int NodeTypeID = Integer.parseInt(obj.getString("NodeTypeID"));
                    switch (action) {
                        case Update:
                            Database.Node.Struct node = Database.Node.select(NodeID);
                            boolean isBookmark = node.isBookmark;
                            Database.Node.edit(NodeID, NodeRoomID, NodeIcon, NodeStatus, NodeName, NodeTypeID, isBookmark);
                            G.log("Node Update");
                            break;
                        case Delete:
                            Database.Node.delete(NodeID);
                            G.log("Node Delete");
                            break;
                        case Insert:
                            Database.Node.insert(NodeID, NodeRoomID, NodeIcon, NodeStatus, NodeName, NodeTypeID, false);
                            G.log("Node Insert");
                            break;
                    }
                    G.ui.runOnNodeChanged(NodeID);
                }
                return true;
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        public static boolean Section(MessageAction action, MessageType type, JSONArray array) {
            try {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Database.Section.Struct section = new Database.Section.Struct();
                    section.iD = obj.getInt("ID");
                    section.name = obj.getString("Name");
                    section.Icon = obj.getString("Icon");
                    section.sort = obj.getInt("Sort");
                    switch (action) {
                        case Update:
                            Database.Section.edit(section);
                            G.log("Section Update");
                            break;
                        case Delete:
                            Database.Section.delete(section.iD);
                            G.log("Section Delete");
                            break;
                        case Insert:
                            Database.Section.insert(section);
                            G.log("Section Insert");
                            break;
                    }
                    G.ui.runOnSectionChanged(section.iD);
                }
                return true;
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        public static boolean Room(MessageAction action, MessageType type, JSONArray array) {
            try {

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    int RoomID = obj.getInt("ID");
                    String RoomName = obj.getString("Name");
                    String RoomIcon = obj.getString("Icon");
                    int SectionSort = obj.getInt("Sort");
                    int RoomSectionID = obj.getInt("SectionID");
                    switch (action) {
                        case Update:
                            Database.Room.edit(RoomID, RoomName, RoomIcon, SectionSort, RoomSectionID);
                            G.log("Room Update");
                            break;
                        case Delete:
                            Database.Room.delete(RoomID);
                            G.log("Room Delete");
                            break;
                        case Insert:
                            Database.Room.insert(RoomID, RoomName, RoomIcon, SectionSort, RoomSectionID);
                            G.log("Room Insert");
                            break;
                    }
                    G.ui.runOnSectionChanged(RoomSectionID);
                }
                return true;
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                G.log("" + e);
                return false;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        public static boolean SyncData(MessageAction action, JSONObject obj) {
            switch (action) {
                case Delete:
                    try {
                        String msgText = obj.getString("Message");
                        if (msgText.equals("Not Permitted")) {
                            G.stopServerCommunication();
                            G.setting.register = false;
                            Database.Setting.edit(G.setting);
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (G.currentActivity != null) {
                                        G.toast("Message Class : Socket Close IF " + G.currentActivity);

                                        DialogClass dlg = new DialogClass(G.currentActivity);
                                        dlg.setOnOkListner(new onOkListener() {
                                            @Override
                                            public void event() {
                                                Intent intent = new Intent(G.context, ActivityWizard.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                G.context.startActivity(intent);
                                                ((Activity) G.currentActivity).finish();
                                            }
                                        });
                                        dlg.showSimpleOkDialog("Unregister your phone");
                                    }
                                }
                            });

                        }
                    }
                    catch (Exception e) {
                        G.printStackTrace(e);
                    }
                    break;

                default:
                    break;
            }
            return false;
        }
        public static boolean MobileData(MessageAction action, JSONObject obj) {
            switch (action) {
                case Delete:
                    G.stopServerCommunication();
                    G.setting.register = false;
                    Database.Setting.edit(G.setting);
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            if (G.currentActivity != null) {
                                G.toast("Message Class : Socket Close IF " + G.currentActivity);
                                DialogClass dlg = new DialogClass(G.currentActivity);
                                dlg.setOnOkListner(new onOkListener() {
                                    @Override
                                    public void event() {
                                        Intent intent = new Intent(G.currentActivity, ActivityWizard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        G.currentActivity.startActivity(intent);
                                        ((Activity) G.currentActivity).finish();
                                    }
                                });
                                dlg.showSimpleOkDialog("Unregister your phone");
                            }
                        }
                    });
                    break;

                default:
                    break;
            }
            return false;
        }
    }

    public static boolean setMessage(final String action, final Database.Scenario.Struct obj) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (action == "Update") {
                        JSONArray rootArray = new JSONArray();
                        JSONObject rootObject = new JSONObject();
                        try {
                            rootObject.put("Action", "Update");
                            rootObject.put("Type", "ScenarioStatus");
                            JSONArray array = new JSONArray();
                            JSONObject object = new JSONObject();
                            object.put("Active", obj.status);
                            object.put("ID", obj.iD);
                            array.put(object);
                            rootObject.put("ScenarioStatus", array);
                            rootArray.put(rootObject);
                            G.sendMessageToServer(rootArray.toString() + "\n");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean setMessage(final String action, final Database.Switch.Struct obj) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (action == "Update") {
                        JSONArray rootArray = new JSONArray();
                        JSONObject rootObject = new JSONObject();
                        try {
                            rootObject.put("Action", "Update");
                            rootObject.put("Type", "SwitchStatus");
                            JSONArray array = new JSONArray();
                            JSONObject object = new JSONObject();
                            object.put("Value", obj.value);
                            object.put("SwitchId", obj.iD);
                            array.put(object);
                            rootObject.put("SwitchStatus", array);
                            rootArray.put(rootObject);
                            G.sendMessageToServer(rootArray.toString() + "\n");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static String formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String time = sdf.format(new Date());
        return time;
    }


    public interface onScenarioActiveChanged {
        void onActiveChanged();
    }

    public interface onSectionChanged {
        void onSectionChanged();
    }

    public interface onRefreshData {
        void onRefreshDataReceive();
    }

    public static void setOnRefreshDataReceive(onRefreshData lis) {
        mOnRefreshData = lis;
    }

    public static void setOnScenarioActiveChanged(onScenarioActiveChanged listener) {
        mOnScenarioActiveChanged = listener;
    }

    public static void setOnSectionChanged(onSectionChanged listener) {
        mOnSectionChanged = listener;
    }
}
