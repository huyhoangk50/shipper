package com.app.temproject.shipper.Shipper;

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

public class SPNotificationAdapter extends RecyclerView.Adapter<SPNotificationAdapter.MyViewHolder> {

    private List<Notification> SPNotificationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView STActorName;
        private TextView STAction;
        private TextView STCreatedTime;

        public MyViewHolder(View view) {
            super(view);
            STActorName = (TextView) view.findViewById(R.id.tv_STActorName);
            STAction = (TextView) view.findViewById(R.id.tv_STAction);
            STCreatedTime = (TextView) view.findViewById(R.id.tv_STCreatedTime);
        }
    }


    public SPNotificationAdapter(List<Notification> SPNotificationList) {
        this.SPNotificationList = SPNotificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sp_notification_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notification = SPNotificationList.get(position);

        holder.STActorName.setText(notification.getActorName());

        if(notification.getCode() == Constant.ST_ACCEPT_CODE){
            holder.STAction.setText("đồng ý lời đề nghị của bạn ");
        } else if(notification.getCode() == Constant.ST_REFUSE_CODE){
            holder.STAction.setText("từ chối lời đề nghị của bạn ");
        } else if(notification.getCode() == Constant.SP_REQUEST_CODE){
            holder.STAction.setText("gửi lời đề nghị của bạn ");
        } else if(notification.getCode() == Constant.SP_CANCEL_CODE){
            holder.STAction.setText("hủy bỏ lời đề nghị của bạn ");
        }

        holder.STCreatedTime.setText(notification.getCreatedTime());
    }

    @Override
    public int getItemCount() {
        return SPNotificationList.size();
    }
}