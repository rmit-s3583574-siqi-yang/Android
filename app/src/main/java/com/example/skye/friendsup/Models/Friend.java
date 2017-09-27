package com.example.skye.friendsup.Models;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Yanni on 27/9/17.
 */

public class Friend {
    public static final String TABLE_NAME = "friends";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_EMAIL = "email";

    public static final String COLUMN_BIRTHDAY = "birthday";

    public static final String CREATE_STATEMENT =

            "CREATE TABLE " + TABLE_NAME + "(" +

                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +

                    COLUMN_NAME + " TEXT NOT NULL, " +

                    COLUMN_EMAIL + " TEXT NOT NULL, " +

                    COLUMN_BIRTHDAY + " DATE NOT NULL" +

                    ")";

    private long id;
    private String name;
    private String email;
    private long birthday;
    private int avatar;

    public Friend(long id, String name, String email, long birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.avatar = avatar;
    }

    public Friend(long id, String name, String email, long birthday, int avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
