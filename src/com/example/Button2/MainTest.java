package com.example.Button2;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Admin on 08.06.2014.
 */
public class MainTest {

    Context ctx;

    public MainTest() {
        ctx = new Application().getApplicationContext();
    }

    @Test
    public void testAlwaysPassed(){
        assertTrue(true);
    }

    @Test
     public void helloWorldCheck() {
        Class<Main> mainClass;
        Main main = null;
        try {
            mainClass = (Class<Main>)ctx.getClassLoader().loadClass("Main");
            main = mainClass.newInstance();
        }
        catch (Exception e) {

        }

        SharedPreferences preferences = ctx.getSharedPreferences(main.APP_PREFERENCES, ctx.MODE_PRIVATE);

        Log.d("111", preferences.getString(main.APP_PREFERENCES_NAME4, "") + " " + main.sendSMS2());

        assertEquals(preferences.getString(main.APP_PREFERENCES_NAME4, ""), main.sendSMS2());

    }



}
