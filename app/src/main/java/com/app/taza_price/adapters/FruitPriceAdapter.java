package com.app.taza_price.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.taza_price.R;
import com.app.taza_price.model.FruitPriceDetail;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/20/2016.
 */

public class FruitPriceAdapter  extends RecyclerView.Adapter {

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
    ArrayList<FruitPriceDetail> mFruitPriceDetailList;
    public FruitPriceAdapter(ArrayList<FruitPriceDetail> fruitPriceDetailList) {
        this.mFruitPriceDetailList=fruitPriceDetailList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_price,null);
        FruitPriceAdapter.PriceHolder priceHolder = new FruitPriceAdapter.PriceHolder(view);
        return priceHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        FruitPriceDetail fruitPriceDetail=mFruitPriceDetailList.get(position);
        if(fruitPriceDetail==null)
            return;
        FruitPriceAdapter.PriceHolder priceHolder=(FruitPriceAdapter.PriceHolder)holder;
        priceHolder.tvName.setText(fruitPriceDetail.getVeg_name());
        priceHolder.tvUnit.setText("1 "+fruitPriceDetail.getUnit());
        priceHolder.tvPrice.setText(fruitPriceDetail.getPrice()+" rs");



    }

    @Override
    public int getItemCount() {
        return mFruitPriceDetailList.size();
    }
}
