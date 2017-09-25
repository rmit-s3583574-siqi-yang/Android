package com.example.skye.friendsup.Models;

import android.graphics.Path;
import java.util.Calendar;
import java.util.UUID;


/**
 * Created by skye on 31/8/17.
 */

public class Friends {
    private String friendID;
    private String friendName;
    private String friendEmail;
    private Calendar friendBD;
    private int friendImg;



    public Friends(String friendName, String friendEmail, Calendar friendBD, int friendImg) {
        this.friendID = UUID.randomUUID().toString();
        this.friendName = friendName;
        this.friendEmail = friendEmail;
        this.friendBD = friendBD;
        this.friendImg = friendImg;
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

    public Calendar getFriendBD() {
        return friendBD;
    }

    public void setFriendBD(Calendar friendBD) {
        this.friendBD = friendBD;
    }

    public int getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(int friendImg) {this.friendImg = friendImg;}

}
