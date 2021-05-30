package com.example.vkapiexample.adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkapiexample.HomeActivity;
import com.example.vkapiexample.R;
import com.example.vkapiexample.model.VKUser;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class VKFriendViewHolder extends RecyclerView.ViewHolder {

    private TextView friendName;
    private ImageView photo;
    private TextView bdate;
    private Button addBdate;

    public VKFriendViewHolder(@NonNull View itemView) {
        super(itemView);

        photo = itemView.findViewById(R.id.iv_friend_photo);
        friendName = itemView.findViewById(R.id.tv_friend_name);
        bdate = itemView.findViewById(R.id.tv_friend_bdate);
        addBdate = itemView.findViewById(R.id.bt_addBdate);
    }

    public void setFriend(VKUser user) {

            friendName.setText(user.firstName + " " + user.lastName);
            bdate.setText(user.bdate);

            Picasso
                    .get()
                    .load(user.photo100)
                    .into(photo);

    }
}

public class VKFriendsAdapter extends RecyclerView.Adapter<VKFriendViewHolder> {

    private Activity activity;
    private List<VKUser> friends;
    private Button addBdate;




    public VKFriendsAdapter(List<VKUser> friends, Activity activity) {
        this.friends = friends;
        this.activity = activity;
    }


    @NonNull
    @Override
    public VKFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.itm_vk_friend, parent, false);
        addBdate = view.findViewById(R.id.bt_addBdate);
        return new VKFriendViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull VKFriendViewHolder holder, int position) {
        holder.setFriend(friends.get(position));
        addBdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = friends.get(position).bdate;
                if (friends.get(position).bdate != null) {
                    try {
                        addBdateInCalendar(friends.get(position).firstName, friends.get(position).lastName, friends.get(position).bdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Упс! Пользователь не установил свою дату рождения!", Toast.LENGTH_LONG).show();                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public void addBdateInCalendar(String name, String lastName, String bdate) throws NullPointerException, ParseException {

            String fromDate = bdate;
            DateFormat df = new SimpleDateFormat("dd.MM");
            Date dtt = df.parse(fromDate);
            System.out.println("the date is " + df.format(dtt));
            Log.d("the date is ", df.format(dtt));
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = dtt.getMonth();
            int day = dtt.getDate();


            int year = Calendar.getInstance().get(Calendar.YEAR);

            Calendar beginTime = Calendar.getInstance();
            beginTime.set(year, month, day, 6, 30);
            Calendar endTime = Calendar.getInstance();
            endTime.set(year, month, day, 23, 59);
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.ACTION_EVENT_REMINDER, 1)
                    .putExtra(CalendarContract.Events.RRULE, "FREQ=Yearly")
                    //.putExtra(CalendarContract.Events.)
                    .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, "true")
                    .putExtra(CalendarContract.Events.TITLE, "День рождения вашего друга/подруги!")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Сегодня " + name + " " + lastName +
                            " отмечает свой день рождения! Не забудьте поздравить!");
            //.putExtra(CalendarContract.Events.EVENT_LOCATION, "")
            //.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            activity.startActivity(intent);

    }
}


