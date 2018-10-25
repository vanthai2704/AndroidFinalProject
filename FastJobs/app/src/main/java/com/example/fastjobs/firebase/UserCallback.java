package com.example.fastjobs.firebase;

import com.example.fastjobs.entity.User;

import java.util.List;

public interface UserCallback {
    void onCallback(User user, String key, List<User> users);
}
