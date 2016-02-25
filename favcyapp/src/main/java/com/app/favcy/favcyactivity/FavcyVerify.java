package com.app.favcy.favcyactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.favcy.R;
import com.app.favcy.favcyutility.FavcyJsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FavcyVerify extends Activity {

    public static final String MyPREFERENCES = "favcypref";
    public String favcyapp_id, favcyapp_secret;
    JSONObject json = null;
    ProgressDialog progress;
    Handler mHandler = new Handler();
    SharedPreferences sharedpreferences;
    Button bt_verify;
    ImageView imageView;
    EditText et_vcode;
    boolean mIsReceiverRegistered = false;
    FavcySMSListener mReceiver = null;
    private String facebook_id, f_name, m_name, l_name, fb_gender, profile_image, full_name, email_id, mobile_number, fbtoken;
    private String twitter_id, twitter_name, t_full_name, t_profile_image;
    private String type, vcode;
    ProgressBar spinner;

    public static String getMetadata(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
// if we can’t find it in the manifest, just return null
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!isConnected()) {
            Toast.makeText(FavcyVerify.this, getResources().getString(R.string.network_connection_error_favcy), Toast.LENGTH_SHORT).show();
            this.finish();
        }

        setContentView(R.layout.activity_favcy_verify);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        type = bundle.getString("social_account");

        if (type.equals("Facebook")) {

            email_id = bundle.getString("email");
            mobile_number = bundle.getString("mobile_number");
            facebook_id = bundle.getString("facebook_id");
            f_name = bundle.getString("firstname");
            m_name = bundle.getString("m_name");
            l_name = bundle.getString("lastname");
            fb_gender = bundle.getString("fb_gender");
            profile_image = bundle.getString("img_url");
            full_name = bundle.getString("full_name");
            fbtoken = bundle.getString("fbtoken");


        } else {

            email_id = bundle.getString("email");
            mobile_number = bundle.getString("mobile_number");
            twitter_id = bundle.getString("facebook_id");
            twitter_name = bundle.getString("twitter_name");
            t_full_name = bundle.getString("t_full_name");
            t_profile_image = bundle.getString("img_url");
        }


        favcyapp_id = bundle.getString("app_id");
        favcyapp_secret = bundle.getString("app_secret");

        bt_verify = (Button) findViewById(R.id.vcode_button);
        et_vcode = (EditText) findViewById(R.id.vcode_favcy);
        spinner=(ProgressBar)findViewById(R.id.imageloader);

        URL url = null;

        Log.i("Favcy Log", profile_image);
        if(profile_image!=null) {
            imageView = (ImageView) findViewById(R.id.profile_pic);
            new GetImageAsyncTask(FavcyVerify.this).execute(profile_image);
        }
        bt_verify.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (!isConnected()) {
                    Toast.makeText(FavcyVerify.this, getResources().getString(R.string.network_connection_error_favcy), Toast.LENGTH_SHORT).show();
                    return;
                }

                vcode = et_vcode.getText().toString();


                if (TextUtils.isEmpty(vcode)) {
                    Toast.makeText(FavcyVerify.this, getResources().getString(R.string.verification_code_favcy), Toast.LENGTH_SHORT).show();
                    return;
                }


                new HttpAsyncTask(FavcyVerify.this).execute(new String[]{favcyapp_id, favcyapp_secret});

            }
        });

        //  new HttpAsyncTask(FavcyVerify.this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsReceiverRegistered) {
            if (mReceiver == null)
                mReceiver = new FavcySMSListener();
            registerReceiver(mReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
            mIsReceiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Log.d("Favcy App", "On pause");

        if (mIsReceiverRegistered) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
            mIsReceiverRegistered = false;
        }

        // Other onPause() code here

    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favcy_verify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public class FavcySMSListener extends BroadcastReceiver {

        private SharedPreferences preferences;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs = null;
                String msg_from;


                if (bundle != null) {
                    //---retrieve the SMS message received---
                    try {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for (int i = 0; i < msgs.length; i++) {
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            msg_from = msgs[i].getOriginatingAddress();
                            String msgBody = msgs[i].getMessageBody();
                            msgBody = msgBody.replaceAll("[^0-9]", "");

                            if (msg_from.contains("FAVCYS")) {
                                et_vcode.setText(msgBody);
                            }
                        }
                    } catch (Exception e) {
                        Log.d("Exception caught", e.getMessage());
                    }
                }
            }
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, String, JSONObject> {

        Activity mActivity;

        public HttpAsyncTask(Activity activity) {
            super();
            this.mActivity = activity;
        }


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(FavcyVerify.this);
            progress.setMessage(getResources().getString(R.string.verifying_favcy));
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.show();
        }

        /**
         * Creating User
         */
        protected JSONObject doInBackground(String... args) {


            String favcyapp_id = args[0];
            //getMetadata(FavcyVerify.this, "favcyapp_id");
            String favcyapp_secret = args[1];
            //getMetadata(FavcyVerify.this, "favcyapp_secret");

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            if (type.equals("Facebook")) {
                params.add(new BasicNameValuePair("app_id", favcyapp_id));
                params.add(new BasicNameValuePair("social_account", type));
                params.add(new BasicNameValuePair("facebook_id", facebook_id));
                params.add(new BasicNameValuePair("firstname", f_name));
                params.add(new BasicNameValuePair("m_name", m_name));
                params.add(new BasicNameValuePair("lastname", l_name));
                params.add(new BasicNameValuePair("gender", fb_gender));
                params.add(new BasicNameValuePair("img_url", profile_image));
                params.add(new BasicNameValuePair("fullname", full_name));

            } else {

                params.add(new BasicNameValuePair("app_id", favcyapp_id));
                params.add(new BasicNameValuePair("social_account", type));
                params.add(new BasicNameValuePair("twitter_id", twitter_id));
                params.add(new BasicNameValuePair("firstname", twitter_name));
                params.add(new BasicNameValuePair("fullname", t_full_name));
                params.add(new BasicNameValuePair("img_url", t_profile_image));

            }


            // getting JSON Object
            // Note that create user url accepts POST method
            params.add(new BasicNameValuePair("email", email_id));
            params.add(new BasicNameValuePair("mobile", mobile_number));
            params.add(new BasicNameValuePair("verificationcode", vcode));
            json = FavcyJsonParser.makeHttpRequest("http://www.favcy.com/favcy-rest/v1/groupsignup", "POST", params, favcyapp_id, favcyapp_secret);

            String message = "";
            boolean error_code = true;
            ArrayList<String> jsonresponse = new ArrayList<String>();
            // check for success tag
            try {


                error_code = json.getBoolean("error");
                message = json.getString("message");
                // Log.d("Favcy Apps", json.toString());
                if (error_code == true) {

                } else {

                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    String name = json.getString("name");
                    String email = json.getString("email");
                    String profile_pic = json.getString("profile_pic");
                    String favcy_member = json.getString("favcy_member");
                    Integer currency_value = json.getInt("currency_value");
                    editor.putString("favcy_member", favcy_member);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("profile_pic", profile_image);
                    editor.putInt("currency_value", currency_value);
                    editor.putString("fbtoken", fbtoken);
                    editor.putString("fb_id", facebook_id);
                    editor.putString("fb_gender", fb_gender);
                    editor.commit();
                    //Log.d("Favcy Apps", json.toString()+name);

                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }


            return json;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(JSONObject jsonresponse) {
            // dismiss the dialog once done
            // Log.d("Favcy App", jsonresponse.toString());
            progress.dismiss();
            try {
                Boolean postres = jsonresponse.getBoolean("error");
                String postmessage = jsonresponse.getString("message");

                if (postres.equals(false)) {

                     Log.d("Favcy Apps",""+jsonresponse);

                    String name = jsonresponse.getString("name");
                    String email = jsonresponse.getString("email");
                    String profile_pic = jsonresponse.getString("profile_pic");
                    String favcy_member = jsonresponse.getString("favcy_member");
                    Integer currency_value = jsonresponse.getInt("currency_value");
                    Intent i = new Intent();
                    i.setAction("com.app.favcy.FAVCYUSERDATA");
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    i.putExtra("favcy_member", favcy_member);
                    i.putExtra("profile_pic", profile_image);
                    i.putExtra("Login", "success");
                    i.putExtra("currency_value", currency_value);
                    sendBroadcast(i);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("name", name);
                    returnIntent.putExtra("email", email);
                    returnIntent.putExtra("favcy_member", favcy_member);
                    returnIntent.putExtra("profile_pic", profile_pic);
                    returnIntent.putExtra("Login", "success");
                    returnIntent.putExtra("currency_value", currency_value);
                    returnIntent.putExtra("fb_id", facebook_id);
                    returnIntent.putExtra("fbtoken", fbtoken);
                    returnIntent.putExtra("fb_gender", fb_gender);
                    setResult(4, returnIntent);
                    mActivity.finish();
                } else {


                    Toast.makeText(getApplicationContext(), postmessage, Toast.LENGTH_LONG)
                            .show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    class GetImageAsyncTask extends AsyncTask<String, String, Bitmap> {

        Activity mActivity;

        public GetImageAsyncTask(Activity activity) {
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
        protected Bitmap doInBackground(String... args) {
            String loadurl = args[0];
            URL url = null;
            try {
                url = new URL(loadurl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                if (url != null) {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bmp;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(Bitmap loadimage) {
            // dismiss the dialog once done
            imageView.setImageBitmap(loadimage);
            spinner.setVisibility(View.GONE);

        }

    }


}



