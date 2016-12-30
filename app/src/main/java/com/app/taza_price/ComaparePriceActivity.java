package com.app.taza_price;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.app.taza_price.Webservice.ConnectWebService;
import com.app.taza_price.appInterface.ServerResponseInterface;
import com.app.taza_price.fragments.CompareFragment;
import com.app.taza_price.model.ShopComparePrice;
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
import static com.app.taza_price.utility.AppConstants.SUCESS;

public class ComaparePriceActivity extends BaseActivity {
    final String content[] = {"Vegitables", "Fruits"};
    public final static int VEG = 0;
    final static int FRUIT = 1;

    ArrayList<String> seletedShopsId;
    ArrayList<ShopComparePrice> mVegComparePrice = new ArrayList<ShopComparePrice>();
    ArrayList<ShopComparePrice> mFruitComparePrice = new ArrayList<ShopComparePrice>();
    ArrayList<String> mShopList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comapare_price);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        seletedShopsId = (ArrayList<String>) getIntent().getSerializableExtra(COMAPRE_SHOPS);
        callCompareApi();

        findViewById(R.id.lyRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCompareApi();
            }
        });

    }

    private void setViewPageAdapter() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        ComaparePriceActivity.ViewPagerAdapter mSectionsPagerAdapter = new ComaparePriceActivity.ViewPagerAdapter(getSupportFragmentManager(), content);
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void callCompareApi() {

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

                        mVegComparePrice.addAll((ArrayList<ShopComparePrice>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<ShopComparePrice>>() {
                        }.getType()));

                        jsonArray = jsonObject.getJSONArray("fruits");

                        mFruitComparePrice.addAll((ArrayList<ShopComparePrice>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<ShopComparePrice>>() {
                        }.getType()));

                        jsonArray = jsonObject.getJSONArray("shops");

                        mShopList.addAll((ArrayList<String>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<String>>() {
                        }.getType()));
                        setViewPageAdapter();

                        ComaparePriceActivity.this.findViewById(R.id.lyRefresh).setVisibility(View.GONE);


                    } else {
                        Utility.alerDialog(ComaparePriceActivity.this, ComaparePriceActivity.this.getResources().getString(R.string.please_try_again));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServerError(String s) {
                Utility.alerDialog(ComaparePriceActivity.this, ComaparePriceActivity.this.getResources().getString(R.string.please_try_again));


            }


        });

        String shopsId = TextUtils.join(",", seletedShopsId);

        Map<String, String> param = new HashMap<>();
        param.put("shops_id", shopsId);


        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.COMAPRE_PRICE, this, param, true);

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

                    return CompareFragment.getNewInstance(mVegComparePrice,mShopList,VEG);
                case FRUIT:
                    return CompareFragment.getNewInstance(mFruitComparePrice,mShopList,FRUIT);


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
