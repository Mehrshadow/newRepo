package com.example.fcc.myapplication;

import ir.parsansoft.app.ihs.mobile.G;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {

    public static void InitializeDB() {
        try {

            String myDBFILE = G.DIR_DATABASE_FILE;
            G.dbObject = SQLiteDatabase.openOrCreateDatabase(myDBFILE, null);

        }
        catch (Exception e) {
            G.printStackTrace(e);
        }
    }

    public static class Room {
        public static class Struct {
            public int    iD        = 0;
            public String name      = "";
            public String icon      = "";
            public int    sort      = 0;
            public int    sectionID = 0;
        }

        public static long insert(Struct myRoom) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myRoom.iD);
            Values.put("Name", myRoom.name);
            Values.put("Icon", myRoom.icon);
            Values.put("Sort", myRoom.sort);
            Values.put("SectionID", myRoom.sectionID);
            return G.dbObject.insert("T_Room", null, Values);
        }

        public static long insert(int iD, String name, String icon, int sort, int sectionID) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Name", name);
            Values.put("Icon", icon);
            Values.put("Sort", sort);
            Values.put("SectionID", sectionID);
            return G.dbObject.insert("T_Room", null, Values);
        }

        public static int edit(Struct myRoom) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myRoom.iD);
            Values.put("Name", myRoom.name);
            Values.put("Icon", myRoom.icon);
            Values.put("Sort", myRoom.sort);
            Values.put("SectionID", myRoom.sectionID);
            return G.dbObject.update("T_Room", Values, "ID=" + myRoom.iD, null);
        }

        public static int edit(int iD, String name, String icon,
                               int sort, int sectionID) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Name", name);
            Values.put("Icon", icon);
            Values.put("Sort", sort);
            Values.put("SectionID", sectionID);
            return G.dbObject.update("T_Room", Values, "ID=" + iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Room", "ID=" + iD, null);
        }

        public static int delete() {
            return G.dbObject.delete("T_Room", "", null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery(
                    "SELECT * FROM T_Room WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.icon = cursor.getString(cursor
                        .getColumnIndex("Icon"));
                selectedRow.sort = cursor.getInt(cursor.getColumnIndex("Sort"));
                selectedRow.sectionID = cursor.getInt(cursor
                        .getColumnIndex("SectionID"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Room"
                    + whereQuery, null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.icon = cursor.getString(cursor.getColumnIndex("Icon"));
                selectedRow.sort = cursor.getInt(cursor.getColumnIndex("Sort"));
                selectedRow.sectionID = cursor.getInt(cursor
                        .getColumnIndex("SectionID"));
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Room"
                    + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1",
                    null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.icon = cursor.getString(cursor
                        .getColumnIndex("Icon"));
                selectedRow.sort = cursor.getInt(cursor.getColumnIndex("Sort"));
                selectedRow.sectionID = cursor.getInt(cursor
                        .getColumnIndex("SectionID"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

    public static class Translation {
        public static class Struct {
            public int    iD           = 0;
            public int    langID       = 0;
            public int    sentenseID   = 0;
            public String sentenseText = "";
        }

        public static long insert(Struct myTranslation) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myTranslation.iD);
            Values.put("LangID", myTranslation.langID);
            Values.put("SentenseID", myTranslation.sentenseID);
            Values.put("SentenseText", myTranslation.sentenseText);
            return G.dbObject.insert("T_Translation", null, Values);
        }

        public static long insert(int iD, int langID, int sentenseID,
                                  String sentenseText) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("LangID", langID);
            Values.put("SentenseID", sentenseID);
            Values.put("SentenseText", sentenseText);
            return G.dbObject.insert("T_Translation", null, Values);
        }

        public static int edit(Struct myTranslation) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myTranslation.iD);
            Values.put("LangID", myTranslation.langID);
            Values.put("SentenseID", myTranslation.sentenseID);
            Values.put("SentenseText", myTranslation.sentenseText);
            return G.dbObject.update("T_Translation", Values, "ID="
                    + myTranslation.iD, null);
        }

        public static int edit(int iD, int langID, int sentenseID,
                               String sentenseText) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("LangID", langID);
            Values.put("SentenseID", sentenseID);
            Values.put("SentenseText", sentenseText);
            return G.dbObject.update("T_Translation", Values, "ID=" + iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Translation", "ID=" + iD, null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery(
                    "SELECT * FROM T_Translation WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.langID = cursor.getInt(cursor
                        .getColumnIndex("LangID"));
                selectedRow.sentenseID = cursor.getInt(cursor
                        .getColumnIndex("SentenseID"));
                selectedRow.sentenseText = cursor.getString(cursor
                        .getColumnIndex("SentenseText"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Translation"
                    + whereQuery, null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.langID = cursor.getInt(cursor
                        .getColumnIndex("LangID"));
                selectedRow.sentenseID = cursor.getInt(cursor
                        .getColumnIndex("SentenseID"));
                selectedRow.sentenseText = cursor.getString(cursor
                        .getColumnIndex("SentenseText"));
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Translation"
                    + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1",
                    null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.langID = cursor.getInt(cursor
                        .getColumnIndex("LangID"));
                selectedRow.sentenseID = cursor.getInt(cursor
                        .getColumnIndex("SentenseID"));
                selectedRow.sentenseText = cursor.getString(cursor
                        .getColumnIndex("SentenseText"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

    public static class Language {
        public static class Struct {
            public int     iD   = 0;
            public String  name = "";
            public String  icon = "";
            public boolean rTL  = false;
        }

        public static long insert(Struct myLanguage) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myLanguage.iD);
            Values.put("Name", myLanguage.name);
            Values.put("Icon", myLanguage.icon);
            Values.put("RTL", myLanguage.rTL);
            return G.dbObject.insert("T_Language", null, Values);
        }

        public static long insert(int iD, String name, String icon, boolean rTL) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Name", name);
            Values.put("Icon", icon);
            Values.put("RTL", rTL);
            return G.dbObject.insert("T_Language", null, Values);
        }

        public static int edit(Struct myLanguage) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myLanguage.iD);
            Values.put("Name", myLanguage.name);
            Values.put("Icon", myLanguage.icon);
            Values.put("RTL", myLanguage.rTL);
            return G.dbObject.update("T_Language", Values, "ID="
                    + myLanguage.iD, null);
        }

        public static int edit(int iD, String name, String icon, boolean rTL) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Name", name);
            Values.put("Icon", icon);
            Values.put("RTL", rTL);
            return G.dbObject.update("T_Language", Values, "ID=" + iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Language", "ID=" + iD, null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery(
                    "SELECT * FROM T_Language WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.icon = cursor.getString(cursor
                        .getColumnIndex("Icon"));
                selectedRow.rTL = cursor.getInt(cursor.getColumnIndex("RTL")) != 0;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Language"
                    + whereQuery, null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.icon = cursor.getString(cursor
                        .getColumnIndex("Icon"));
                selectedRow.rTL = cursor.getInt(cursor.getColumnIndex("RTL")) != 0;
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Language"
                    + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1",
                    null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.icon = cursor.getString(cursor.getColumnIndex("Icon"));
                selectedRow.rTL = cursor.getInt(cursor.getColumnIndex("RTL")) != 0;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

    public static class Scenario {
        public static class Struct {
            public int     iD          = 0;
            public boolean run         = false;
            public String  name        = "";
            public int     status      = 0;
            public double  latitude    = 0;
            public double  longitude   = 0;
            public double  distance    = 0;
            public String  description = "";
            public String  result      = "";
            public String  condition   = "";
            public String  des         = "";
        }

        public static long insert(Struct mySenario) {
            ContentValues Values = new ContentValues();
            Values.put("ID", mySenario.iD);
            Values.put("Name", mySenario.name);
            Values.put("Status", mySenario.status);
            Values.put("Lat", mySenario.latitude);
            Values.put("Long", mySenario.longitude);
            Values.put("Distance", mySenario.distance);
            Values.put("Description", mySenario.description);
            Values.put("Result", mySenario.result);
            Values.put("Condition", mySenario.condition);
            Values.put("Des", mySenario.des);
            return G.dbObject.insert("T_Senario", null, Values);
        }

        public static long insert(int iD, String name, int status, double latitude, double longitude, double distance, String description, String condition, String result, String des) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Name", name);
            Values.put("Status", status);
            Values.put("Lat", latitude);
            Values.put("Long", longitude);
            Values.put("Distance", distance);
            Values.put("Description", description);
            Values.put("Result", result);
            Values.put("Condition", condition);
            Values.put("Des", des);
            return G.dbObject.insert("T_Senario", null, Values);
        }

        public static int edit(Struct mySenario) {
            ContentValues Values = new ContentValues();
            Values.put("ID", mySenario.iD);
            Values.put("Name", mySenario.name);
            Values.put("Status", mySenario.status);
            Values.put("Lat", mySenario.latitude);
            Values.put("Long", mySenario.longitude);
            Values.put("Distance", mySenario.distance);
            Values.put("Description", mySenario.description);
            Values.put("Result", mySenario.result);
            Values.put("Condition", mySenario.condition);
            Values.put("Des", mySenario.des);
            return G.dbObject.update("T_Senario", Values, "ID=" + mySenario.iD, null);
        }

        public static int edit(int iD, String name, int status, double latitude, double longitude, double distance, String description, String condition, String result, String des) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Name", name);
            Values.put("Status", status);
            Values.put("Lat", latitude);
            Values.put("Long", longitude);
            Values.put("Distance", distance);
            Values.put("Description", description);
            Values.put("Result", result);
            Values.put("Condition", condition);
            Values.put("Des", des);
            return G.dbObject.update("T_Senario", Values, "ID=" + iD, null);
        }
        public static int edit(int iD, int Status) {
            ContentValues Values = new ContentValues();
            Values.put("Status", Status);
            return G.dbObject.update("T_Senario", Values, "ID=" + iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Senario", "ID=" + iD, null);
        }
        public static int delete() {
            return G.dbObject.delete("T_Senario", "", null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Senario WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.status = cursor.getInt(cursor.getColumnIndex("Status"));
                selectedRow.latitude = cursor.getDouble(cursor.getColumnIndex("Lat"));
                selectedRow.longitude = cursor.getDouble(cursor.getColumnIndex("Long"));
                selectedRow.distance = cursor.getDouble(cursor.getColumnIndex("Distance"));
                selectedRow.description = cursor.getString(cursor.getColumnIndex("Description"));
                selectedRow.result = cursor.getString(cursor.getColumnIndex("Result"));
                selectedRow.condition = cursor.getString(cursor.getColumnIndex("Condition"));
                selectedRow.des = cursor.getString(cursor.getColumnIndex("Des"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Senario"
                    + whereQuery, null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.status = cursor.getInt(cursor.getColumnIndex("Status"));
                selectedRow.latitude = cursor.getDouble(cursor.getColumnIndex("Lat"));
                selectedRow.longitude = cursor.getDouble(cursor.getColumnIndex("Long"));
                selectedRow.distance = cursor.getDouble(cursor.getColumnIndex("Distance"));
                selectedRow.description = cursor.getString(cursor.getColumnIndex("Description"));
                selectedRow.result = cursor.getString(cursor.getColumnIndex("Result"));
                selectedRow.condition = cursor.getString(cursor.getColumnIndex("Condition"));
                selectedRow.des = cursor.getString(cursor.getColumnIndex("Des"));
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Senario"
                    + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1",
                    null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.status = cursor.getInt(cursor.getColumnIndex("Status"));
                selectedRow.latitude = cursor.getDouble(cursor.getColumnIndex("Lat"));
                selectedRow.longitude = cursor.getDouble(cursor.getColumnIndex("Long"));
                selectedRow.distance = cursor.getDouble(cursor.getColumnIndex("Distance"));
                selectedRow.description = cursor.getString(cursor.getColumnIndex("Description"));
                selectedRow.result = cursor.getString(cursor.getColumnIndex("Result"));
                selectedRow.condition = cursor.getString(cursor.getColumnIndex("Condition"));
                selectedRow.des = cursor.getString(cursor.getColumnIndex("Des"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

    public static class Node {
        public static class Struct {
            public int     iD         = 0;
            public int     roomID     = 0;
            public String  icon       = "";
            public int     status     = 0;
            public int     nodeTypeID = 0;
            public String  name       = "";
            public boolean isBookmark = false;
        }

        public static long insert(Struct myNode) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myNode.iD);
            Values.put("RoomID", myNode.roomID);
            Values.put("Icon", myNode.icon);
            Values.put("Status", myNode.status);
            Values.put("NodeTypeID", myNode.nodeTypeID);
            Values.put("Name", myNode.name);
            Values.put("IsBookmark", myNode.isBookmark);
            return G.dbObject.insert("T_Node", null, Values);
        }

        public static long insert(int iD, int roomID, String icon, int status,
                                  String name, int nodeTypeID, boolean isBookmark) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("RoomID", roomID);
            Values.put("Icon", icon);
            Values.put("Status", status);
            Values.put("NodeTypeID", nodeTypeID);
            Values.put("Name", name);
            Values.put("IsBookmark", isBookmark);
            return G.dbObject.insert("T_Node", null, Values);
        }

        public static int edit(Struct myNode) {
            ContentValues Values = new ContentValues();
            Values.put("ID", myNode.iD);
            Values.put("RoomID", myNode.roomID);
            Values.put("Icon", myNode.icon);
            Values.put("NodeTypeID", myNode.nodeTypeID);
            Values.put("Status", myNode.status);
            Values.put("Name", myNode.name);
            Values.put("IsBookmark", myNode.isBookmark);
            return G.dbObject.update("T_Node", Values, "ID=" + myNode.iD, null);
        }

        public static int edit(int iD, int roomID, String icon,
                               int status, String name, int nodeTypeID, boolean isBookmark) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("RoomID", roomID);
            Values.put("Icon", icon);
            Values.put("Status", status);
            Values.put("Name", name);
            Values.put("NodeTypeID", nodeTypeID);
            Values.put("IsBookmark", isBookmark);
            return G.dbObject.update("T_Node", Values, "ID=" + iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Node", "ID=" + iD, null);
        }
        public static int delete() {
            return G.dbObject.delete("T_Node", "", null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery(
                    "SELECT * FROM T_Node WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.roomID = cursor.getInt(cursor
                        .getColumnIndex("RoomID"));
                selectedRow.icon = cursor.getString(cursor
                        .getColumnIndex("Icon"));
                selectedRow.status = cursor.getInt(cursor
                        .getColumnIndex("Status"));
                selectedRow.nodeTypeID = cursor.getInt(cursor
                        .getColumnIndex("NodeTypeID"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.isBookmark = cursor.getInt(cursor
                        .getColumnIndex("IsBookmark")) != 0;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Node"
                    + whereQuery, null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.roomID = cursor.getInt(cursor
                        .getColumnIndex("RoomID"));
                selectedRow.icon = cursor.getString(cursor
                        .getColumnIndex("Icon"));
                selectedRow.status = cursor.getInt(cursor
                        .getColumnIndex("Status"));
                selectedRow.nodeTypeID = cursor.getInt(cursor
                        .getColumnIndex("NodeTypeID"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.isBookmark = cursor.getInt(cursor
                        .getColumnIndex("IsBookmark")) != 0;
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Node"
                    + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1",
                    null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.roomID = cursor.getInt(cursor
                        .getColumnIndex("RoomID"));
                selectedRow.icon = cursor.getString(cursor
                        .getColumnIndex("Icon"));
                selectedRow.status = cursor.getInt(cursor
                        .getColumnIndex("Status"));
                selectedRow.nodeTypeID = cursor.getInt(cursor
                        .getColumnIndex("NodeTypeID"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.isBookmark = cursor.getInt(cursor
                        .getColumnIndex("IsBookmark")) != 0;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

    public static class Switch {
        public static class Struct {
            public int    iD     = 0;
            public int    code   = 0;
            public String name   = "";
            public double value  = 0;
            public int    nodeID = 0;
        }

        public static long insert(Struct mySwitch) {
            ContentValues Values = new ContentValues();
            Values.put("ID", mySwitch.iD);
            Values.put("Code", mySwitch.code);
            Values.put("Name", mySwitch.name);
            Values.put("Value", mySwitch.value);
            Values.put("NodeID", mySwitch.nodeID);
            return G.dbObject.insert("T_Switch", null, Values);
        }

        public static long insert(int iD, int code, String name, double value,
                                  int nodeID) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Code", code);
            Values.put("Name", name);
            Values.put("Value", value);
            Values.put("NodeID", nodeID);
            return G.dbObject.insert("T_Switch", null, Values);
        }

        public static int edit(Struct mySwitch) {
            ContentValues Values = new ContentValues();
            Values.put("ID", mySwitch.iD);
            Values.put("Code", mySwitch.code);
            Values.put("Name", mySwitch.name);
            Values.put("Value", mySwitch.value);
            Values.put("NodeID", mySwitch.nodeID);
            return G.dbObject.update("T_Switch", Values, "ID=" + mySwitch.iD,
                    null);
        }

        public static int edit(int iD, int code, String name,
                               double value, int nodeID) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Code", code);
            Values.put("Name", name);
            Values.put("Value", value);
            Values.put("NodeID", nodeID);
            return G.dbObject.update("T_Switch", Values, "ID=" + iD, null);
        }
        public static int edit(int iD, double value) {
            ContentValues Values = new ContentValues();
            Values.put("Value", value);
            return G.dbObject.update("T_Switch", Values, "ID=" + iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Switch", "ID=" + iD, null);
        }
        public static int delete() {
            return G.dbObject.delete("T_Switch", "", null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery(
                    "SELECT * FROM T_Switch WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.code = cursor.getInt(cursor.getColumnIndex("Code"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.value = cursor.getDouble(cursor
                        .getColumnIndex("Value"));
                selectedRow.nodeID = cursor.getInt(cursor
                        .getColumnIndex("NodeID"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Switch"
                    + whereQuery, null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.code = cursor.getInt(cursor.getColumnIndex("Code"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.value = cursor.getDouble(cursor
                        .getColumnIndex("Value"));
                selectedRow.nodeID = cursor.getInt(cursor
                        .getColumnIndex("NodeID"));
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Switch"
                    + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1",
                    null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.code = cursor.getInt(cursor.getColumnIndex("Code"));
                selectedRow.name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                selectedRow.value = cursor.getDouble(cursor
                        .getColumnIndex("Value"));
                selectedRow.nodeID = cursor.getInt(cursor
                        .getColumnIndex("NodeID"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

    public static class Section {
        public static class Struct {
            public int    iD   = 0;
            public String Icon = "";
            public String name = "";
            public int    sort = 0;
        }

        public static long insert(Struct mySection) {
            ContentValues Values = new ContentValues();
            Values.put("ID", mySection.iD);
            Values.put("Icon", mySection.Icon);
            Values.put("Name", mySection.name);
            Values.put("Sort", mySection.sort);
            return G.dbObject.insert("T_Section", null, Values);
        }

        public static long insert(int iD, String Icon, String name, int sort) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Icon", Icon);
            Values.put("Name", name);
            Values.put("Sort", sort);
            return G.dbObject.insert("T_Section", null, Values);
        }

        public static int edit(Struct mySection) {
            ContentValues Values = new ContentValues();
            Values.put("ID", mySection.iD);
            Values.put("Icon", mySection.Icon);
            Values.put("Name", mySection.name);
            Values.put("Sort", mySection.sort);
            return G.dbObject.update("T_Section", Values, "ID=" + mySection.iD,
                    null);
        }

        public static int edit(int iD, String Icon, String name, int sort) {
            ContentValues Values = new ContentValues();
            Values.put("ID", iD);
            Values.put("Icon", Icon);
            Values.put("Name", name);
            Values.put("Sort", sort);
            return G.dbObject.update("T_Section", Values, "ID=" + iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Section", "ID=" + iD, null);
        }
        public static int delete() {
            return G.dbObject.delete("T_Section", "", null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery(
                    "SELECT * FROM T_Section WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.Icon = cursor.getString(cursor.getColumnIndex("Icon"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.sort = cursor.getInt(cursor.getColumnIndex("Sort"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Section"
                    + whereQuery, null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.Icon = cursor.getString(cursor.getColumnIndex("Icon"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.sort = cursor.getInt(cursor.getColumnIndex("Sort"));
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Section" + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1",
                    null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.Icon = cursor.getString(cursor.getColumnIndex("Icon"));
                selectedRow.name = cursor.getString(cursor.getColumnIndex("Name"));
                selectedRow.sort = cursor.getInt(cursor.getColumnIndex("Sort"));
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

    public static class Setting {
        public static class Struct {
            public int     id            = 1;
            public String  serverIP      = "";
            public int     serverPort    = 0;
            public String  centerIP      = "";
            public int     centerPort    = 0;
            public int     customerId    = 0;
            public int     languageID    = 0;
            public String  wiFiSSID      = "";
            public String  wiFiMac       = "";
            public long    lastMessageID = 0;
            public String  exKey         = "";
            public String  customerName  = "";
            public int     mobileId      = 0;
            public double  lat           = 0;
            public double  log           = 0;
            public boolean register      = false;


        }

        public static int edit(Struct mySetting) {
            ContentValues Values = new ContentValues();
            Values.put("ServerIP", mySetting.serverIP);
            Values.put("ServerPort", mySetting.serverPort);
            Values.put("CustomerId", mySetting.customerId);
            Values.put("MobileID", mySetting.mobileId);
            Values.put("LanguageID", mySetting.languageID);
            Values.put("WiFiSSID", mySetting.wiFiSSID);
            Values.put("WiFiMac", mySetting.wiFiMac);
            Values.put("CenterIP", mySetting.centerIP);
            Values.put("CenterPort", mySetting.centerPort);
            Values.put("LastMessageID", mySetting.lastMessageID);
            Values.put("ExKey", mySetting.exKey);
            Values.put("CustomerName", mySetting.customerName);
            Values.put("Latitude", mySetting.lat);
            Values.put("Longitude", mySetting.log);
            Values.put("Register", mySetting.register);
            return G.dbObject.update("T_Setting", Values, "ID=" + mySetting.id, null);
        }

        public static Struct select() {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Setting WHERE ID=1", null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.id = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.serverIP = cursor.getString(cursor.getColumnIndex("ServerIP"));
                selectedRow.serverPort = cursor.getInt(cursor.getColumnIndex("ServerPort"));
                selectedRow.customerId = cursor.getInt(cursor.getColumnIndex("CustomerId"));
                selectedRow.mobileId = cursor.getInt(cursor.getColumnIndex("MobileID"));
                selectedRow.languageID = cursor.getInt(cursor.getColumnIndex("LanguageID"));
                selectedRow.wiFiSSID = cursor.getString(cursor.getColumnIndex("WiFiSSID"));
                selectedRow.wiFiMac = cursor.getString(cursor.getColumnIndex("WiFiMac"));
                selectedRow.centerIP = cursor.getString(cursor.getColumnIndex("CenterIP"));
                selectedRow.centerPort = cursor.getInt(cursor.getColumnIndex("CenterPort"));
                selectedRow.lastMessageID = cursor.getLong(cursor.getColumnIndex("LastMessageID"));
                selectedRow.exKey = cursor.getString(cursor.getColumnIndex("ExKey"));
                selectedRow.customerName = cursor.getString(cursor.getColumnIndex("CustomerName"));
                selectedRow.lat = cursor.getDouble(cursor.getColumnIndex("Latitude"));
                selectedRow.log = cursor.getDouble(cursor.getColumnIndex("Longitude"));
                selectedRow.register = cursor.getInt(cursor.getColumnIndex("Register")) != 0;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }


    }


    public static class Notify {
        public static class Struct {
            public int     iD    = 0;
            public String  date  = "";
            public String  title = "";
            public String  text  = "";
            public int     ring  = 0;
            public boolean seen  = false;
        }

        public static long insert(Struct myNotify) {
            ContentValues Values = new ContentValues();
            Values.put("Datetime", myNotify.date);
            Values.put("NotifyTitle", myNotify.title);
            Values.put("NotifyText", myNotify.text);
            Values.put("Ring", myNotify.ring);
            Values.put("Seen", myNotify.seen);
            return G.dbObject.insert("T_Notify", null, Values);
        }

        public static long insert(String date, String title, String text, int ring, boolean seen) {
            ContentValues Values = new ContentValues();
            Values.put("Datetime", date);
            Values.put("NotifyTitle", title);
            Values.put("NotifyText", text);
            Values.put("Ring", ring);
            Values.put("Seen", seen);
            return G.dbObject.insert("T_Notify", null, Values);
        }

        public static int edit(Struct myNotify) {
            ContentValues Values = new ContentValues();
            Values.put("Datetime", myNotify.date);
            Values.put("NotifyTitle", myNotify.title);
            Values.put("NotifyText", myNotify.text);
            Values.put("Ring", myNotify.ring);
            Values.put("Seen", myNotify.seen);
            return G.dbObject.update("T_Notify", Values, "ID=" + myNotify.iD, null);
        }

        public static int delete(int iD) {
            return G.dbObject.delete("T_Notify", "ID=" + iD, null);
        }
        public static int delete() {
            return G.dbObject.delete("T_Notify", "", null);
        }

        public static Struct select(int iD) {
            Struct selectedRow = null;
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Notify WHERE ID=" + iD, null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.date = cursor.getString(cursor.getColumnIndex("Datetime"));
                selectedRow.title = cursor.getString(cursor.getColumnIndex("NotifyTitle"));
                selectedRow.text = cursor.getString(cursor.getColumnIndex("NotifyText"));
                selectedRow.ring = cursor.getInt(cursor.getColumnIndex("Ring"));
                selectedRow.seen = cursor.getInt(cursor.getColumnIndex("Seen")) != 0;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }

        public static Struct[] select(String whereCriteria) {
            String whereQuery = "";
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Notify" + whereQuery + " ORDER BY ID DESC", null);
            if (cursor.getCount() < 1) {
                try {
                    cursor.close();
                }
                catch (Exception e) {
                    G.printStackTrace(e);
                }
                return null;
            }
            Struct[] result = new Struct[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                Struct selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.date = cursor.getString(cursor.getColumnIndex("Datetime"));
                selectedRow.title = cursor.getString(cursor.getColumnIndex("NotifyTitle"));
                selectedRow.text = cursor.getString(cursor.getColumnIndex("NotifyText"));
                selectedRow.ring = cursor.getInt(cursor.getColumnIndex("Ring"));
                selectedRow.seen = cursor.getInt(cursor.getColumnIndex("Seen")) != 0;
                result[i] = selectedRow;
                i++;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return result;
        }

        public static Struct getMax(String field, String whereCriteria) {
            String whereQuery = "";
            Struct selectedRow = null;
            if (whereCriteria.trim().length() > 0)
                whereQuery = " WHERE " + whereCriteria.trim();
            Cursor cursor = G.dbObject.rawQuery("SELECT * FROM T_Notify" + whereQuery + " ORDER BY " + field + " DESC  LIMIT 1", null);
            if (cursor.moveToNext()) {
                selectedRow = new Struct();
                selectedRow.iD = cursor.getInt(cursor.getColumnIndex("ID"));
                selectedRow.date = cursor.getString(cursor.getColumnIndex("Datetime"));
                selectedRow.title = cursor.getString(cursor.getColumnIndex("NotifyTitle"));
                selectedRow.text = cursor.getString(cursor.getColumnIndex("NotifyText"));
                selectedRow.ring = cursor.getInt(cursor.getColumnIndex("Ring"));
                selectedRow.seen = cursor.getInt(cursor.getColumnIndex("Seen")) != 0;
            }
            try {
                cursor.close();
            }
            catch (Exception e) {
                G.printStackTrace(e);
            }
            return selectedRow;
        }
    }

}