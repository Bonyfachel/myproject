package com.example.vkapiexample.adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkapiexample.HomeActivity;
import com.example.vkapiexample.R;
import com.example.vkapiexample.model.VKUser;
import com.squareup.picasso.Picasso;

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
               friends.get(position);
               
               addBdateInCalendar();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public void addBdateInCalendar() throws NullPointerException {

        int year = Calendar.getInstance().get(Calendar.YEAR);

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, 4, 31, 6, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, 4, 31, 23, 59);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                //.putExtra(CalendarContract.ACTION_EVENT_REMINDER, "1 day before", "2 days before" )
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, "true" )
                .putExtra(CalendarContract.Events.TITLE, "День рождения")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Сегодня . . . отмечает свой день рождения! Не забудьте поздравить!")
                //.putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                //.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        activity.startActivity(intent);
    }
}


