package com.example.vkapiexample.model;

import com.google.gson.annotations.SerializedName;

public class VKUser {
    public int id;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("is_closed")
    public boolean isClosed;

    // optional fields
    @SerializedName("photo_100")
    public String photo100;

    @SerializedName("status")
    public String status;
}
