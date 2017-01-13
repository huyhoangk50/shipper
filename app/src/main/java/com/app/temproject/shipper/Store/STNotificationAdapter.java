package com.app.temproject.shipper.Store;

/**
 * Created by hieppso194 on 09/12/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.temproject.shipper.Object.Notification;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;

import java.util.List;

public class STNotificationAdapter extends RecyclerView.Adapter<STNotificationAdapter.MyViewHolder> {

    private List<Notification> notifications;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private ImageView ivNotificationType;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            ivNotificationType = (ImageView) view.findViewById(R.id.ivNotificationType);
        }
    }


    public STNotificationAdapter(Activity activity, List<Notification> notifications) {
        this.notifications = notifications;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.st_notification_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Notification notification = this.notifications.get(position);
        notification.setMessage();
        notification.setTitle();
        holder.tvTitle.setText(notification.getTitle());
        holder.tvContent.setText(notification.getMessage());
        holder.tvTime.setText(notification.getCreatedTime());

        switch (notification.getCode()){
            case Notification.SHIPPER_APPLY_REQUEST_NOTIFICATION_CODE:
                holder.ivNotificationType.setImageResource(R.drawable.new_notification);
                break;
            case Notification.SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_CODE:
                holder.ivNotificationType.setImageResource(R.drawable.require_accepte_notification);
                break;
            case Notification.SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_CODE:
                holder.ivNotificationType.setImageResource(R.drawable.delete_notification);
                break;
            default:
                break;

//                public static final int SHIPPER_APPLY_REQUEST_NOTIFICATION_CODE = 1;
//                public static final int SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_CODE = 2;
//                public static final int SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_CODE = 3;
//                public static final int STORE_ACCEPT_SHIPPER_NOTIFICATION_CODE = 4;
//                public static final int STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_CODE = 5;
//                public static final int STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_CODE = 6;
//                public static final int STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_CODE = 7;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, STDetailRequestActivity.class);
                intent.putExtra(Constant.KEY_REQUEST_ID, notification.getRequestId());
                activity.startActivity(intent);
//                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}