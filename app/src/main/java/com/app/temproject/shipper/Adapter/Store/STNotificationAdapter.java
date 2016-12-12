package com.app.temproject.shipper.Adapter.Store;

/**
 * Created by hieppso194 on 09/12/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.temproject.shipper.Object.Notification;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;

import java.util.List;

public class STNotificationAdapter extends RecyclerView.Adapter<STNotificationAdapter.MyViewHolder> {

    private List<Notification> STNotificationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView SPActorName;
        private TextView SPAction;
        private TextView SPCreatedTime;

        public MyViewHolder(View view) {
            super(view);
            SPActorName = (TextView) view.findViewById(R.id.tv_SPActorName);
            SPAction = (TextView) view.findViewById(R.id.tv_SPAction);
            SPCreatedTime = (TextView) view.findViewById(R.id.tv_SPCreatedTime);
        }
    }


    public STNotificationAdapter(List<Notification> STNotificationList) {
        this.STNotificationList = STNotificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.st_notification_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notification = STNotificationList.get(position);

        holder.SPActorName.setText(notification.getActorName());

        if(notification.getCode() == Constant.ST_ACCEPT_CODE){
            holder.SPAction.setText("đồng ý lời đề nghị của bạn ");
        } else if(notification.getCode() == Constant.ST_REFUSE_CODE){
            holder.SPAction.setText("từ chối lời đề nghị của bạn ");
        } else if(notification.getCode() == Constant.SP_REQUEST_CODE){
            holder.SPAction.setText("gửi lời đề nghị của bạn ");
        } else if(notification.getCode() == Constant.SP_CANCEL_CODE){
            holder.SPAction.setText("hủy bỏ lời đề nghị của bạn ");
        }

        holder.SPCreatedTime.setText(notification.getCreatedTime());
    }

    @Override
    public int getItemCount() {
        return STNotificationList.size();
    }
}