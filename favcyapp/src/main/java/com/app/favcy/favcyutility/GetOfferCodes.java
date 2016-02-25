package com.app.favcy.favcyutility;

/**
 * Created by favcy-pc on 28-12-2015.
 */
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetOfferCodes extends AsyncTask<String, String, JSONObject> {
    public static final String MyPREFERENCES = "gamepref";
    SharedPreferences sharedpreferences;
    private Activity mActivity;
    private HttpCallListener asyncall;
    public void setListener(HttpCallListener asyncall){
        this.asyncall=asyncall;
    }

    public GetOfferCodes(Activity activity) {
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

        if(favcy_member!=null  && favcy_member!="") {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("favcy_member", favcy_member));
            params.add(new BasicNameValuePair("app_id", favcyapp_id));
            json = FavcyJsonParser.makeHttpRequest("http://www.favcy.com/favcy-rest/v1/offercodes", "POST", params, favcyapp_id, favcyapp_secret);
        }else {

            json = null;
        }
        return json;
    }

    /**
     * After completing background task Dismiss the progress dialog
     **/
    protected void onPostExecute(JSONObject jsonresponse) {

        if(jsonresponse!=null) {
                    if(this.asyncall!=null) {
                        this.asyncall.asyncComplete(jsonresponse);
                    }
        }else {

            //  Toast.makeText(mActivity.getApplicationContext(), "", Toast.LENGTH_LONG)
            //     .show();
        }

    }

}
