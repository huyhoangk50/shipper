package com.app.temproject.shipper.Store;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.temproject.shipper.Object.Notification;
import com.app.temproject.shipper.Object.OnNotifyListener;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.Shipper.SPDetailRequestActivity;

public class STHomeFragment extends Fragment implements OnNotifyListener{
    private RelativeLayout rlContent;

    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        View rootView = inflater.inflate(R.layout.st_fragment_home, container, false);
//       View rootView = inflater.inflate(R.layout.sp_fragment_home, container, false);
        STRequestPagerAdapter stRequestPagerAdapter = new STRequestPagerAdapter(getActivity().getSupportFragmentManager(),getContext());
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.vpRequest);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tlRequest);
        rlContent = (RelativeLayout) rootView.findViewById(R.id.rlContent);

        if(ProjectManagement.socketConnection!=null){
            ProjectManagement.socketConnection.setOnNotifyListener(this);
        }

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(stRequestPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.waiting);
        tabLayout.getTabAt(1).setIcon(R.drawable.processing);
        tabLayout.getTabAt(2).setIcon(R.drawable.complete);
        return rootView;
    }


    @Override
    public void onNotify(final Notification notification) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    final LinearLayout llNotification = (LinearLayout) inflater.inflate(R.layout.notification_item, null);
                    TextView tvTitle = (TextView) llNotification.findViewById(R.id.tvTitle);
                    TextView tvContent =(TextView) llNotification.findViewById(R.id.tvContent);
                    TextView tvTime = (TextView)llNotification.findViewById(R.id.tvTime);

                    tvContent.setText(notification.getMessage());
                    tvTime.setText(notification.getCreatedTime());
                    tvTitle.setText(notification.getTitle());
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                    llNotification.setLayoutParams(layoutParams);
                    rlContent.removeAllViews();
                    rlContent.addView(llNotification);
                    llNotification.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), STDetailRequestActivity.class);
                            intent.putExtra(Constant.KEY_REQUEST_ID, notification.getRequestId());
                            intent.putExtra(Constant.KEY_REQUEST_STATUS, Constant.PROCESSING_REQUEST);
                            startActivity(intent);
                        }
                    });

                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    float screenWidth = displaymetrics.widthPixels;
                    TranslateAnimation animation = new TranslateAnimation(-(screenWidth + llNotification.getWidth()) / 2, 0, 0, 0);
                    animation.setDuration(1500);
                    animation.setFillAfter(true);
                    llNotification.setAnimation(animation);
                    llNotification.startAnimation(animation);

                    Thread showAnimation = new Thread() {
                        public void run() {
                            try {
                                sleep(3000);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rlContent.removeView(llNotification);
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    showAnimation.start();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
