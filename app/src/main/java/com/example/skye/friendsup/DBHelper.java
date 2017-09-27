package com.example.skye.friendsup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.skye.friendsup.Models.Friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "FriendsupDB";

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Friend.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Friend.TABLE_NAME);

        onCreate(db);
    }

    public void addFriend(Friend f) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Friend.COLUMN_NAME, f.getName());

        values.put(Friend.COLUMN_EMAIL, f.getEmail());

        values.put(Friend.COLUMN_BIRTHDAY, f.getBirthday());

        db.insert(Friend.TABLE_NAME, null, values);

        db.close();

    }

    public ArrayList<Friend> getAllFriends() {
        ArrayList<Friend> friends = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Friend.TABLE_NAME + " ORDER BY " + Friend.COLUMN_NAME, null);
        // Add reminder to hash map for each row result
        if (cursor.moveToFirst()) {
            do {
                Friend  f = new Friend(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
                friends.add(f);
            } while (cursor.moveToNext());
        }
        // Close cursor
        cursor.close();
        return friends;
    }


}
