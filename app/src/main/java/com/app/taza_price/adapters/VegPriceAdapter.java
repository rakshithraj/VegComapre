package com.app.taza_price.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.taza_price.R;
import com.app.taza_price.model.VegPriceDetail;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/19/2016.
 */

public class VegPriceAdapter extends RecyclerView.Adapter {

    class PriceHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvUnit,tvPrice;
        View view;
        public PriceHolder(View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvUnit=(TextView)itemView.findViewById(R.id.tvUnit);
            tvPrice=(TextView)itemView.findViewById(R.id.tvPrice);
            view=itemView;

        }
    }
    ArrayList<VegPriceDetail> mVegPriceDetailList;
    public VegPriceAdapter(ArrayList<VegPriceDetail> vegPriceDetailList) {
        this.mVegPriceDetailList=vegPriceDetailList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_price,null);
        PriceHolder priceHolder = new PriceHolder(view);
        return priceHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        VegPriceDetail vegPriceDetail=mVegPriceDetailList.get(position);
        if(vegPriceDetail==null)
            return;
        PriceHolder  priceHolder=(PriceHolder)holder;
        priceHolder.tvName.setText(vegPriceDetail.getVeg_name());
        priceHolder.tvUnit.setText("1 "+vegPriceDetail.getUnit());
        priceHolder.tvPrice.setText(vegPriceDetail.getPrice()+" rs");



    }

    @Override
    public int getItemCount() {
        return mVegPriceDetailList.size();
    }
}
