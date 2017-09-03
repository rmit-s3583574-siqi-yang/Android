package com.example.skye.friendsup.Models;

import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by skye on 31/8/17.
 */

public class Model {
    public static final String TAG = "Model status";

    private ArrayList<Friends> friends = new ArrayList<>();
    private ArrayList<Meetings> meetings = new ArrayList<>();


    private String friendID;
    private String friendName;
    private String friendEmail;
    private Calendar friendBD;
    private int friendImg;


    private String meetingID;
    private String title;
    private Calendar startTime;
    private Calendar endTime;
    private ArrayList<Friends> friendsMeeting;
    private String location;



    public void addNewFriend(Friends newFriend){
        Log.i(TAG,"Try to add new friend in the friend List");

        try{
            friends.add(newFriend);

        }catch(Exception e){
            Log.e(TAG,e.getMessage());
        }


        for (int i=0; i<friends.size();i++){
            Log.i(TAG,friends.get(i).getFriendID());
        }




    }

    public void addNewMeeting(Meetings newMeeting){
        try{
            meetings.add(newMeeting);


        }catch(Exception e){
            Log.e("ERROR",e.getMessage());
        }
        Log.i("Action","Try to add new meeting");

    }

    public ArrayList<Friends> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friends> friends) {
        this.friends = friends;
    }

    public ArrayList<Meetings> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<Meetings> meetings) {
        this.meetings = meetings;
    }



//    public String getFriendID() {
//        return friendID;
//    }
//
//
//    public String getFriendName() {
//        return friendName;
//    }
//
//    public void setFriendName(String friendName) {
//        this.friendName = friendName;
//    }
//
//    public String getFriendEmail() {
//        return friendEmail;
//    }
//
//    public void setFriendEmail(String friendEmail) {
//        this.friendEmail = friendEmail;
//    }
//
//    public Calendar getFriendBD() {
//        return friendBD;
//    }
//
//    public void setFriendBD(Calendar friendBD) {
//        this.friendBD = friendBD;
//    }
//
//    public int getFriendImg() {
//        return friendImg;
//    }
//
//    public void setFriendImg(int friendImg) {
//        this.friendImg = friendImg;
//    }






//    public String getMeetingID() {
//        return meetingID;
//    }
//
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public Calendar getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Calendar startTime) {
//        this.startTime = startTime;
//    }
//
//    public Calendar getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Calendar endTime) {
//        this.endTime = endTime;
//    }
//
//    public ArrayList<Friends> getFriendsMeeting() {
//        return friendsMeeting;
//    }
//
//    public void setFriendsMeeting(ArrayList<Friends> friendsMeeting) {
//        this.friendsMeeting = friendsMeeting;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
}
