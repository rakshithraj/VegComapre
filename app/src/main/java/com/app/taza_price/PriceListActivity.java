package com.app.taza_price;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.app.taza_price.Webservice.ConnectWebService;
import com.app.taza_price.appInterface.ServerResponseInterface;
import com.app.taza_price.fragments.FruitsPriceFragment;
import com.app.taza_price.fragments.VegPriceFragment;
import com.app.taza_price.model.FruitPriceDetail;
import com.app.taza_price.model.VegPriceDetail;
import com.app.taza_price.utility.Config;
import com.app.taza_price.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.taza_price.utility.AppConstants.COMAPRE_SHOPS;
import static com.app.taza_price.utility.AppConstants.SELECTED_SHOP_ID;
import static com.app.taza_price.utility.AppConstants.SELECTED_SHOP_NAME;
import static com.app.taza_price.utility.AppConstants.SELECTED_SHOP_POSITION;
import static com.app.taza_price.utility.AppConstants.SUCESS;
import static com.app.taza_price.utility.AppStatic.SHOP_LIST_TO_COMAPRE;

public class PriceListActivity extends BaseActivity {
    final String content[] = {"Vegitables", "Fruits"};
    final static int VEG = 0, FRUIT = 1;
    String mSelectedShop;
    String mSelectedShopName;
    int mSelectedShopPosition;
    ArrayList<VegPriceDetail> mVegPriceDetailList = new ArrayList<VegPriceDetail>();
    ArrayList<FruitPriceDetail> mFruitPriceDetailList = new ArrayList<FruitPriceDetail>();
     CharSequence[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSelectedShop = getIntent().getStringExtra(SELECTED_SHOP_ID);
        mSelectedShopPosition = getIntent().getIntExtra(SELECTED_SHOP_POSITION, -1);
        mSelectedShopName= getIntent().getStringExtra(SELECTED_SHOP_NAME);
        TextView tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvTitle.setText(mSelectedShopName);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        items=new CharSequence[SHOP_LIST_TO_COMAPRE.size()];
        for(int i=0;i<SHOP_LIST_TO_COMAPRE.size();i++){
            items[i]=SHOP_LIST_TO_COMAPRE.get(i).getShop_name();
        }
        callGetPriceDetails();

        findViewById(R.id.lyRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callGetPriceDetails();
            }
        });
    }

    private void callGetPriceDetails() {

        ConnectWebService connectWebService = new ConnectWebService();

        connectWebService.setOnServerResponse(new ServerResponseInterface() {

            @Override
            public void setLoading(boolean value) {


            }

            @Override
            public void onServerResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals(SUCESS)) {

                        jsonObject = jsonObject.getJSONObject("data");


                        Gson gson = new Gson();

                        JSONArray jsonArray = jsonObject.getJSONArray("vegetables");

                        mVegPriceDetailList.addAll((ArrayList<VegPriceDetail>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<VegPriceDetail>>() {
                        }.getType()));


                        jsonArray = jsonObject.getJSONArray("fruits");

                        gson = new Gson();
                        mFruitPriceDetailList.addAll((ArrayList<FruitPriceDetail>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<FruitPriceDetail>>() {
                        }.getType()));

                        setViewPager();

                        PriceListActivity.this.findViewById(R.id.lyRefresh).setVisibility(View.GONE);


                    } else {
                        Utility.alerDialog(PriceListActivity.this, PriceListActivity.this.getResources().getString(R.string.please_try_again));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServerError(String s) {
                Utility.alerDialog(PriceListActivity.this, PriceListActivity.this.getResources().getString(R.string.please_try_again));


            }

        });

        Map<String, String> param = new HashMap<>();
        param.put("shop_id", mSelectedShop + "");


        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.SEARCH_PRICE_LIST, this, param, true);


    }


    private void setViewPager() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter mSectionsPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), content);
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


     ArrayList<String> seletedShopsId=new ArrayList<String>();

    public void comparePrice(View view) {
        seletedShopsId.clear();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select the following market to compared.")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedShopsId.add(SHOP_LIST_TO_COMAPRE.get(indexSelected).getShop_id());
                        } else if (seletedShopsId.contains(SHOP_LIST_TO_COMAPRE.get(indexSelected).getShop_id())) {
                            // Else, if the item is already in the array, remove it
                            seletedShopsId.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("Compare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        seletedShopsId.add(0,mSelectedShop);
                        Intent intent = new Intent(PriceListActivity.this,ComaparePriceActivity.class);
                        intent.putExtra(COMAPRE_SHOPS,seletedShopsId);
                        PriceListActivity.this.startActivity(intent);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();


                    }
                }).create();
        dialog.show();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        String content[];


        public ViewPagerAdapter(FragmentManager fm, String content[]) {
            super(fm);
            this.content = content;

        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case VEG:

                    return VegPriceFragment.getNewInstance(mVegPriceDetailList);
                case FRUIT:
                    return FruitsPriceFragment.getNewInstance(mFruitPriceDetailList);


            }
            return null;
        }


        @Override
        public int getCount() {
            return content.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return content[position];
        }
    }


}
