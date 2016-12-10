package com.app.temproject.shipper.Activity.Shipper;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.temproject.shipper.Activity.FileProcessing;
import com.app.temproject.shipper.Activity.HomeActivity;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;

import com.app.temproject.shipper.Fragment.Shipper.SPHomeFragment;
import com.app.temproject.shipper.Fragment.Shipper.SPRequestFragment;
import com.app.temproject.shipper.Fragment.Shipper.SPNotificationFragment;
import com.bumptech.glide.Glide;

public class SPHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean doubleBackToExitPressOnce = false;

    private View header;
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvAddress;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_activity_home);
        initView();

    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddRequest);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SPHomeFragment SPHomeFragment = new SPHomeFragment();
        fragmentTransaction.replace(R.id.flContent, SPHomeFragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        header = LayoutInflater.from(this).inflate(R.layout.sp_nav_header_home, null);
        ivAvatar = (ImageView) header.findViewById(R.id.ivAvatar);
        tvName = (TextView) header.findViewById(R.id.tvName);
        tvAddress = (TextView) header.findViewById(R.id.tvAddress);
        try{
            tvAddress.setText(ProjectManagement.shipper.getAddress());
            tvName.setText(ProjectManagement.shipper.getName());
            Glide.with(this).load(ProjectManagement.shipper.getAvatar()).centerCrop().into(ivAvatar);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(doubleBackToExitPressOnce){
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressOnce = true;

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Do you want to exit?");



            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface arg0, int arg1){
                    finish();

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });

            alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface arg0, int arg1){
                }
            });
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    doubleBackToExitPressOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (id){
            case R.id.nav_home:{
                SPHomeFragment spHomeFragment = new SPHomeFragment();

                fragmentTransaction.replace(R.id.flContent, spHomeFragment);
                break;
            }
            case R.id.nav_notification:{
                SPNotificationFragment SPNotificationFragment = new SPNotificationFragment();
                fragmentTransaction.replace(R.id.flContent, SPNotificationFragment);
                break;
            }
            case R.id.nav_logout:{
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_menu_manage)
                        .setTitle(R.string.logout)
                        .setMessage(R.string.really_want_to_logout)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    FileProcessing.deleteInternalStorageFile(Constant.PATH_TO_LOGIN_INFORMATION_FILE, SPHomeActivity.this);
                                    ProjectManagement.shipper = null;
                                    Intent intent = new Intent(SPHomeActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        })
                        .show();
                break;
            }
//            case R.id.nav_message:{
//                SPMessageFragment SPMessageFragment = new SPMessageFragment();
//                fragmentTransaction.replace(R.id.flContent, SPMessageFragment);
//                break;
//            }
//            case R.id.nav_help:
//                break;
//            case R.id.nav_info:
//                break;
//            case R.id.nav_share:
//                break;
            default:
                break;
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}