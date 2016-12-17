package com.app.temproject.shipper.Store;

/**
 * Created by hieppso194 on 09/12/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.temproject.shipper.Object.Notification;
import com.app.temproject.shipper.R;

import java.util.List;

public class STNotificationAdapter extends RecyclerView.Adapter<STNotificationAdapter.MyViewHolder> {

    private List<Notification> notifications;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private ImageView ivTitle;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            ivTitle = (ImageView) view.findViewById(R.id.ivTitle);
        }
    }


    public STNotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.st_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Notification notification = this.notifications.get(position);
        notification.setMessage();
        notification.setTitle();
        holder.tvTitle.setText(notification.getTitle());
        holder.tvContent.setText(notification.getMessage());
        holder.tvTime.setText(notification.getCreatedTime());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}