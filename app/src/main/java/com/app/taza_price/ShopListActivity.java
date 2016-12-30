package com.app.taza_price;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.app.taza_price.Webservice.ConnectWebService;
import com.app.taza_price.adapters.ShopListAdapter;
import com.app.taza_price.appInterface.SelectShopInterface;
import com.app.taza_price.appInterface.ServerResponseInterface;
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

import static com.app.taza_price.utility.AppConstants.AREA;
import static com.app.taza_price.utility.AppConstants.AREA_LIST;
import static com.app.taza_price.utility.AppConstants.CITY;
import static com.app.taza_price.utility.AppConstants.SELECTED_SHOP_ID;
import static com.app.taza_price.utility.AppConstants.SELECTED_SHOP_NAME;
import static com.app.taza_price.utility.AppConstants.SELECTED_SHOP_POSITION;
import static com.app.taza_price.utility.AppConstants.STATE;
import static com.app.taza_price.utility.AppConstants.SUCESS;
import static com.app.taza_price.utility.AppStatic.SHOP_LIST_TO_COMAPRE;

public class ShopListActivity extends BaseActivity implements SelectShopInterface {
    String selectState, selectCity, selectArea;

    ArrayList<ShopDetail> mShopDetailList = new ArrayList<ShopDetail>();
    ShopListAdapter shopListAdapter;
    ArrayList<HomeActivity.Area> mAreaList = new   ArrayList<HomeActivity.Area> ();

    int selectAreaIndex=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectState = this.getIntent().getStringExtra(STATE);
        selectCity = this.getIntent().getStringExtra(CITY);
        selectArea = this.getIntent().getStringExtra(AREA);
        mAreaList= ( ArrayList<HomeActivity.Area>)this.getIntent().getSerializableExtra(AREA_LIST);

        intialize();
        callSerachShops();

        findViewById(R.id.rlArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAreaList();
            }
        });

    }



    private void showAreaList() {
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        final String list[] = new String[mAreaList.size()];
        for (int i = 0; i < mAreaList.size(); i++) {
            list[i] = mAreaList.get(i).getArea();
        }

        ad.setSingleChoiceItems(list, selectAreaIndex, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                selectAreaIndex = arg1;
                ((TextView) findViewById(R.id.tvArea)).setText(list[arg1]);

                arg0.dismiss();
                selectArea=mAreaList.get(selectAreaIndex).getArea_id();
                callSerachShops();

            }
        });
        ad.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SHOP_LIST_TO_COMAPRE.clear();
    }

    private void intialize() {

        RecyclerView listShopList = (RecyclerView) this.findViewById(R.id.listShopList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listShopList.setLayoutManager(linearLayoutManager);


        shopListAdapter = new ShopListAdapter(mShopDetailList);
        shopListAdapter.setSelectShopInterface(this);
        listShopList.setAdapter(shopListAdapter);


        findViewById(R.id.lyRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callSerachShops();
            }
        });
    }


    private void callSerachShops() {
        ShopListActivity.this.findViewById(R.id.lyRefresh).setVisibility(View.VISIBLE);

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

                        boolean flag=true;
                        try {
                            flag = jsonObject.getBoolean("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mShopDetailList.clear();
                        if(flag) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Gson gson = new Gson();
                            mShopDetailList.clear();
                            mShopDetailList.addAll((ArrayList<ShopDetail>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<ShopDetail>>() {
                            }.getType()));
                        }
                        shopListAdapter.notifyDataSetChanged();

                        ShopListActivity.this.findViewById(R.id.lyRefresh).setVisibility(View.GONE);
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
        param.put("state_id", selectState + "");
        param.put("city_id", selectCity + "");
        param.put("area_id", selectArea + "");

        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.SEARCH_SHOP_LIST, this, param, true);


    }

    @Override
    public void onShopSelected(String shop_id,String shop_name,int position) {
        SHOP_LIST_TO_COMAPRE.clear();
        SHOP_LIST_TO_COMAPRE.addAll(mShopDetailList);
        SHOP_LIST_TO_COMAPRE.remove(position);
        Intent intent = new Intent(this, PriceListActivity.class);
        intent.putExtra(SELECTED_SHOP_ID, shop_id);
        intent.putExtra(SELECTED_SHOP_POSITION, position);
        intent.putExtra(SELECTED_SHOP_NAME, shop_name);

        startActivity(intent);

    }


    public class ShopDetail {
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
