package com.app.temproject.shipper.Adapter.Shipper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.temproject.shipper.Activity.Shipper.SPDetailRequestActivity;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;

import java.util.List;

/**
 * Created by Admin on 12/11/2016.
 */

public class SPRequestAdapter extends RecyclerView.Adapter<SPRequestAdapter.ViewHolder> {
    private Context context;
    private List<Request> requests;

    public SPRequestAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
    }

    @Override
    public SPRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_request_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SPRequestAdapter.ViewHolder holder, int position) {
        final Request request = requests.get(position);
        String storeName = request.getStoreName();
        String storePosition = request.getStorePosition();
        String destination  = request.getDestination();
        String productName = request.getProductName();
        int price = request.getPrice();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SPDetailRequestActivity.class);
                intent.putExtra(Constant.KEY_REQUEST_ID, request.getId());
//                intent.putExtra(Constant.KEY_USER_ID,  ProjectManagement.shipper.getId());
                context.startActivity(intent);

            }
        });

        holder.tvStoreName.setText(Constant.STORE + storeName);
        holder.tvDestination.setText(Constant.FROM + storePosition + Constant.TO + destination);
        holder.tvProductName.setText(productName);
        holder.tvPrice.setText(Constant.PRICE + price + " " + Constant.THOUSAND_DONG);

    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStoreName;
        private TextView tvDestination;
        private TextView tvProductName;
        private TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStoreName = (TextView) itemView.findViewById(R.id.tvStoreName);
            tvDestination = (TextView) itemView.findViewById(R.id.tvDestination);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        }
    }
}
