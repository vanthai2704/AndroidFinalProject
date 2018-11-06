package com.example.fastjobs.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.JobDetail;
import com.example.fastjobs.MessageFragment.MessageFragment;
import com.example.fastjobs.MyFragment;
import com.example.fastjobs.R;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    private List<Post> listData;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;

    public PostAdapter(List<Post> listData, MyFragment myFragment, FragmentManager fragmentManager) {
        this.listData = listData;
        this.myFragment = myFragment;
        this.fragmentManager = fragmentManager;
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PostHolder holder;
        if (convertView == null) {
            convertView = myFragment.getLayoutInflater().inflate(R.layout.post_layout, null);
            holder = new PostHolder();
            holder.jobTitle = convertView.findViewById(R.id.layoutTitle);
            holder.jobDetail = convertView.findViewById(R.id.jobDetail);
            convertView.setTag(holder);
        } else {
            holder = (PostHolder) convertView.getTag();
        }
        holder.jobTitle.setText(listData.get(position).getPost_title());
        holder.jobDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = listData.get(position);
                JobDetail jobDetail = JobDetail.newInstance(
                       post.getPost_id(), null);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.contentLayout));
                fragmentTransaction.add(R.id.contentLayout, jobDetail);
                fragmentTransaction.commit();
            }
        });
        return convertView;
    }

    public  class PostHolder{
        TextView jobTitle;
        Button jobDetail;
    }

}
