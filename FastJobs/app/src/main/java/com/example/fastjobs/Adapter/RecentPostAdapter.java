package com.example.fastjobs.Adapter;

import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fastjobs.Entity.*;
import com.example.fastjobs.MyPostDetail;
import com.example.fastjobs.MainPage;
import com.example.fastjobs.R;
import com.example.fastjobs.SubHomeFragment.RecentPostFragment;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.ImageSupport;

import java.util.List;

public class RecentPostAdapter extends BaseAdapter {

    private RecentPostFragment recentPostFragment;
    private List<Post> postList;
    private MainPage mainPage;
    private Location currentLocation;

    public RecentPostAdapter(RecentPostFragment recentPostFragment, List<Post> postList, Location currentLocation) {
        this.recentPostFragment = recentPostFragment;
        this.postList = postList;
        this.currentLocation = currentLocation;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            convertView = recentPostFragment.getLayoutInflater().inflate(R.layout.layout_recentpost,null);
            final MyHolder myHolder = new MyHolder();
            myHolder.postImage=  convertView.findViewById(R.id.postImage);
            myHolder.titlePost= convertView.findViewById(R.id.titlePost);
            myHolder.remuneration= convertView.findViewById(R.id.remunerationPost);
            myHolder.distancePost= convertView.findViewById(R.id.distancePost);
            myHolder.contentPost= convertView.findViewById(R.id.descriptionPost);
            convertView.setTag(myHolder);

            if(postList.get(position).getImages() != null && postList.get(position).getImages().size()>0){
                ImageSupport imageSupport = ImageSupport.getInstance();
                imageSupport.get(postList.get(position).getImages().get(0).getImage_id(), new CallbackSupport<Bitmap>() {

                    @Override
                    public void onCallback(Bitmap bitmap, String key, List<Bitmap> bitmaps) {
                        myHolder.postImage.setImageBitmap(bitmap);
                    }
                });
            }


            myHolder.contentPost.setText(postList.get(position).getPost_content());
            myHolder.titlePost.setText(postList.get(position).getPost_title());
            myHolder.remuneration.setText(""+postList.get(position).getRemuneration());
            myHolder.distancePost.setText("...Km");
            Location location = new Location("postLocation");
            location.setLatitude(Double.parseDouble(postList.get(position).getLocation_coordinate().split("-")[0]));
            location.setLongitude(Double.parseDouble(postList.get(position).getLocation_coordinate().split("-")[1]));
            if(currentLocation != null){
                double distance = currentLocation.distanceTo(location);
                int tmpDistance = Math.round(((float) distance)/10);
                myHolder.distancePost.setText(((double)tmpDistance)/100+"Km");
            }

        }else{
            final MyHolder  myHolder = (MyHolder)convertView.getTag();
            if(postList.get(position).getImages() != null && postList.get(position).getImages().size()>0){
                ImageSupport imageSupport = new ImageSupport();
                imageSupport.get(postList.get(position).getImages().get(0).getImage_id(), new CallbackSupport<Bitmap>() {

                    @Override
                    public void onCallback(Bitmap bitmap, String key, List<Bitmap> bitmaps) {
                        myHolder.postImage.setImageBitmap(bitmap);
                    }
                });
            }

            myHolder.contentPost.setText(postList.get(position).getPost_content());
            myHolder.titlePost.setText(postList.get(position).getPost_title());
            myHolder.remuneration.setText(""+postList.get(position).getRemuneration());
            myHolder.distancePost.setText("...Km");
            Location location = new Location("postLocation");
            location.setLatitude(Double.parseDouble(postList.get(position).getLocation_coordinate().split("-")[0]));
            location.setLongitude(Double.parseDouble(postList.get(position).getLocation_coordinate().split("-")[1]));
            if(currentLocation != null){
                double distance = currentLocation.distanceTo(location);
                int tmpDistance = Math.round(((float) distance)/10);
                myHolder.distancePost.setText(((double)tmpDistance)/100+"Km");
            }
        }

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
