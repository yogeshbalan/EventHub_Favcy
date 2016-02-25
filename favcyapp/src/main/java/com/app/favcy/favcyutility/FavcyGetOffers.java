package com.app.favcy.favcyutility;

import android.app.Activity;
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
public class FavcyGetOffers extends AsyncTask<String, String, JSONObject> {
    public static final String MyPREFERENCES = "gamepref";
    SharedPreferences sharedpreferences;
    private Activity mActivity;
    private HttpCallListener asyncall;
    public void setListener(HttpCallListener asyncall){
        this.asyncall=asyncall;
    }

    public FavcyGetOffers(Activity activity) {
        super();
        this.mActivity = activity;
    }
    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    /**
     * Creating User
     */
    protected JSONObject doInBackground(String... args) {

        JSONObject json = null;
        String favcyapp_id = args[0];
        String favcyapp_secret = args[1];
        String favcy_member = args[2];
        String brand_id = args[3];
        String offer_id = args[4];
        // sharedpreferences = mActivity.getSharedPreferences(MyPREFERENCES, mActivity.MODE_PRIVATE);
        // favcy_member = sharedpreferences.getString("favcy_member", null);
        if(favcy_member!=null  && favcy_member!="") {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("action_code", actionCode));
            params.add(new BasicNameValuePair("brand_id", brand_id));
            params.add(new BasicNameValuePair("offer_id", offer_id));
            //params.add(new BasicNameValuePair("request_url", request_url));
            json = FavcyJsonParser.makeHttpRequest("https://www.favcy.com/favcy-rest/v1/shopoffer", "POST", params, favcyapp_id, favcyapp_secret);
        }else {
            json = null;
        }
        return json;
    }

    /**
     * After completing background task Dismiss the progress dialog
     **/
    protected void onPostExecute(JSONObject jsonresponse) {
        Log.i("Json Response", "" + jsonresponse);
        if(jsonresponse!=null) {
            try {
                Boolean postres = jsonresponse.getBoolean("error");

                    if(this.asyncall!=null) {

                        this.asyncall.asyncComplete(jsonresponse);
                    }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }else {

            //  Toast.makeText(mActivity.getApplicationContext(), "Already Synced", Toast.LENGTH_LONG)
            //     .show();
        }

    }

}
