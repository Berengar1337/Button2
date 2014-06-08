package com.example.Button2;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Admin on 08.06.2014.
 */
public class Alert {

    public boolean sendSMS(String phoneNumber, String message)
    {
        if (phoneNumber.length()>0)
        {
            //SmsManager sms = SmsManager.getDefault();
            //sms.sendTextMessage(phoneNumber, null, message, null, null);
            return true;
        }
        else
        {
            return false;
        }
    }
}
