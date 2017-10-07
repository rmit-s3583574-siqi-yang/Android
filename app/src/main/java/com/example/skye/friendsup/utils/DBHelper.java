package com.example.skye.friendsup.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.Models.Meeting;
import com.example.skye.friendsup.Models.MeetingFriend;

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
        db.execSQL(Meeting.CREATE_STATEMENT);
        db.execSQL(MeetingFriend.CREATE_STATEMENT);
        Log.i("Helper status",MeetingFriend.CREATE_STATEMENT);
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

        db.insert(Meeting.TABLE_NAME, null, values);

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

    public ArrayList<Meeting> getAllMeetings(){
        ArrayList<Meeting> meetings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Meeting.TABLE_NAME + " ORDER BY " + Meeting.COLUMN_TITLE, null);
        // Add reminder to hash map for each row result
        if (cursor.moveToFirst()) {
            do {
                Meeting m = new Meeting(cursor.getInt(0), cursor.getString(1), cursor.getLong(2), cursor.getLong(3), cursor.getString(4));
                meetings.add(m);
            } while (cursor.moveToNext());
        }
        // Close cursor
        cursor.close();
        return meetings;

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

    public Boolean checkEmpty(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + tableName, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount>0) {
            return false;
        }else return true;

    }

    public Boolean checkFriendInMeeting(Friend f){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + Friend.TABLE_NAME + " WHERE " + MeetingFriend.COLUMN_FRIEND_ID + " = "
                + f.getId(), null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount>0) {
            return true;
        }else return false;
    }

//    public Friend findFriendFromMeeting(Friend f){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        HashMap<Integer, Friend> friendsData = getAllMappedFriends();
//        Friend resultF = null;
//        long friendId = f.
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + MeetingFriend.TABLE_NAME + " WHERE " + MeetingFriend.COLUMN_FRIEND_ID + " = "
//                + f.getId(), null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                if (long friendId = cursor.getInt(0)){
//
//                };
//                resultF = friendsData.get(friendId);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        return resultF;
//
//    }

    public Friend getFriendById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Friend.TABLE_NAME + " WHERE " + Friend.COLUMN_ID + " = "
                + id, null);

        Friend f = null;
        if (cursor.moveToFirst()) {
            do {
                f = new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return f;
    }

    public void updateFriendById(int id, Friend newFriend){
        Friend f = getFriendById(id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Friend.COLUMN_NAME, newFriend.getName());
        contentValues.put(Friend.COLUMN_EMAIL, newFriend.getEmail());
        contentValues.put(Friend.COLUMN_BIRTHDAY, newFriend.getBirthday());
        String selection = Friend.COLUMN_ID + " = ?"; // where ID column = rowId (that is, selectionArgs)
        String[] selectionArgs = { String.valueOf(id) };

        db.update(Friend.TABLE_NAME, contentValues, selection,
                selectionArgs);
        db.close();
    }

    public Meeting getMeetingById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Meeting.TABLE_NAME + " WHERE " + Meeting.COLUMN_ID + " = "
                + id, null);

        Meeting m = null;
        if (cursor.moveToFirst()) {
            do {
                m = new Meeting(cursor.getInt(0), cursor.getString(1), cursor.getLong(2), cursor.getLong(3), cursor.getString(4));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return m;
    }

    public void updateMeetingById(int id, Meeting newMeeting){
        Meeting m = getMeetingById(id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Meeting.COLUMN_TITLE, newMeeting.getTitle());
        contentValues.put(Meeting.COLUMN_START_TIME, newMeeting.getStartTime());
        contentValues.put(Meeting.COLUMN_END_TIME, newMeeting.getEndTime());
        contentValues.put(Meeting.COLUMN_LOCATION, newMeeting.getLocation());
        String selection = Meeting.COLUMN_ID + " = ?"; // where ID column = rowId (that is, selectionArgs)
        String[] selectionArgs = { String.valueOf(id) };

        db.update(Meeting.TABLE_NAME, contentValues, selection,
                selectionArgs);
        db.close();
    }

    public void removeFriend(Friend f) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("version status",""+ db.getVersion());


        db.delete(MeetingFriend.TABLE_NAME, MeetingFriend.COLUMN_FRIEND_ID + " = ?",
                new String[]{String.valueOf(f.getId())});


        db.delete(Friend.TABLE_NAME, Friend.COLUMN_ID + " = ?",
                new String[]{String.valueOf(f.getId())});

        db.close();
    }

    public void removeFriendFromMeeting(Friend f, Meeting m) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MeetingFriend.TABLE_NAME, MeetingFriend.COLUMN_MEETING_ID + " = ? AND " + MeetingFriend.COLUMN_FRIEND_ID + " = ?",
                new String[]{String.valueOf(m.getId()), String.valueOf(f.getId())});

        db.close();
    }

    public void removeMeeting(Meeting m) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MeetingFriend.TABLE_NAME, MeetingFriend.COLUMN_MEETING_ID + " = ?",
                new String[]{String.valueOf(m.getId())});


        db.delete(Meeting.TABLE_NAME, Meeting.COLUMN_ID + " = ?",
                new String[]{String.valueOf(m.getId())});

        db.close();
    }
}
