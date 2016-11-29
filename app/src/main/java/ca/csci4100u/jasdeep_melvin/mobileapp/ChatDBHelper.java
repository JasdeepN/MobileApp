package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ChatDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "chat.db";

    private static final String CREATE_STATEMENT = "" +
            "create table chat(" +
            " email varchar() primary key," +
            " yourLastMessage varchar(SQLITE_MAX_LENGTH)," +
            " theirLastMessage varchar(SQLITE_MAX_LENGTH))";

    private static final String DROP_STATEMENT = "" +
            "drop table chat";

    public ChatDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        db.execSQL(CREATE_STATEMENT);
    }

    public void addNewChat(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        db.insert("chat", null, values);
    }

    public ArrayList<String> getChat(String username) {
        ArrayList<String> chatLog = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"email"};
        String where = "";
        String[] whereArgs = new String[] {};
        String groupBy = "";
        String groupArgs = "";
        String orderBy = "";
        Cursor cursor  = db.query("chats", columns, where, whereArgs, groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            chatLog.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return chatLog;
    }
}
