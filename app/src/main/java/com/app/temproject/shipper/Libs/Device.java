package com.app.temproject.shipper.Libs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;

/**
 * Created by huyhoang on 23/10/2016.
 */
public class Device {
    public static void openGPSSetting(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setIcon(R.drawable.ic_menu_manage)
                .setTitle(R.string.gps)
                .setMessage(R.string.turn_on_gps)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }

                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }

    public static boolean isEnableGPS(Activity activity){
        String provider = Settings.Secure.getString(
                activity.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains(Constant.GPS)) {
            return true;
        }
        return false;
    }
}
