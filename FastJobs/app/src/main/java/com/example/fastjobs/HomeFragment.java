package com.example.fastjobs;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fastjobs.Adapter.HomeAdapter;
import com.example.fastjobs.SubHomeFragment.NewsFragment;
import com.example.fastjobs.SubHomeFragment.RecentPostFragment;
import com.example.fastjobs.SubHomeFragment.TopRankFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Trang Chủ");

        TabLayout tabLayout  = view.findViewById(R.id.tab_layout);
        ViewPager viewPager  = view.findViewById(R.id.view_pager);
        HomeAdapter adapter = new HomeAdapter(getChildFragmentManager());
        adapter.addFrag(RecentPostFragment.getInstance(), "Mới Đăng");
        adapter.addFrag(TopRankFragment.getInstance(), "Top Rank");
        adapter.addFrag(NewsFragment.getInstance(), "Tin Mới");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }





}
