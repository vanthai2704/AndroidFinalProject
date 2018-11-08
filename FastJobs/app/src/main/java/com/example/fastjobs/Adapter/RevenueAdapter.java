package com.example.fastjobs.Adapter;

import android.graphics.Bitmap;
import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.Entity.Revenue;
import com.example.fastjobs.MainPage;
import com.example.fastjobs.ManagementRevenue;
import com.example.fastjobs.R;
import com.example.fastjobs.SubHomeFragment.RecentPostFragment;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.ImageSupport;

import java.text.SimpleDateFormat;
import java.util.List;

public class RevenueAdapter extends BaseAdapter {

    private ManagementRevenue managementRevenue;
    private List<Revenue> revenues;

    public RevenueAdapter(ManagementRevenue managementRevenue, List<Revenue> revenues) {
        this.managementRevenue = managementRevenue;
        this.revenues = revenues;
    }

    @Override
    public int getCount() {
        return revenues.size();
    }

    @Override
    public Object getItem(int position) {
        return revenues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        RevenueAdapter.MyHolder myHolder = new RevenueAdapter.MyHolder();
        if(convertView == null){
            convertView = managementRevenue.getLayoutInflater().inflate(R.layout.layout_revenue_adapter,null);


            myHolder.txtEmail= convertView.findViewById(R.id.txtEmail);
            myHolder.txtMoney= convertView.findViewById(R.id.txtMoney);
            myHolder.txtDate= convertView.findViewById(R.id.txtDate);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
    }
        myHolder.txtEmail.setText(revenues.get(position).getEmail());
        myHolder.txtMoney.setText(""+revenues.get(position).getMoney());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        myHolder.txtDate.setText(""+formatter.format(revenues.get(position).getDate()));
        return convertView;

    }

    class MyHolder{
        public TextView txtEmail;
        public TextView txtMoney;
        public TextView txtDate;

    }

}
