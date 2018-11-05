package com.example.fastjobs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.R;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    private List<Post> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public PostAdapter(List<Post> listData, Context context) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        PostHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.post_layout, null);
            holder = new PostHolder();
            holder.jobTitle = convertView.findViewById(R.id.layoutTitle);
            holder.jobDetail = convertView.findViewById(R.id.jobDetail);
            convertView.setTag(holder);
        } else {
            holder = (PostHolder) convertView.getTag();
        }
        holder.jobTitle.setText(listData.get(position).getPost_title());
        return convertView;
    }

    public  class PostHolder{
        TextView jobTitle;
        Button jobDetail;
    }

}
