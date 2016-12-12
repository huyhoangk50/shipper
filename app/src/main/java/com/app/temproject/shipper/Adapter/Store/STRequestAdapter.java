package com.app.temproject.shipper.Adapter.Store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.temproject.shipper.Activity.Store.STDetailRequestActivity;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;

import java.util.List;

/**
 * Created by Admin on 09/12/2016.
 */

public class STRequestAdapter extends RecyclerView.Adapter<STRequestAdapter.ViewHolder> {
private Context context;
private List<Request> requests;

public STRequestAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
        }

@Override
public STRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.st_request_item, parent, false);
        return new ViewHolder(v);
        }

@Override
public void onBindViewHolder(STRequestAdapter.ViewHolder holder, int position) {
final Request request = requests.get(position);
        String storeName = request.getStoreName();
        String storePosition = request.getStorePosition();
        String destination  = request.getDestination();
        String productName = request.getProductName();
        double deposit = request.getDeposit();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(context, STDetailRequestActivity.class);
        intent.putExtra(Constant.KEY_REQUEST_ID, request.getId());
        intent.putExtra(Constant.KEY_REQUEST_STATUS, request.getStatus());
//                intent.putExtra(Constant.KEY_USER_ID,  ProjectManagement.shipper.getId());
        context.startActivity(intent);

        }
        });

        holder.tvCustomerName.setText(Constant.CUSTOMER + request.getCustomerName());
        holder.tvProductName.setText(productName);
        holder.tvDeposit.setText(deposit + "");

        }

@Override
public int getItemCount() {
        return requests.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {


    private TextView tvCustomerName;
    private TextView tvProductName;
    private TextView tvDeposit;

    public ViewHolder(View itemView) {
        super(itemView);
        tvCustomerName = (TextView) itemView.findViewById(R.id.tvCustomerName);
        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
        tvDeposit = (TextView) itemView.findViewById(R.id.tvDeposit);
    }
}
}