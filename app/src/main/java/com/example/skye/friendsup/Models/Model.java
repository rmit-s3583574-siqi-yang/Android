package com.example.skye.friendsup.Models;

import android.graphics.Path;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

/**
 * Created by skye on 31/8/17.
 */

public class Model {

    private List<Friends> friends;
    private List<Meetings> meetings;


    private String friendID;
    private String friendName;
    private String friendEmail;
    private String friendBD;
    private int friendImg;


    private String meetingID;
    private String title;
    private Calendar startTime;
    private Calendar endTime;
    private List<Friends> friendsMeeting;
    private String location;



    public void addNewFriend(){

        if(this.friendName!=null && this.friendEmail!=null && this.friendBD!=null ){
            Friends newFriend = new Friends(this.friendName, this.friendEmail, this.friendBD, this.friendImg);
            friends.add(newFriend);
        }
        Log.i("Action","Try to add new friend");
    }

    public void addNewMeeting(){
        try{
            if(this.title!=null && this.startTime!=null && this.endTime!=null && this.friendsMeeting!=null && this.location!=null){
                Meetings newMeeting = new Meetings(this.title, this.startTime, this.endTime, this.friendsMeeting, this.location);
                meetings.add(newMeeting);
            }

        }catch(Exception e){
            Log.e("ERROR",e.getMessage());
        }
        Log.i("Action","Try to add new meeting");

    }

    public List<Friends> getFriends() {
        return friends;
    }

    public void setFriends(List<Friends> friends) {
        this.friends = friends;
    }

    public List<Meetings> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meetings> meetings) {
        this.meetings = meetings;
    }

    public String getFriendID() {
        return friendID;
    }



    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getFriendBD() {
        return friendBD;
    }

    public void setFriendBD(String friendBD) {
        this.friendBD = friendBD;
    }

    public int getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(int friendImg) {
        this.friendImg = friendImg;
    }






    public String getMeetingID() {
        return meetingID;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public List<Friends> getFriendsMeeting() {
        return friendsMeeting;
    }

    public void setFriendsMeeting(List<Friends> friendsMeeting) {
        this.friendsMeeting = friendsMeeting;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
