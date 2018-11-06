package com.example.fastjobs.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fastjobs.Entity.*;
import com.example.fastjobs.MainPage;
import com.example.fastjobs.R;
import com.example.fastjobs.SubHomeFragment.RecentPostFragment;

import java.util.List;

public class RecentPostAdapter extends BaseAdapter {

    private RecentPostFragment recentPostFragment;
    private List<Post> PostList;
    private MainPage mainPage;
    public RecentPostAdapter(RecentPostFragment recentPostFragment, List<Post> PostList) {
        this.recentPostFragment = recentPostFragment;
        this.PostList = PostList;
    }

    @Override
    public int getCount() {
        return PostList.size();
    }

    @Override
    public Object getItem(int position) {
        return PostList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder=null;

        if(convertView == null){
            convertView = recentPostFragment.getLayoutInflater().inflate(R.layout.layout_recentpost,null);
            myHolder = new MyHolder();
            myHolder.postImage=  convertView.findViewById(R.id.postImage);
            myHolder.titlePost= convertView.findViewById(R.id.titlePost);
            myHolder.remuneration= convertView.findViewById(R.id.remunerationPost);
            myHolder.distancePost= convertView.findViewById(R.id.distancePost);
            myHolder.contentPost= convertView.findViewById(R.id.descriptionPost);
            convertView.setTag(myHolder);

        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        myHolder.postImage.setImageResource(R.drawable.anh_nha);
        myHolder.contentPost.setText(PostList.get(position).getPost_content());
        myHolder.titlePost.setText(PostList.get(position).getPost_title());
        myHolder.remuneration.setText(""+PostList.get(position).getRemuneration());
        myHolder.distancePost.setText("20 Km");
        return convertView;
    }

    class MyHolder{
        public ImageView postImage;
        public TextView titlePost;
        public TextView contentPost;
        public TextView remuneration;
        public TextView distancePost;

    }

}
