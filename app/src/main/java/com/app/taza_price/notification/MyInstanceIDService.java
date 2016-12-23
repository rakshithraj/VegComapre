package com.app.taza_price.notification;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Rakshith on 8/12/2016.
 */
public class MyInstanceIDService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        refreshAllTokens();
    }

    private void refreshAllTokens() {
        GcmUtility.storeRegistrationId(this,"");
    }
};