package com.example.skye.friendsup.Models;

import android.graphics.Path;

import java.util.Date;

/**
 * Created by skye on 31/8/17.
 */

public class Friends {
    private String friendID;
    private String friendName;
    private String friendEmail;
    private Date friendBD;
    private Path friendImg;

    public Friends(String friendID, String friendName, String friendEmail, Date friendBD, Path friendImg) {
        this.friendID = friendID;
        this.friendName = friendName;
        this.friendEmail = friendEmail;
        this.friendBD = friendBD;
        this.friendImg = friendImg;
    }



}
