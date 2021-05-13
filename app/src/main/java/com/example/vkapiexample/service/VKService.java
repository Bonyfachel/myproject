package com.example.vkapiexample.service;

import com.example.vkapiexample.model.VKFriendsResponse;
import com.example.vkapiexample.model.VKResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// https://api.vk.com/method/users.get?user_id=210700286&v=5.52

public interface VKService {

    @GET("/method/friends.get")
    Call<VKResponse<VKFriendsResponse>> getFriends(
            @Query("access_token") String accessToken,
            @Query("count") int count,
            @Query("fields") String fields,
            @Query("v") String v
    );

}
