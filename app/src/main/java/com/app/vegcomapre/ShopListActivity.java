package com.app.vegcomapre;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.vegcomapre.Webservice.ConnectWebService;
import com.app.vegcomapre.adapters.ShopListAdapter;
import com.app.vegcomapre.appInterface.ServerResponseInterface;
import com.app.vegcomapre.utility.Config;
import com.app.vegcomapre.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.vegcomapre.utility.AppConstants.AREA;
import static com.app.vegcomapre.utility.AppConstants.CITY;
import static com.app.vegcomapre.utility.AppConstants.STATE;
import static com.app.vegcomapre.utility.AppConstants.SUCESS;

public class ShopListActivity extends AppCompatActivity {
    String selectState,selectCity,selectArea;

    ArrayList<ShopDetail>  mShopDetailList = new ArrayList<ShopDetail>();
    ShopListAdapter shopListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectState=this.getIntent().getStringExtra(STATE);
        selectCity=this.getIntent().getStringExtra(CITY);
        selectArea=this.getIntent().getStringExtra(AREA);
        intialize();
        callSerachShops();

    }

    private void intialize() {

        RecyclerView listShopList =(RecyclerView) this.findViewById(R.id.listShopList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listShopList.setLayoutManager(linearLayoutManager);

        

        shopListAdapter=new ShopListAdapter(mShopDetailList);
        listShopList.setAdapter(shopListAdapter);


    }


    private void callSerachShops() {

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
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if (status.equals(SUCESS)) {

                        Gson gson = new Gson();
                        mShopDetailList.addAll((ArrayList<ShopDetail>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<ShopDetail>>() {
                        }.getType()));
                        shopListAdapter.notifyDataSetChanged();;

                    } else {
                        Utility.alerDialog(ShopListActivity.this, ShopListActivity.this.getResources().getString(R.string.please_try_again));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServerError(String s) {
                Utility.alerDialog(ShopListActivity.this, ShopListActivity.this.getResources().getString(R.string.please_try_again));


            }

        });

        Map<String, String> param = new HashMap<>();
        param.put("state_id",selectState+"");
        param.put("city_id",selectCity+"");
        param.put("area_id",selectArea+"");

        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.SEARCH_SHOP_LIST, this, param, true);


    }


    public class ShopDetail{
        String shop_id;
        String shop_name;
        String shop_address;
        String state_id;
        String city_id;
        String area_id;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
    }

}
