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
            " email varchar(100) primary key," +
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
        values.put("yourLastMessage", "");
        values.put("theirLastMessage", "");
        db.insert("chat", null, values);
    }

    public Chat getChat(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"email", "yourLastMessage", "theirLastMessage"};
        String where = "email = ?";
        String[] whereArgs = new String[] {email};
        String groupBy = "";
        String groupArgs = "";
        String orderBy = "";
        Cursor cursor  = db.query("chat", columns, where, whereArgs, groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        String newEmail = cursor.getString(0);
        String yours = cursor.getString(1);
        String theirs = cursor.getString(2);

        Chat chat = new Chat(newEmail, yours, theirs);

        return chat;
    }

    public boolean updateTheirMessage(Chat chat, String newMessage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("yourLastMessage", chat.getYourLastMessage());
        values.put("theirLastMessage", newMessage);

        int numRows = db.update("chat",
                values,
                "email = ?",
                new String [] {""+chat.getEmail()});
        return (numRows == 1);
    }

    public boolean updateYourMessage(Chat chat, String newMessage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("yourLastMessage", newMessage);
        values.put("theirLastMessage", chat.getTheirLastMessage());


        int numRows = db.update("chat",
                values,
                "email = ?",
                new String [] {""+chat.getEmail()});
        return (numRows == 1);
    }
}
