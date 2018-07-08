package io.ginius.cp.kt.lostfound;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GeocoderHandler extends Handler {
    @Override
    public void handleMessage(Message message) {
        String locationAddress;
        switch (message.what) {
            case 1:
                Bundle bundle = message.getData();
                locationAddress = bundle.getString("address");
                break;
            default:
                locationAddress = null;
        }
        Log.e("location Address=", locationAddress);
    }
}