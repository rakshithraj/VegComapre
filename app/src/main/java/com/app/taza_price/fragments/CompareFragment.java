package com.app.taza_price.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.taza_price.R;
import com.app.taza_price.adapters.ComparePriceAdapter;
import com.app.taza_price.model.ShopComparePrice;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/22/2016.
 */

public class CompareFragment extends Fragment {
    ArrayList<ShopComparePrice> mShopComparePrice = new ArrayList<ShopComparePrice>();
    Context context;
    ArrayList<String> mShopList;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public static Fragment getNewInstance(ArrayList<ShopComparePrice> shopComparePrice, ArrayList<String> shopList) {
        CompareFragment  compareFragment = new CompareFragment();
        compareFragment.setData(shopComparePrice,shopList);
        return compareFragment;
    }

    private void setData(ArrayList<ShopComparePrice> shopComparePrice, ArrayList<String> shopList) {
        this.mShopComparePrice=shopComparePrice;
        mShopList=shopList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_compare_price, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        RecyclerView priceList =(RecyclerView)getView().findViewById(R.id.priceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        priceList.setLayoutManager(linearLayoutManager);
        ComparePriceAdapter fruitPriceAdapter = new ComparePriceAdapter(mShopComparePrice,mShopList);
        priceList.setAdapter(fruitPriceAdapter);

    }
}
