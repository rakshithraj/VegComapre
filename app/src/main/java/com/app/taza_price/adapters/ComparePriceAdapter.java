package com.app.taza_price.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.taza_price.R;
import com.app.taza_price.model.ShopComparePrice;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/22/2016.
 */

public class ComparePriceAdapter extends RecyclerView.Adapter {
    ArrayList<ShopComparePrice> mShopComparePrice;
    ArrayList<String> mShopList;
    public ComparePriceAdapter(ArrayList<ShopComparePrice> shopComparePrice, ArrayList<String> shopList) {
        this.mShopComparePrice=shopComparePrice;
        mShopList=shopList;
    }

    private class ComparePriceHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        RecyclerView comparePriceList;
        public ComparePriceHolder(View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            comparePriceList=(RecyclerView)itemView.findViewById(R.id.comparePriceList);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comapre_price,null);
        ComparePriceAdapter.ComparePriceHolder comparePriceHolder = new ComparePriceAdapter.ComparePriceHolder(view);
        return comparePriceHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShopComparePrice shopComparePrice=mShopComparePrice.get(position);
        if(shopComparePrice==null)
            return;
        ComparePriceAdapter.ComparePriceHolder comparePriceHolder=(ComparePriceAdapter.ComparePriceHolder)holder;
        comparePriceHolder.tvName.setText(shopComparePrice.getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(comparePriceHolder.comparePriceList.getContext());
        comparePriceHolder.comparePriceList.setLayoutManager(linearLayoutManager);
        CompareShopAdapter compareShopAdapter = new CompareShopAdapter(shopComparePrice.getPrice(),mShopList);
        comparePriceHolder.comparePriceList.setAdapter(compareShopAdapter);
    }

    @Override
    public int getItemCount() {
        return mShopComparePrice.size();
    }


}
