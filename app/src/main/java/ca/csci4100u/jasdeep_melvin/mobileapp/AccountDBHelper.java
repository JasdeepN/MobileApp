package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "accounts.db";

    private static final String CREATE_STATEMENT = "" +
            "create table accounts(" +
            " username varchar(100) primary key," +
            " password varchar(100) not null)";

    private static final String DROP_STATEMENT = "" +
            "drop table accounts";

    public AccountDBHelper(Context context) {
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

    public void deletAllaccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("accounts", "", new String[] {});
    }

    public void addNewAccount(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        db.insert("accounts", null, values);
    }

    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"username"};
        String where = "";
        String[] whereArgs = new String[] {};
        String groupBy = "";
        String groupArgs = "";
        String orderBy = "";
        Cursor cursor  = db.query("accounts", columns, where, whereArgs, groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            if(cursor.getString(0).equals(username)){
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }

    public boolean loginPass(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"username", "password"};
        String where = "";
        String[] whereArgs = new String[] {};
        String groupBy = "";
        String groupArgs = "";
        String orderBy = "";
        Cursor cursor  = db.query("accounts", columns, where, whereArgs, groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            if(cursor.getString(0).equals(username) && cursor.getString(1).equals(password)){
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }

}
