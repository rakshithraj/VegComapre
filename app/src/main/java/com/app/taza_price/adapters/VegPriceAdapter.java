package com.app.taza_price.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.taza_price.R;
import com.app.taza_price.Webservice.AppController;
import com.app.taza_price.model.VegPriceDetail;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/19/2016.
 */

public class VegPriceAdapter extends RecyclerView.Adapter {
    ImageLoader imageLoader;

    class PriceHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPriceKg,tvPriceGram;
        NetworkImageView images;
        View view;
        public PriceHolder(View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvPriceKg=(TextView)itemView.findViewById(R.id.tvPriceKg);
            tvPriceGram=(TextView)itemView.findViewById(R.id.tvPriceGram);
            images=(NetworkImageView)itemView.findViewById(R.id.image);
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
        priceHolder.tvPriceKg.setText(" "+vegPriceDetail.getPrice_kg()+" rs");
        priceHolder.tvPriceGram.setText(" "+vegPriceDetail.getPrice_gm()+" rs");

        if(imageLoader==null)
            imageLoader= AppController.getInstance().getImageLoader();

        priceHolder.images.setDefaultImageResId(R.mipmap.loading);
        priceHolder.images.setImageUrl(vegPriceDetail.getImage(),imageLoader);

    }

    @Override
    public int getItemCount() {
        return mVegPriceDetailList.size();
    }
}
