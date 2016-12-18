package com.app.vegcomapre.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.vegcomapre.R;
import com.app.vegcomapre.ShopListActivity;

import java.util.ArrayList;

/**
 * Created by rakshith raj on 18-12-2016.
 */

public class ShopListAdapter extends RecyclerView.Adapter {


    ArrayList<ShopListActivity.ShopDetail> mShopDetailList;
    public  ShopListAdapter(ArrayList<ShopListActivity.ShopDetail> mShopDetailList){
        this.mShopDetailList=mShopDetailList;
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {

         TextView tvShopName,tvShopAddress;
        View view;
        public ShopViewHolder(View view) {
            super(view);
            this.view=view;
            tvShopName=(TextView)view.findViewById(R.id.tvShopName);
            tvShopAddress=(TextView)view.findViewById(R.id.tvShopAddress);

        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shop,null);
        ShopViewHolder shopViewHolder = new ShopViewHolder(view);
        return  shopViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ShopListActivity.ShopDetail shopDetail=mShopDetailList.get(position);
        if(shopDetail==null)
            return;;

       ShopViewHolder shopViewHolder = (ShopViewHolder)holder;
        shopViewHolder.tvShopName.setText(shopDetail.getShop_name());
        shopViewHolder.tvShopAddress.setText(shopDetail.getShop_address());

    }

    @Override
    public int getItemCount() {
        return mShopDetailList.size();
    }
}
