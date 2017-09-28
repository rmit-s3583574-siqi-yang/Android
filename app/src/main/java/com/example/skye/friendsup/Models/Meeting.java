package com.example.skye.friendsup.Models;

import android.app.Service;

/**
 * Created by Yanni on 28/9/17.
 */

public class Meeting {
    public static final String TABLE_NAME = "meetings";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_TITLE = "title";

    public static final String COLUMN_START_TIME = "start_time";

    public static final String COLUMN_END_TIME = "end_time";

    public static final String COLUMN_LOCATION = "location";

    public static final String CREATE_STATEMENT =

            "CREATE TABLE " + TABLE_NAME + "(" +

                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +

                    COLUMN_TITLE + " TEXT NOT NULL, " +

                    COLUMN_START_TIME + " LONG NOT NULL, " +

                    COLUMN_END_TIME + " LONG NOT NULL, " +

                    COLUMN_LOCATION + " LONG NOT NULL" +

                    ")";

    private int id;
    private String title;
    private long startTime;
    private long endTime;
    private String location;

    public Meeting(int id, String title, long startTime, long endTime, String location) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public Meeting(String title, long startTime, long endTime, String location) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
