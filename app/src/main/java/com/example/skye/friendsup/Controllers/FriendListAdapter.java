package com.example.skye.friendsup.Controllers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.R;

import java.util.ArrayList;

public class FriendListAdapter extends BaseAdapter {

    private ArrayList<Friend> friendArrayList;
    private Context context;
    //private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public void setFriendArrayList(ArrayList<Friend> friendArrayList) {
        this.friendArrayList = friendArrayList;
    }

    public FriendListAdapter(Context context, ArrayList<Friend> friendArrayList) {
        this.context = context;
        this.friendArrayList = friendArrayList;
    }

    @Override
    public int getCount() {
        return friendArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Prepare adapter view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend_row, null);
        }
        TextView friendName = (TextView) convertView.findViewById(R.id.friendName);
        TextView friendEmail = (TextView) convertView.findViewById(R.id.friendEmail);
        // Set parameters
        friendName.setText("Name: " + friendArrayList.get(position).getName());
        friendEmail.setText("Email " + friendArrayList.get(position).getEmail());
        return convertView;
    }
}
