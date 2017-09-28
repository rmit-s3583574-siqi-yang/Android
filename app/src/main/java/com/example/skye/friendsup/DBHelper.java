package com.example.skye.friendsup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.Models.Friends;
import com.example.skye.friendsup.Models.Meeting;
import com.example.skye.friendsup.Models.MeetingFriend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.os.Build.VERSION_CODES.M;

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
        db.execSQL(Meeting.CREATE_STATEMENT);
        db.execSQL(MeetingFriend.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Friend.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Meeting.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MeetingFriend.TABLE_NAME);
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

    public void addMeeting(Meeting m) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Meeting.COLUMN_TITLE, m.getTitle());

        values.put(Meeting.COLUMN_START_TIME, m.getStartTime());

        values.put(Meeting.COLUMN_END_TIME, m.getEndTime());

        values.put(Meeting.COLUMN_LOCATION, m.getLocation());

        db.insert(Friend.TABLE_NAME, null, values);

        db.close();

    }

    public void addFriendToMeeting(Meeting m, Friend f) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MeetingFriend.COLUMN_FRIEND_ID, f.getId());
        values.put(MeetingFriend.COLUMN_MEETING_ID, m.getId());
        db.insert(MeetingFriend.TABLE_NAME, null, values);
    }


    public ArrayList<Friend> getAllFriends() {
        ArrayList<Friend> friends = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Friend.TABLE_NAME + " ORDER BY " + Friend.COLUMN_NAME, null);
        // Add reminder to hash map for each row result
        if (cursor.moveToFirst()) {
            do {
                Friend f = new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
                friends.add(f);
            } while (cursor.moveToNext());
        }
        // Close cursor
        cursor.close();
        return friends;
    }

    public HashMap<Integer, Friend> getAllMappedFriends() {
        HashMap<Integer, Friend> friends = new LinkedHashMap<Integer, Friend>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Friend.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Friend f = new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
                friends.put(f.getId(), f);
            } while (cursor.moveToNext());
        }

        return friends;

    }

    public ArrayList<Friend> getFriendsFromMeeting(Meeting m) {
        SQLiteDatabase db = this.getWritableDatabase();


        HashMap<Integer, Friend> friendsData = getAllMappedFriends();
        ArrayList<Friend> resultFriends = new ArrayList<Friend>();


        Cursor cursor = db.rawQuery("SELECT * FROM " + MeetingFriend.TABLE_NAME + " WHERE " + MeetingFriend.COLUMN_MEETING_ID + " = "
                + m.getId(), null);


        if (cursor.moveToFirst()) {
            do {
                long friendId = cursor.getInt(0);
                resultFriends.add(friendsData.get(friendId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return resultFriends;

    }

    public void removeFriend(Friend f){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MeetingFriend.TABLE_NAME, MeetingFriend.COLUMN_FRIEND_ID  + " = ?",
                new String[]{String.valueOf(f.getId())});

        db.delete(Friend.TABLE_NAME, Friend.COLUMN_ID  + " = ?",
                new String[]{String.valueOf(f.getId())});

        db.close();
    }

    public void removeFriendFromMeeting(Friend f, Meeting m) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MeetingFriend.TABLE_NAME, MeetingFriend.COLUMN_MEETING_ID + " = ? AND " + MeetingFriend.COLUMN_FRIEND_ID + " = ?",
                new String[]{String.valueOf(m.getId()), String.valueOf(f.getId())});

        db.close();
    }

    public void removeMeeting(Meeting m){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MeetingFriend.TABLE_NAME, MeetingFriend.COLUMN_MEETING_ID  + " = ?",
                new String[]{String.valueOf(m.getId())});


        db.delete(Meeting.TABLE_NAME, Meeting.COLUMN_ID  + " = ?",
                new String[]{String.valueOf(m.getId())});

        db.close();
    }
}
