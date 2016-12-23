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
import com.app.taza_price.adapters.FruitPriceAdapter;
import com.app.taza_price.model.FruitPriceDetail;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/19/2016.
 */

public class FruitsPriceFragment extends Fragment {
    ArrayList<FruitPriceDetail> mFruitPriceDetailList;
    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_fruits_price, container, false);
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
        FruitPriceAdapter fruitPriceAdapter = new FruitPriceAdapter(mFruitPriceDetailList);
        priceList.setAdapter(fruitPriceAdapter);

    }
    public static FruitsPriceFragment getNewInstance(ArrayList<FruitPriceDetail> mFruitPriceDetailList) {
        FruitsPriceFragment fruitsPriceFragment = new FruitsPriceFragment();
        fruitsPriceFragment.setData(mFruitPriceDetailList);
       return fruitsPriceFragment;
    }


    private void setData(ArrayList<FruitPriceDetail> mVegPriceDetailList) {
        this.mFruitPriceDetailList=mVegPriceDetailList;
    }
}
