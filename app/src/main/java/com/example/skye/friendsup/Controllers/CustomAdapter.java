package com.example.skye.friendsup.Controllers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.skye.friendsup.Models.Friends;
import com.example.skye.friendsup.R;

import java.util.ArrayList;

import static com.example.skye.friendsup.Controllers.MainActivity.model;

/**
 * Created by skye on 3/9/17.
 */

public class CustomAdapter extends ArrayAdapter<Friends> {
    private Activity activity;
    private ArrayList<Friends> friends =model.getFriends();
    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity activity, int textViewResourceId, ArrayList<Friends> _friends) {
        super(activity, textViewResourceId, _friends);
        try {
            this.activity = activity;
            this.friends = _friends;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return friends.size();
    }

    public Friends getItem(Friends position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_email;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.custom_friend_row, null);
                holder = new ViewHolder();

                holder.display_name = vi.findViewById(R.id.rowText);
                holder.display_email = vi.findViewById(R.id.rowText2);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText(friends.get(position).getFriendName());
            holder.display_email.setText(friends.get(position).getFriendEmail());


        } catch (Exception e) {


        }
        return vi;
    }
}
