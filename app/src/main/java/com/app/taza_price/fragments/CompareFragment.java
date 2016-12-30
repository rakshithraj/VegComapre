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
import com.app.taza_price.adapters.ComparePriceAdapter;
import com.app.taza_price.model.ShopComparePrice;

import java.util.ArrayList;

import static com.app.taza_price.ComaparePriceActivity.VEG;

/**
 * Created by Rakshith on 12/22/2016.
 */

public class CompareFragment extends Fragment {
    ArrayList<ShopComparePrice> mShopComparePrice = new ArrayList<ShopComparePrice>();
    ArrayList<ShopComparePrice> mShopFilterComparePrice = new ArrayList<ShopComparePrice>();

    Context context;
    ArrayList<String> mShopList;
    int TYPE;
    int selectCompareIndex=-1;
    ComparePriceAdapter fruitPriceAdapter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public static Fragment getNewInstance(ArrayList<ShopComparePrice> shopComparePrice, ArrayList<String> shopList, int TYPE) {
        CompareFragment  compareFragment = new CompareFragment();
        compareFragment.setData(shopComparePrice,shopList,TYPE);
        return compareFragment;
    }

    private void setData(ArrayList<ShopComparePrice> shopComparePrice, ArrayList<String> shopList, int TYPE) {
        this.mShopComparePrice=shopComparePrice;
        mShopList=shopList;
        mShopFilterComparePrice.addAll(mShopComparePrice);
        this.TYPE=TYPE;
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
         fruitPriceAdapter = new ComparePriceAdapter(mShopFilterComparePrice,mShopList);
        priceList.setAdapter(fruitPriceAdapter);
        TextView tvCompareFilter; tvCompareFilter=(TextView) getView().findViewById(R.id.tvCompareFilter);
        if(TYPE==VEG){
            tvCompareFilter.setText("Select Vegitables");
        }else{
            tvCompareFilter.setText("Select Fruits");

        }


        getView().findViewById(R.id.rlCompareFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterList();
            }
        });
    }

    private void showFilterList() {


        if (mShopComparePrice == null)
            return;
        final AlertDialog.Builder ad = new AlertDialog.Builder(context);

        final String list[] = new String[mShopComparePrice.size() + 1];
        list[0] = "Select All";
        for (int i = 1; i < mShopComparePrice.size() + 1; i++) {
            list[i] = mShopComparePrice.get(i-1).getName();
        }

        ad.setSingleChoiceItems(list, selectCompareIndex, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                selectCompareIndex = arg1;
                ((TextView) getView().findViewById(R.id.tvCompareFilter)).setText(list[arg1]);
                arg0.dismiss();

                mShopFilterComparePrice.clear();
                if (selectCompareIndex == 0)
                    mShopFilterComparePrice.addAll(mShopComparePrice);
                else {
                    mShopFilterComparePrice.add(mShopComparePrice.get(selectCompareIndex - 1));
                }

                fruitPriceAdapter.notifyDataSetChanged();

            }
        });
        ad.show();
    }
}
