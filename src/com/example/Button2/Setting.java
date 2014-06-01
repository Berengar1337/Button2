package com.example.Button2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Admin on 15.05.2014.
 */
public class Setting extends Activity {
    Button btnSave;
    Button btnTest;
    Button btnBack;
    EditText numberTxt1;
    EditText numberTxt2;
    EditText numberTxt3;
    EditText messageTxt;
    TextView txtTest1;
    TextView txtTest2;
    TextView txtTest3;
    TextView tvEnabledGPS;
    TextView tvLocationGPS;
    TextView tvStatusGPS;
    private LocationManager locationManager;
    SharedPreferences mSettings;
    public static  String APP_PREFERENCES = "mysettings";
    public static  String APP_PREFERENCES_NAME1 = "numberTxt1";
    public static  String APP_PREFERENCES_NAME2 = "numberTxt2";
    public static  String APP_PREFERENCES_NAME3 = "numberTxt3";
    public static  String APP_PREFERENCES_NAME4 = "messageTxt";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        txtTest1 = (TextView) findViewById(R.id.textView);
        txtTest2 = (TextView) findViewById(R.id.textView2);
        txtTest3 = (TextView) findViewById(R.id.textView3);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnTest = (Button) findViewById(R.id.btnTest);
        numberTxt1 = (EditText) findViewById(R.id.editText);
        numberTxt2 = (EditText) findViewById(R.id.editText2);
        numberTxt3 = (EditText) findViewById(R.id.editText3);
        messageTxt = (EditText) findViewById(R.id.editText4);
        tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_NAME1, numberTxt1.getText().toString());
                editor.putString(APP_PREFERENCES_NAME2, numberTxt2.getText().toString());
                editor.putString(APP_PREFERENCES_NAME3, numberTxt3.getText().toString());
                editor.putString(APP_PREFERENCES_NAME4, messageTxt.getText().toString());
                editor.apply();
                onStart();
                Intent intent = new Intent(Setting.this, Main.class);
                startActivity(intent);
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_NAME1, numberTxt1.getText().toString());
                editor.putString(APP_PREFERENCES_NAME2, numberTxt2.getText().toString());
                editor.putString(APP_PREFERENCES_NAME3, numberTxt3.getText().toString());
                editor.putString(APP_PREFERENCES_NAME4, messageTxt.getText().toString());
                editor.apply();
                onStart();
                return;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Main.class);
                startActivity(intent);
            }
        });
    }
    public void onStart(){
        super.onStart();
        if(mSettings.contains(APP_PREFERENCES_NAME1)) {
            txtTest1.setText(mSettings.getString(APP_PREFERENCES_NAME1, ""));
            numberTxt1.setText(mSettings.getString(APP_PREFERENCES_NAME1, ""));
        }
        if(mSettings.contains(APP_PREFERENCES_NAME2)) {
            txtTest2.setText(mSettings.getString(APP_PREFERENCES_NAME2, ""));
            numberTxt2.setText(mSettings.getString(APP_PREFERENCES_NAME2, ""));
        }
        if(mSettings.contains(APP_PREFERENCES_NAME3)) {
            txtTest3.setText(mSettings.getString(APP_PREFERENCES_NAME3, ""));
            numberTxt3.setText(mSettings.getString(APP_PREFERENCES_NAME3, ""));
        }
        if(mSettings.contains(APP_PREFERENCES_NAME4)) {
            messageTxt.setText(mSettings.getString(APP_PREFERENCES_NAME4, ""));
        }
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Status: " + String.valueOf(status));
            }
        }

        @Override
        public void onProviderDisabled(String provider) {  checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }
    };

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Координаты: Широта = %1$.4f, Долгота = %2$.4f, Время = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    private void showLocation(Location location) {

        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, locationListener);
        checkEnabled();

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private void checkEnabled() {
        tvEnabledGPS.setText("Включено: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };
}
