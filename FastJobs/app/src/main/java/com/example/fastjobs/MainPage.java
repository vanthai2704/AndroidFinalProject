package com.example.fastjobs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.View.LoginActivity;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, JobDetail.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoginSupport loginSupport = new LoginSupport();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        if(!loginSupport.isLogin()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        Toolbar toolbar =  findViewById(R.id.toolbarDrawer);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getMainPage(), MessageActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.contentLayout,new HomeFragment());
        ft.commit();
        View headerLayout =
                navigationView.getHeaderView(0);
        TextView textViewHeaderEmail = headerLayout.findViewById(R.id.textViewHeaderEmail);
        textViewHeaderEmail.setText(loginSupport.getCurrentUserEmail());
    }

    public MainPage getMainPage(){
        return this;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentLayout,new HomeFragment());
            ft.commit();

        } else if (id == R.id.nav_search) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentLayout,new SearchFragment());
            ft.commit();
        } else if (id == R.id.nav_addpost) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentLayout,new NewPostPragment());
            ft.commit();
        }else if (id == R.id.nav_listPost) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentLayout,new MyFragment());
            ft.commit();
        }
        else if (id == R.id.nav_logOut) {
            LoginSupport loginSupport = new LoginSupport();
            loginSupport.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
