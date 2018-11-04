package com.example.fastjobs;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fastjobs.MessageFragment.ListMessageFragment;
import com.example.fastjobs.MessageFragment.MessageFragment;

public class MessageActivity  extends AppCompatActivity
        implements MessageFragment.OnFragmentInteractionListener, ListMessageFragment.OnFragmentInteractionListener {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new ListMessageFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
