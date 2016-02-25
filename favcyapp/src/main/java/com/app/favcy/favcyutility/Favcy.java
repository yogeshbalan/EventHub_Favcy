package com.app.favcy.favcyutility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by IIFM on 12/3/2015.
 */
public class Favcy extends Application {
    public static final String MyPREFERENCES = "favcypref";
    SharedPreferences sharedpreferences;
    Integer fcoins;
    public void  FavcyLogout(Context context){

        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putString("favcy_member", "");
        editor.putString("name", "");
        editor.putString("email", "");
        editor.putString("profile_pic", "");
        editor.putInt("currency_value", 0);
        editor.putString("fbtoken", "");
        editor.putString("fb_id", "");
        editor.putString("groupname", "");
        editor.putString("brandimg", "");
        editor.putString("orgprofile", "");
        editor.putString("currencyname", "");
        editor.commit();

    }


    public Boolean FavcyCustomAction(Context context, Activity activity, String actionCode, String favcyapp_id, String favcyapp_secret, String request_url) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String favcy_member = sharedpreferences.getString("favcy_member", null);
        Log.i("Favcy Custom Action", actionCode + request_url + favcy_member);
        if(favcy_member !=null && favcy_member!="") {
            PassPoints passPoints = new PassPoints(activity);
            passPoints.execute(new String[]{favcyapp_id, favcyapp_secret, favcy_member, request_url, actionCode});

            return true;
        }else {
            return false;
        }
    }

    public HashMap<String, String> getFavcyUser() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String member_id = sharedpreferences.getString("favcy_member", null);
        String email = sharedpreferences.getString("email",null);
        String name = sharedpreferences.getString("name", null);
        String currencyname = sharedpreferences.getString("currencyname", null);
        String profile_pic = sharedpreferences.getString("profile_pic", null);
        String fb_id = sharedpreferences.getString("fb_id", "none");
        Integer currency_value = sharedpreferences.getInt("currency_value", 0);
        String fb_gender = sharedpreferences.getString("fb_gender", null);


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("favcy_member", member_id);
        map.put("email", email);
        map.put("profile_pic", profile_pic);
        map.put("fb_id", fb_id);
        map.put("fb_gender", fb_gender);
        map.put("currency_value",currency_value.toString());
        return map;
    }


    public void setCoins(Integer fcoins) {
         this.fcoins = fcoins;
     }

    public Integer getCoins(Integer fcoins) {
       return fcoins;
    }
}
