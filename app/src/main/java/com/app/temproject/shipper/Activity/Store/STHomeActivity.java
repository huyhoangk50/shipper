package com.app.temproject.shipper.Activity.Store;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.temproject.shipper.Fragment.Store.STHomeFragment;
import com.app.temproject.shipper.Fragment.Store.STNotificationFragment;
import com.app.temproject.shipper.R;

public class STHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fabAddRequest;
    private boolean doubleBackToExitPressOnce = false;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try{
            fabAddRequest = (FloatingActionButton) findViewById(R.id.fabAddRequest);

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
            STHomeFragment stHomeFragment = new STHomeFragment();
            fragmentTransaction.replace(R.id.flContent, stHomeFragment);
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();

            initView();
            setEvent();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void  initView(){

    }

    private void setEvent(){

        fabAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(STHomeActivity.this, STCreateRequestActivity.class);
                startActivity(intent);
            }
        });
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
        getMenuInflater().inflate(R.menu.sthome, menu);
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
                STHomeFragment stHomeFragment = new STHomeFragment();

                fragmentTransaction.replace(R.id.flContent, stHomeFragment);
                break;
            }
            case R.id.nav_notification:{
                STNotificationFragment stNotificationFragment = new STNotificationFragment();
                fragmentTransaction.replace(R.id.flContent, stNotificationFragment);
                break;
            }
            case R.id.nav_logout:{
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
