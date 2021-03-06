package com.example.fastjobs;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fastjobs.Adapter.PostAdapter;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.PostSupport;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    private PostSupport helper;
    private ListView lvPost;
    private List<Post> listPost;
    private PostAdapter adapter;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Bài Đăng Của Tôi");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new PostSupport();
        lvPost= view.findViewById(R.id.listPost);
        Post postSearch = new Post();
        postSearch.setUser_id((new LoginSupport()).getCurrentUserEmail().replaceAll("\\.","_"));
        helper.search(postSearch, 1, 100, new CallbackSupport<Post>() {

            @Override
            public void onCallback(Post post, String key, List<Post> posts) {
                listPost = posts;
                Collections.reverse(listPost);
                adapter = new PostAdapter(listPost, getMyFragment(), getFragmentManager());
                lvPost.setAdapter(adapter);
                registerForContextMenu(lvPost);
            }
        });

    }

    public MyFragment getMyFragment(){
        return this;
    }
}
