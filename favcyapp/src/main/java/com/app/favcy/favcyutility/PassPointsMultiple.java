package com.app.favcy.favcyutility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Favcy on 11/13/2015.
 */
public class PassPointsMultiple extends AsyncTask<String, String, JSONObject> {
    public static final String MyPREFERENCES = "gamepref";
    SharedPreferences sharedpreferences;
    private Activity mActivity;
    private Context mContext;
    private HttpCallListener asyncall;
    private ArrayList<String> args;
    public void setListener(HttpCallListener asyncall, ArrayList<String> args){
        this.asyncall=asyncall;
        this.args = args;

    }

    public PassPointsMultiple(Activity activity) {
        super();
        this.mActivity = activity;
    }
    public PassPointsMultiple(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    protected JSONObject doInBackground(String... argss) {
        Log.i("do in background", "passpointsmultiple");
        JSONObject json = null;
        String favcyapp_id=argss[0];
        String favcyapp_secret=argss[1];
        String favcy_member=argss[2];
        String actionCode=argss[3];
        Log.i("Custom Action multi", favcyapp_id + favcyapp_secret + favcy_member + actionCode);

        // sharedpreferences = mActivity.getSharedPreferences(MyPREFERENCES, mActivity.MODE_PRIVATE);
        // favcy_member = sharedpreferences.getString("favcy_member", null);
        if(favcy_member!=null  && favcy_member!="") {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("action_code", actionCode));
            params.add(new BasicNameValuePair("favcy_member", favcy_member));
            params.add(new BasicNameValuePair("app_id", favcyapp_id));

            for(int i=0; i<this.args.size(); i++) {

                params.add(new BasicNameValuePair("request_url[]",  this.args.get(i)));
                Log.i("Custom Action multi", this.args.get(i));
            }


            json = FavcyJsonParser.makeHttpRequest("http://www.favcy.com/favcy-rest/v1/customactionmulti", "POST", params, favcyapp_id, favcyapp_secret);

            Log.i("Custom Action multi json ", ""+json);

        }else {

            json = null;
        }
        return json;
    }

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    /**
     * After completing background task Dismiss the progress dialog
     **/
    protected void onPostExecute(JSONObject jsonresponse) {
        Log.i("Json Response", "f" + jsonresponse);
        if(jsonresponse!=null) {
            try {
                Boolean postres = jsonresponse.getBoolean("error");
                if (postres.equals(false) && jsonresponse.getString("TID")!=null) {
                    Integer coins = jsonresponse.getInt("currency_value");
                    if(this.asyncall!=null) {

                        this.asyncall.asyncComplete(jsonresponse);
                    }
                } else {
                 //   Toast.makeText(mActivity.getApplicationContext(), "Oopps! error please try again after sometime", Toast.LENGTH_LONG)
                    //        .show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }else {

            // Toast.makeText(mActivity.getApplicationContext(), "error", Toast.LENGTH_LONG)
              //   .show();
        }

    }

}
