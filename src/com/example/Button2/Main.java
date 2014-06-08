package com.example.Button2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class Main extends Activity {

    Button btnSendSMS;
    Button btnSetting;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    String phoneNo;
    String messageTxt;
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
    public static Integer k;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        btnSetting = (Button) findViewById(R.id.btnSetting);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnSendSMS.setOnClickListener(new View.OnClickListener()
        {
        public void onClick(View v)
            {
                messageTxt=mSettings.getString(APP_PREFERENCES_NAME4, "");
                sendSMS2();
                k=1;
                           }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Setting.class);
                startActivity(intent);
            }
        });

    }

    public void sendSMS(String phoneNumber, String message)
    {
        if (phoneNo.length()>0)
        {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, Main.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
        }
        else
        {
        Toast.makeText(getBaseContext(),
                "Пожалуйста добавьте номера для отправки в меню настроек",
                Toast.LENGTH_SHORT).show();
        }
    }
    public void onStart(){
        super.onStart();
        if(mSettings.contains(APP_PREFERENCES_NAME1)) {
            textView1.setText(mSettings.getString(APP_PREFERENCES_NAME1, ""));
        }
        if(mSettings.contains(APP_PREFERENCES_NAME2)) {
            textView2.setText(mSettings.getString(APP_PREFERENCES_NAME2, ""));
        }
        if(mSettings.contains(APP_PREFERENCES_NAME3)) {
            textView3.setText(mSettings.getString(APP_PREFERENCES_NAME3, ""));
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
              //  "Координаты: Широта = %1$.4f, Долгота = %2$.4f, Время = %3$tF %3$tT",
                "Координаты Широта %1$.4f, Долгота %2$.4f",
                location.getLatitude(), location.getLongitude());
    }

    private void showLocation(Location location) {
        Log.d("sample2", k.toString());

        if (location == null)
            return;

        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));
        }
        if (k==1){
            k=0;
            messageTxt=tvLocationGPS.getText().toString().replace(",",".");;
            sendSMS2();
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

    public String sendSMS2() {
        Log.d("sample", messageTxt);
        phoneNo = mSettings.getString(APP_PREFERENCES_NAME1, "");
        sendSMS(phoneNo, messageTxt);
        phoneNo = mSettings.getString(APP_PREFERENCES_NAME2, "");
        sendSMS(phoneNo, messageTxt);
        phoneNo = mSettings.getString(APP_PREFERENCES_NAME3, "");
        sendSMS(phoneNo, messageTxt);
        return messageTxt;
    }

}
