package com.app.taza_price.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    int selectFruitsIndex = -1;
    ArrayList<FruitPriceDetail> mFruitPriceDetailFilterList = new ArrayList<FruitPriceDetail>();
    FruitPriceAdapter fruitPriceAdapter;

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


        RecyclerView priceList = (RecyclerView) getView().findViewById(R.id.priceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        priceList.setLayoutManager(linearLayoutManager);
        fruitPriceAdapter = new FruitPriceAdapter(mFruitPriceDetailList);
        priceList.setAdapter(fruitPriceAdapter);

        getView().findViewById(R.id.rlFruits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterList();
            }
        });

    }

    private void showFilterList() {


        if (mFruitPriceDetailList == null)
            return;
        final AlertDialog.Builder ad = new AlertDialog.Builder(context);

        final String list[] = new String[mFruitPriceDetailList.size() + 1];
        list[0] = "Select All";
        for (int i = 1; i < mFruitPriceDetailList.size() + 1; i++) {
            list[i] = mFruitPriceDetailList.get(i-1).getVeg_name();
        }

        ad.setSingleChoiceItems(list, selectFruitsIndex, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                selectFruitsIndex = arg1;
                ((TextView) getView().findViewById(R.id.tvFruits)).setText(list[arg1]);
                arg0.dismiss();

                mFruitPriceDetailFilterList.clear();
                if (selectFruitsIndex == 0)
                    mFruitPriceDetailFilterList.addAll(mFruitPriceDetailList);
                else {
                    mFruitPriceDetailFilterList.add(mFruitPriceDetailList.get(selectFruitsIndex - 1));
                }

                fruitPriceAdapter.notifyDataSetChanged();

            }
        });
        ad.show();
    }

    public static FruitsPriceFragment getNewInstance(ArrayList<FruitPriceDetail> mFruitPriceDetailList) {
        FruitsPriceFragment fruitsPriceFragment = new FruitsPriceFragment();
        fruitsPriceFragment.setData(mFruitPriceDetailList);
        return fruitsPriceFragment;
    }


    private void setData(ArrayList<FruitPriceDetail> mVegPriceDetailList) {
        this.mFruitPriceDetailList = mVegPriceDetailList;
        this.mFruitPriceDetailFilterList.addAll(mVegPriceDetailList);
    }
}
