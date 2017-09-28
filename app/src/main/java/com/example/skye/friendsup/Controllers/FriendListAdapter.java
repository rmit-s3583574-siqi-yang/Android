package com.example.skye.friendsup.Controllers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.R;

import java.util.ArrayList;

public class FriendListAdapter extends BaseAdapter {

    private ArrayList<Friend> friendArrayList;
    private Context context;
    private TextView friendName;
    private TextView friendEmail;
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
        ViewHolder holder;
        if (convertView == null) { // if convertView is null
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend_row, null);
            holder = new ViewHolder();
            holder.friendName = convertView.findViewById(R.id.friendName);
            holder.friendEmail = convertView.findViewById(R.id.friendEmail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.friendName.setText("Name: " + friendArrayList.get(position).getName());
        holder.friendEmail.setText("Email " + friendArrayList.get(position).getEmail());
        return convertView;
    }

     static class ViewHolder {
        private TextView friendName;
        public TextView friendEmail;
    }
}
