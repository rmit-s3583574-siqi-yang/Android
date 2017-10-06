package com.example.skye.friendsup.Models;


public class MeetingFriend {

    public static final String TABLE_NAME = "meeting_friends";
    public static final String COLUMN_MEETING_ID = "meeting_id";
    public static final String COLUMN_FRIEND_ID = "friend_id";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_FRIEND_ID + " INTEGER NOT NULL, " +
                    COLUMN_MEETING_ID + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + COLUMN_MEETING_ID + ", " + COLUMN_FRIEND_ID + "))";


    private Friend friend;
    private Meeting meeting;

    public MeetingFriend(Friend friend, Meeting meeting) {
        this.friend = friend;
        this.meeting = meeting;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
