package com.example.skye.friendsup.Models;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.skye.friendsup.Controllers.MainActivity.model;

/**
 * Created by skye on 3/9/17.
 */

public class DataNotNull {

    private String demoFriendName = "Anonymous";
    private String demoFriendEmail = "Anonymous@hex.com";
    private Calendar demoFriendBD = Calendar.getInstance();
    private int demoFriendImg = 2130837600;


    private String demoTitle = "Let's meet up!";
    private Calendar demoStartTime = Calendar.getInstance();
    private Calendar demoEndTime = Calendar.getInstance();
    private ArrayList<Friends> demoFriendsMeeting = new ArrayList<>();
    private String demoLocation = "-122.084,37.422";



    public void setDemoData(){
        Friends demoFriend = new Friends(demoFriendName,demoFriendEmail,demoFriendBD,demoFriendImg);
        model.addNewFriend(demoFriend);


        demoFriendsMeeting.add(demoFriend);
        Meetings demoMeeting = new Meetings(demoTitle, demoStartTime, demoEndTime, demoFriendsMeeting, demoLocation);
        model.addNewMeeting(demoMeeting);




    }
}
