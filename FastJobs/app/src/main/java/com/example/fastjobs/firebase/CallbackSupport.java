package com.example.fastjobs.firebase;

import com.example.fastjobs.Entity.User;

import java.util.List;

public interface CallbackSupport<T> {
    void onCallback(T t, String key, List<T> ts);
}
