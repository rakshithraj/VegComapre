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
import com.app.taza_price.adapters.VegPriceAdapter;
import com.app.taza_price.model.VegPriceDetail;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/19/2016.
 */

public class VegPriceFragment extends Fragment {

    Context context;
    ArrayList<VegPriceDetail> mVegPriceDetailList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_veg_price, container, false);
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
        VegPriceAdapter vegPriceAdapter = new VegPriceAdapter(mVegPriceDetailList);
        priceList.setAdapter(vegPriceAdapter);

    }

    public static VegPriceFragment getNewInstance(ArrayList<VegPriceDetail> mVegPriceDetailList) {
        VegPriceFragment vegPriceFragment = new VegPriceFragment();
        vegPriceFragment.setData(mVegPriceDetailList);
        return vegPriceFragment;
    }



    private void setData(ArrayList<VegPriceDetail> mVegPriceDetailList) {
        this.mVegPriceDetailList = mVegPriceDetailList;
    }
}
