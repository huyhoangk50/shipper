package com.app.temproject.shipper.Activity.Store;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Activity.Shipper.SPDetailRequestActivity;
import com.app.temproject.shipper.Inteface.AcceptShipper;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.Object.Shipper;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Admin on 11/12/2016.
 */
public class BaseShipperAdapter extends RecyclerView.Adapter<BaseShipperAdapter.ViewHolder> {
    private Activity activity;
    private List<Shipper> shippers;
    private AcceptShipper acceptShipper;

    public BaseShipperAdapter(Activity activity, List<Shipper> shippers, AcceptShipper acceptShipper) {
        this.activity = activity;
        this.shippers = shippers;
        this.acceptShipper = acceptShipper;
    }

    @Override
    public BaseShipperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_shipper_item, parent, false);
        return new BaseShipperAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseShipperAdapter.ViewHolder holder, int position) {
        final Shipper shipper = this.shippers.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SPDetailRequestActivity.class);
                intent.putExtra(Constant.KEY_REQUEST_ID, shipper.getId());
//                intent.putExtra(Constant.KEY_USER_ID,  ProjectManagement.shipper.getId());
                activity.startActivity(intent);

            }
        });

        holder.tvName.setText(shipper.getName());
        Glide.with(activity).load(ProjectManagement.baseUrl + shipper.getAvatar()).into(holder.ivAvatar);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new
            }
        });
        holder.llAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptShipper.acceptShipper(shipper.getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return shippers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvName;
        private LinearLayout llAccept;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            llAccept = (LinearLayout) itemView.findViewById(R.id.llAccept);
        }
    }


}
