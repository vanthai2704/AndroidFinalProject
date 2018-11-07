package com.example.fastjobs.SubHomeFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fastjobs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRankFragment extends Fragment {

    private static TopRankFragment instance = null;
    public static TopRankFragment getInstance(){
        if(instance == null){
            instance = new TopRankFragment();
        }
        return instance;
    }

    public TopRankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_top_rank, container, false);
    }

}
