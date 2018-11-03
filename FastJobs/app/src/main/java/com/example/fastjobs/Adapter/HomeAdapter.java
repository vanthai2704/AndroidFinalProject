package com.example.fastjobs.Adapter;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fastjobs.HomeFragment;
import com.example.fastjobs.SearchFragment;
import com.example.fastjobs.SubHomeFragment.NewsFragment;
import com.example.fastjobs.SubHomeFragment.RecentPostFragment;
import com.example.fastjobs.SubHomeFragment.TopRankFragment;

public class HomeAdapter extends  FragmentPagerAdapter {
    int mNumOfTabs;
    public HomeAdapter(FragmentManager manager,int NumOfTabs){
        super(manager);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

         switch (i){
             case 0: return new RecentPostFragment();
             case 1: return new TopRankFragment();
             case 2: return new NewsFragment();
         }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0: return "Gần Đây";
            case 1: return "Top Rank";
            case 2: return "Tin Mới";
        }


        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
