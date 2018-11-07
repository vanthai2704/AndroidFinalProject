package com.example.fastjobs.Adapter;

import android.graphics.Bitmap;
import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fastjobs.CartFragment;
import com.example.fastjobs.Entity.Cart;
import com.example.fastjobs.Entity.Category;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.MainPage;
import com.example.fastjobs.R;
import com.example.fastjobs.SubHomeFragment.RecentPostFragment;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.CategorySupport;
import com.example.fastjobs.firebase.ImageSupport;
import com.example.fastjobs.firebase.PostSupport;

import java.util.List;

public class CartAdapter  extends BaseAdapter {

    private CartFragment cartFragment;
    private List<Cart> cartList;
    private Location currentLocation;
    private PostSupport postSupport;

    public CartAdapter(CartFragment cartFragment, List<Cart> cartList, Location currentLocation) {
        this.cartFragment = cartFragment;
        this.cartList = cartList;
        this.currentLocation = currentLocation;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            convertView = cartFragment.getLayoutInflater().inflate(R.layout.layout_recentpost,null);
            final CartAdapter.MyHolder myHolder = new CartAdapter.MyHolder();
            myHolder.postImage=  convertView.findViewById(R.id.postImage);
            myHolder.titlePost= convertView.findViewById(R.id.titlePost);
            myHolder.remuneration= convertView.findViewById(R.id.remunerationPost);
            myHolder.distancePost= convertView.findViewById(R.id.distancePost);
            myHolder.contentPost= convertView.findViewById(R.id.descriptionPost);
            convertView.setTag(myHolder);
            ///
            postSupport = PostSupport.getInstance();
            postSupport.get(cartList.get(position).getPost_id(), new CallbackSupport<Post>() {
                @Override
                public void onCallback(Post post, String key, List<Post> posts) {
                    if(post.getImages() != null && post.getImages().size()>0){
                        ImageSupport imageSupport = new ImageSupport();
                        imageSupport.get(post.getImages().get(0).getImage_id(), new CallbackSupport<Bitmap>() {

                            @Override
                            public void onCallback(Bitmap bitmap, String key, List<Bitmap> bitmaps) {
                                myHolder.postImage.setImageBitmap(bitmap);
                            }
                        });
                    }


                    myHolder.contentPost.setText(post.getPost_content());
                    myHolder.titlePost.setText(post.getPost_title());
                    myHolder.remuneration.setText(""+post.getRemuneration());
                    myHolder.distancePost.setText("...Km");
                    Location location = new Location("postLocation");
                    location.setLatitude(Double.parseDouble(post.getLocation_coordinate().split("-")[0]));
                    location.setLongitude(Double.parseDouble(post.getLocation_coordinate().split("-")[1]));
                    if(currentLocation != null){
                        double distance = currentLocation.distanceTo(location);
                        int tmpDistance = Math.round(((float) distance)/10);
                        myHolder.distancePost.setText(((double)tmpDistance)/100+"Km");
                    }

                }
            });
            ///






        }else{
            final CartAdapter.MyHolder myHolder = (CartAdapter.MyHolder)convertView.getTag();
            postSupport = PostSupport.getInstance();
            postSupport.get(cartList.get(position).getPost_id(), new CallbackSupport<Post>() {

                @Override
                public void onCallback(Post post, String key, List<Post> posts) {
                    if(post.getImages() != null && post.getImages().size()>0){
                        ImageSupport imageSupport = new ImageSupport();
                        imageSupport.get(post.getImages().get(0).getImage_id(), new CallbackSupport<Bitmap>() {

                            @Override
                            public void onCallback(Bitmap bitmap, String key, List<Bitmap> bitmaps) {
                                myHolder.postImage.setImageBitmap(bitmap);
                            }
                        });
                    }

                    myHolder.contentPost.setText(post.getPost_content());
                    myHolder.titlePost.setText(post.getPost_title());
                    myHolder.remuneration.setText(""+post.getRemuneration());
                    myHolder.distancePost.setText("...Km");
                    Location location = new Location("postLocation");
                    location.setLatitude(Double.parseDouble(post.getLocation_coordinate().split("-")[0]));
                    location.setLongitude(Double.parseDouble(post.getLocation_coordinate().split("-")[1]));
                    if(currentLocation != null){
                        double distance = currentLocation.distanceTo(location);
                        int tmpDistance = Math.round(((float) distance)/10);
                        myHolder.distancePost.setText(((double)tmpDistance)/100+"Km");
                    }
                }
            });


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

