package com.app.taza_price.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.taza_price.R;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/22/2016.
 */

public class CompareShopAdapter extends RecyclerView.Adapter {
    ArrayList<String> mPriceList;
    ArrayList<String> mShopList;

    private class CompareShopHolder extends RecyclerView.ViewHolder {
        TextView tvShopName,tvUnit,tvPrice;
        public CompareShopHolder(View itemView) {
            super(itemView);
            tvShopName=(TextView) itemView.findViewById(R.id.tvShopName);
            tvUnit=(TextView) itemView.findViewById(R.id.tvUnit);
            tvPrice=(TextView) itemView.findViewById(R.id.tvPrice);

        }
    }


    public CompareShopAdapter(ArrayList<String> priceList, ArrayList<String> shopList) {
        mPriceList=priceList;
        mShopList=shopList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comapre_shop,null);
        CompareShopAdapter.CompareShopHolder compareShopHolder = new CompareShopAdapter.CompareShopHolder(view);
        return compareShopHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String price=mPriceList.get(position);
        if(price==null)
            return;
        CompareShopAdapter.CompareShopHolder compareShopHolder=(CompareShopAdapter.CompareShopHolder)holder;
        compareShopHolder.tvShopName.setText(mShopList.get(position));
        compareShopHolder.tvPrice.setText(": "+price+" Rs");

    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }


}
