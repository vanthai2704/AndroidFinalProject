package com.example.fastjobs.SubHomeFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fastjobs.Adapter.RecentPostAdapter;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentPostFragment extends Fragment {


    ListView listView;

    public RecentPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_recent_post, container, false);

        listView = view.findViewById(R.id.listViewPost);
        List<Post> posts = new ArrayList<>();
        Post p1 = new Post();
        p1.setPost_title("Sửa Ống Nước");
        p1.setRemuneration(50.000);
        p1.setPost_content("Cần tìm người sửa ống nước gấp");
        Post p2 = new Post();
        p2.setPost_title("Chuyển Nhà");
        p2.setRemuneration(300.000);
        p2.setPost_content("cần tìm người chuyển nhà giúp ngày 7/7/2017");
        posts.add(p1);
        posts.add(p2);

        RecentPostAdapter recentPostAdapter = new RecentPostAdapter(this,posts);
        listView.setAdapter(recentPostAdapter);

        // Inflate the layout for this fragment
        return view;
    }

}
