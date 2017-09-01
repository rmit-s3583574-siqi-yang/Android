package com.example.skye.friendsup.Models;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by skye on 31/8/17.
 */

public class Meetings {

    private String meetingID;
    private String title;
    private Calendar startTime;
    private Calendar endTime;
    private List<Friends> friendsMeeting;
    private String location;

    public Meetings(String title, Calendar startTime, Calendar endTime, List<Friends> friendsMeeting, String location) {
        this.meetingID = UUID.randomUUID().toString();
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.friendsMeeting = friendsMeeting;
        this.location = location;
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
