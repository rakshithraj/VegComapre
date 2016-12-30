package com.app.taza_price.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    ArrayList<VegPriceDetail> mVegPriceDetailFilterList = new ArrayList<VegPriceDetail>();
    int selectVegIndex = -1;
    VegPriceAdapter vegPriceAdapter;

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

        RecyclerView priceList = (RecyclerView) getView().findViewById(R.id.priceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        priceList.setLayoutManager(linearLayoutManager);
        vegPriceAdapter = new VegPriceAdapter(mVegPriceDetailFilterList);
        priceList.setAdapter(vegPriceAdapter);
        getView().findViewById(R.id.rlVeg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterList();
            }
        });
    }


    private void showFilterList() {


        if (mVegPriceDetailList == null)
            return;
        final AlertDialog.Builder ad = new AlertDialog.Builder(context);

        final String list[] = new String[mVegPriceDetailList.size() + 1];
        list[0] = "Select All";
        for (int i = 1; i < mVegPriceDetailList.size() + 1; i++) {
            list[i] = mVegPriceDetailList.get(i-1).getVeg_name();
        }

        ad.setSingleChoiceItems(list, selectVegIndex, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                selectVegIndex = arg1;
                ((TextView) getView().findViewById(R.id.tvVeg)).setText(list[arg1]);
                arg0.dismiss();

                mVegPriceDetailFilterList.clear();
                if (selectVegIndex == 0)
                    mVegPriceDetailFilterList.addAll(mVegPriceDetailList);
                else {
                    mVegPriceDetailFilterList.add(mVegPriceDetailList.get(selectVegIndex - 1));
                }

                vegPriceAdapter.notifyDataSetChanged();

            }
        });
        ad.show();
    }


    public static VegPriceFragment getNewInstance(ArrayList<VegPriceDetail> mVegPriceDetailList) {
        VegPriceFragment vegPriceFragment = new VegPriceFragment();
        vegPriceFragment.setData(mVegPriceDetailList);
        return vegPriceFragment;
    }


    private void setData(ArrayList<VegPriceDetail> mVegPriceDetailList) {
        this.mVegPriceDetailList = mVegPriceDetailList;
        mVegPriceDetailFilterList.addAll(mVegPriceDetailList);
    }
}
