package com.example.vkapiexample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkapiexample.R;
import com.example.vkapiexample.model.VKUser;
import com.squareup.picasso.Picasso;

import java.util.List;

class VKFriendViewHolder extends RecyclerView.ViewHolder {

    private TextView friendName;
    private TextView status;
    private ImageView photo;

    public VKFriendViewHolder(@NonNull View itemView) {
        super(itemView);

        photo = itemView.findViewById(R.id.iv_friend_photo);
        friendName = itemView.findViewById(R.id.tv_friend_name);
        status = itemView.findViewById(R.id.tv_friend_status);
    }

    public void setFriend(VKUser user) {
        friendName.setText(user.firstName + " " + user.lastName);
        status.setText(user.status);

        Picasso
                .get()
                .load(user.photo100)
                .into(photo);
    }
}

public class VKFriendsAdapter extends RecyclerView.Adapter<VKFriendViewHolder> {

    private List<VKUser> friends;

    public VKFriendsAdapter(List<VKUser> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public VKFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.itm_vk_friend, parent, false);
        return new VKFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VKFriendViewHolder holder, int position) {
        holder.setFriend(friends.get(position));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
