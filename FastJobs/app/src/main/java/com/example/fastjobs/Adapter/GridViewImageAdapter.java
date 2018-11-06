package com.example.fastjobs.Adapter;

import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.Message;
import com.example.fastjobs.MessageFragment.MessageFragment;
import com.example.fastjobs.NewPostPragment;
import com.example.fastjobs.R;

import java.io.IOException;
import java.util.List;

public class GridViewImageAdapter extends BaseAdapter {
    private List<Image> images;
    private NewPostPragment newPostPragment;

    public GridViewImageAdapter(List<Image> images, NewPostPragment newPostPragment) {
        this.images = images;
        this.newPostPragment = newPostPragment;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageHolder imageHolder = null;
        if(convertView == null){
            convertView = newPostPragment.getLayoutInflater().inflate(R.layout.image_post_layout, null);
            imageHolder = new ImageHolder();
            imageHolder.imageViewLayout = convertView.findViewById(R.id.imageViewLayout);
            convertView.setTag(imageHolder);
        }else {
            imageHolder = (ImageHolder)convertView.getTag();
        }
        try {
            imageHolder.imageViewLayout.setImageBitmap(MediaStore.Images.Media.getBitmap(newPostPragment.getActivity().getContentResolver(), images.get(position).getImage_uri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ImageHolder{
        public ImageView imageViewLayout;
    }
}
