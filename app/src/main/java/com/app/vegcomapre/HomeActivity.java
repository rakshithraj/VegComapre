package com.app.vegcomapre;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.vegcomapre.Webservice.ConnectWebService;
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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener  {

    ArrayList<State> mStateList = new ArrayList<State>();
    ArrayList<City> mCityList = new ArrayList<City>();
    ArrayList<Area> mAreaList = new   ArrayList<Area> ();

    int selectStateIndex = -1;
    int selectCityIndex = -1;
    int selectAreaIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intializeClickListner();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void intializeClickListner() {

        findViewById(R.id.rlState).setOnClickListener(this);
        findViewById(R.id.rlCity).setOnClickListener(this);
        findViewById(R.id.rlArea).setOnClickListener(this);
        findViewById(R.id.btSearch).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.rlState:

                if (mStateList.size() == 0) {
                    callGetStateApi();
                    return;
                }
                showStateList();

                break;

            case R.id.rlCity:

                if (mCityList.size() == 0) {
                    callGetrCityApi();
                    return;
                }
                showCityList();

                break;
            case R.id.rlArea:
                if(selectCityIndex==-1){
                    Utility.alerDialog(this,getResources().getString(R.string.select_city));
                    return;
                }
                if (mAreaList.size() == 0) {

                    callGetAreaApi();
                    return;
                }
                showAreaList();

                break;
            case R.id.btSearch:

                  if(selectStateIndex==-1) {
                      Utility.alerDialog(this, this.getResources().getString(R.string.select_state));
                      return;
                  }
                if(selectCityIndex==-1) {
                    Utility.alerDialog(this, this.getResources().getString(R.string.select_city));
                    return;
                }
                if(selectAreaIndex==-1) {
                    Utility.alerDialog(this, this.getResources().getString(R.string.select_area));
                    return;
                }

                Intent intent = new Intent(this,ShopListActivity.class);
                intent.putExtra(STATE,mStateList.get(selectStateIndex).getState_id());
                intent.putExtra(CITY,mCityList.get(selectCityIndex).getCity_id());
                intent.putExtra(AREA,mAreaList.get(selectAreaIndex).getArea_id());

                startActivity(intent);




                break;


        }
    }


    private void callGetStateApi() {

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
                        mStateList = (ArrayList<State>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<State>>() {
                        }.getType());

                        showStateList();

                    } else {
                        Utility.alerDialog(HomeActivity.this, HomeActivity.this.getResources().getString(R.string.please_try_again));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServerError(String s) {
                Utility.alerDialog(HomeActivity.this, HomeActivity.this.getResources().getString(R.string.please_try_again));


            }

        });

        Map<String, String> param = new HashMap<>();
        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.STATE_LIST, this, param, true);


    }



    private void callGetAreaApi() {

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
                        mAreaList = (ArrayList<Area>) gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Area>>() {
                        }.getType());

                        showAreaList();

                    } else {
                        Utility.alerDialog(HomeActivity.this, HomeActivity.this.getResources().getString(R.string.please_try_again));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServerError(String s) {
                Utility.alerDialog(HomeActivity.this, HomeActivity.this.getResources().getString(R.string.please_try_again));


            }

        });

        Map<String, String> param = new HashMap<>();
        param.put("city_id",mCityList.get(selectCityIndex).city_id+"");
        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.AREA_LIST, this, param, true);


    }


    private void callGetrCityApi() {

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

                        Gson gson = new Gson();
                        mCityList = (ArrayList<City>) gson.fromJson(jsonObject.getJSONArray("data").toString(), new TypeToken<ArrayList<City>>() {
                        }.getType());

                        showCityList();

                    } else {
                        Utility.alerDialog(HomeActivity.this, HomeActivity.this.getResources().getString(R.string.please_try_again));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServerError(String s) {
                Utility.alerDialog(HomeActivity.this, HomeActivity.this.getResources().getString(R.string.please_try_again));


            }

        });

        Map<String, String> param = new HashMap<>();
        param.put("state_id",mStateList.get(selectStateIndex).state_id+"");

        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.CITY_LIST, this, param, true);


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

            }
        });
        ad.show();
    }

    private void showCityList() {

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        final String list[] = new String[mCityList.size()];
        for (int i = 0; i < mCityList.size(); i++) {
            list[i] = mCityList.get(i).getCity();
        }
        ad.setSingleChoiceItems(list, selectCityIndex, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                selectCityIndex = arg1;
                mAreaList.clear();

                selectAreaIndex = -1;

                ((TextView) findViewById(R.id.tvCity)).setText(list[arg1]);

                ((TextView) findViewById(R.id.tvArea)).setText("Select Area");

                arg0.dismiss();

            }
        });
        ad.show();


    }



    private void showStateList() {
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        final String list[] = new String[mStateList.size()];
        for (int i = 0; i < mStateList.size(); i++) {
            list[i] = mStateList.get(i).getState();
        }
        ad.setSingleChoiceItems(list, selectStateIndex, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                selectStateIndex = arg1;
                mAreaList.clear();

                selectAreaIndex = -1;

                mCityList.clear();

                selectCityIndex = -1;


                ((TextView) findViewById(R.id.tvState)).setText(list[arg1]);

                ((TextView) findViewById(R.id.tvArea)).setText("Select Area");
                ((TextView) findViewById(R.id.tvCity)).setText("Select City");

                arg0.dismiss();

            }
        });
        ad.show();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class State {
        String state_id;
        String state;


        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    class City {
        String city_id;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        String city;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    class Area {
        String area_id;
        String area;


        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }


}
