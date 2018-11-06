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

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends  FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public HomeAdapter(FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
