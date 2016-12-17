package com.app.vegcomapre;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context=this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(context!=null) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                    finish();
                }

            }
        },300);
    }
}
