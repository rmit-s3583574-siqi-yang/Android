package com.example.skye.friendsup.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skye.friendsup.Models.Meeting;
import com.example.skye.friendsup.R;
import com.example.skye.friendsup.utils.DateFormatter;

import java.util.ArrayList;

/**
 * Created by skye on 7/10/17.
 */

public class MeetingListAdapter extends BaseAdapter{


    private ArrayList<Meeting> meetingArrayList;
    private Context context;
    private TextView meetingTitle;
    private TextView meetingTime;

    public void setMeetingArrayList(ArrayList<Meeting> meetingArrayList) {
        this.meetingArrayList = meetingArrayList;
    }

    public MeetingListAdapter(Context context, ArrayList<Meeting> meetingArrayList) {
        this.context = context;
        this.meetingArrayList = meetingArrayList;
    }

    @Override
    public int getCount() {
        return meetingArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return meetingArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) { // if convertView is null
            convertView = LayoutInflater.from(context).inflate(R.layout.item_meeting_row, null);
            holder = new ViewHolder();
            holder.meetingTitle = convertView.findViewById(R.id.meetingTitle);
            holder.meetingTime = convertView.findViewById(R.id.meetingTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.meetingTitle.setText(" " + meetingArrayList.get(position).getTitle());
        holder.meetingTime.setText("   " + DateFormatter.lngStringDateTime(meetingArrayList.get(position).getStartTime()));
        return convertView;

    }

    static class ViewHolder {
        private TextView meetingTitle;
        public TextView meetingTime;
    }
}
