package com.app.taza_price;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Created by Rakshith on 12/23/2016.
 */

public class BaseActivity extends AppCompatActivity {

    private AdView adView;
    AdRequest adRequest;
    LinearLayout layout;



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        layout = (LinearLayout) findViewById(R.id.lyAddView);
      //  layout.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        addAd();
    }

    private void addAd() {

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(this.getResources().getString(R.string.addmob_key));
        layout.addView(adView);


        adView.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();

            }

            @Override
            public void onAdOpened() {
                Log.d("tag", "add clicked");

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                layout.setVisibility(View.VISIBLE);

            }
        });


        adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);



    }
}
