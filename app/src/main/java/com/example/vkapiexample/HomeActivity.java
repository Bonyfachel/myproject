package com.example.vkapiexample;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

import com.example.vkapiexample.adapter.VKFriendsAdapter;
import com.example.vkapiexample.model.VKFriendsResponse;
import com.example.vkapiexample.model.VKResponse;
import com.example.vkapiexample.model.VKUser;
import com.example.vkapiexample.service.VKService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends BaseActivity {

    private final static String BASE_URL = "https://api.vk.com/";
    private final static String VERSION = "5.130";

    private RecyclerView rvFriends;
    private VKService vkService;
    private List<VKUser> friendsList;
    private VKFriendsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvFriends = findViewById(R.id.rv_vk_friends);
        rvFriends.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        vkService = retrofit.create(VKService.class);
        friendsList = new ArrayList<>();
        adapter = new VKFriendsAdapter(friendsList);

        DividerItemDecoration decorator =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decorator.setDrawable(getResources().getDrawable(R.drawable.space));

        rvFriends.setLayoutManager(new LinearLayoutManager(this));
        rvFriends.setAdapter(adapter);
        rvFriends.addItemDecoration(decorator);

        requestFriends();
        //addBdateInCalendar();
    }

    public void requestFriends() {
        Call<VKResponse<VKFriendsResponse>> call = vkService.getFriends(
                loadToken(),
                5000,
                "photo_100,status,bdate",
                VERSION
        );

        call.enqueue(new Callback<VKResponse<VKFriendsResponse>>() {
            @Override
            public void onResponse(Call<VKResponse<VKFriendsResponse>> call, Response<VKResponse<VKFriendsResponse>> response) {
                VKResponse<VKFriendsResponse> res = response.body();
                if(res != null) {
                    friendsList.clear();
                    friendsList.addAll(res.response.items);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<VKResponse<VKFriendsResponse>> call, Throwable t) {
                android.util.Log.d("HomeActivity", t.toString());
            }
        });
    }
    public void addBdateInCalendar() {
    Calendar beginTime = Calendar.getInstance();
    beginTime.set(2021, 4, 31, 7, 30);
    Calendar endTime = Calendar.getInstance();
    endTime.set(2021, 4, 31, 8, 30);
    Intent intent = new Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
            //.putExtra(CalendarContract.ACTION_EVENT_REMINDER, "1 day before", "2 days before" )
            //.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, "true" )
            .putExtra(CalendarContract.Events.TITLE, "День рождения")
            .putExtra(CalendarContract.Events.DESCRIPTION, "Сегодня . . . отмечает свой день рождения! Не забудьте поздравить!")
            //.putExtra(CalendarContract.Events.EVENT_LOCATION, "")
            //.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
    startActivity(intent);
}
}