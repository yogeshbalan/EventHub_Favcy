package com.app.favcy.favcyactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
public class FavcyRegistration extends Activity {

    public static final String MyPREFERENCES = "favcypref";
    public String favcyapp_id, favcyapp_secret;
    Button bt_verify;
    EditText et_mobile_number, et_email_id;
    TextView vemail, vmobile, verifytext;
    JSONObject json = null;
    ProgressDialog progress;
    ImageView  imageView;
    Handler mHandler = new Handler();
    SharedPreferences sharedpreferences;
    private String facebook_id, f_name, m_name, l_name, fb_gender, profile_image, full_name, email_id, mobile_number, fbtoken;
    private String twitter_id, twitter_name, t_full_name, t_profile_image;
    private String type, group_name;
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
            Toast.makeText(FavcyRegistration.this, getResources().getString(R.string.network_connection_error_favcy), Toast.LENGTH_SHORT).show();
            this.finish();
        }
        setContentView(R.layout.activity_favcy_registration);

        facebook_id = f_name = m_name = l_name  = profile_image = full_name = email_id = fb_gender = "";
        twitter_id = twitter_name = t_full_name = t_profile_image = "";

        Intent i = getIntent();
        type = i.getStringExtra("type");
        if (type.equals("Facebook")) {

            facebook_id = i.getStringExtra("facebook_id");
            f_name = i.getStringExtra("f_name");
            m_name = i.getStringExtra("m_name");
            l_name = i.getStringExtra("l_name");
            profile_image = i.getStringExtra("profile_image");
            full_name = i.getStringExtra("full_name");
            email_id = i.getStringExtra("email_id");
            fbtoken = i.getStringExtra("fbtoken");
            fb_gender = i.getStringExtra("gender");
           // Toast.makeText(FavcyRegistration.this, email_id, Toast.LENGTH_LONG).show();


        } else {
            twitter_id = i.getStringExtra("twitter_id");
            twitter_name = i.getStringExtra("twitter_name");
            t_full_name = i.getStringExtra("t_full_name");
            profile_image = i.getStringExtra("t_profile_image");
        }
        Log.i("Favcy Apps", f_name+l_name+profile_image+facebook_id);
        favcyapp_id = i.getStringExtra("app_id");
        favcyapp_secret = i.getStringExtra("app_secret");
        group_name = i.getStringExtra("group_name");
        bt_verify = (Button) findViewById(R.id.verify);
        et_mobile_number = (EditText) findViewById(R.id.favcymobile);
        et_email_id = (EditText) findViewById(R.id.favcyemail);
        vemail = (TextView) findViewById(R.id.everify);
        vmobile = (TextView) findViewById(R.id.mverify);
        verifytext = (TextView)findViewById(R.id.verifytext);
        verifytext.setText("Please verify Email & Mobile to to authorize your "+group_name+" membership");
        et_email_id.setText(email_id);
        spinner=(ProgressBar)findViewById(R.id.imageloader);

        new Checkuser(FavcyRegistration.this).execute(new String[]{favcyapp_id, favcyapp_secret});
        email_id = et_email_id.getText().toString();

        if (!TextUtils.isEmpty(email_id)) {
            vemail.setText("Verified");
            et_email_id.setKeyListener(null);

        }
        vmobile.setVisibility(View.INVISIBLE);
        //if (mobile_number.length()==10) {
        //  vmobile.setText("Verified");
        //}

        Log.i("Favcy Log", profile_image);
        if(profile_image!=null) {
            imageView = (ImageView) findViewById(R.id.profile_pic);
            new GetImageAsyncTask(FavcyRegistration.this).execute(profile_image);
        }
        bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isConnected()) {
                    Toast.makeText(FavcyRegistration.this, getResources().getString(R.string.network_connection_error_favcy), Toast.LENGTH_SHORT).show();

                }

                email_id = et_email_id.getText().toString();
                mobile_number = et_mobile_number.getText().toString();

                if (TextUtils.isEmpty(email_id) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
                    Toast.makeText(FavcyRegistration.this, getResources().getString(R.string.correct_email_id_notification_favcy), Toast.LENGTH_SHORT).show();
                    et_email_id.setError(getResources().getString(R.string.correct_email_id_notification_favcy));
                    return;
                }
                if (mobile_number.length() == 10) {

                } else {
                    Toast.makeText(FavcyRegistration.this, getResources().getString(R.string.correct_mobile_number_notification_favcy), Toast.LENGTH_SHORT).show();
                    return;
                }

                new SendSMSHttpAsyncTask(FavcyRegistration.this).execute(new String[]{favcyapp_id, favcyapp_secret});

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 4) {
            String name = data.getStringExtra("name");
            String email = data.getStringExtra("email");
            String member_id = data.getStringExtra("favcy_member");
            String profile_pic = data.getStringExtra("profile_pic");
            Integer currency_value = data.getIntExtra("currency_value", 0);
            String fb_id = data.getStringExtra("fb_id");
            String fbtoken = data.getStringExtra("fbtoken");
            fb_gender = data.getStringExtra("fb_gender");


            Log.d("FavcyRegistration", "name"+currency_value+"email"+email + "memberid"+member_id+fb_id+fb_gender);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("email", email);
            returnIntent.putExtra("favcy_member", member_id);
            returnIntent.putExtra("profile_pic", profile_pic);
            returnIntent.putExtra("Login", "success");
            returnIntent.putExtra("currency_value", currency_value);
            returnIntent.putExtra("fb_id", fb_id);
            returnIntent.putExtra("fbtoken", fbtoken);
            returnIntent.putExtra("fb_gender", fb_gender);
            setResult(3, returnIntent);
            this.finish();

        }

        // check if the request code is same as what is passed  here it is 2
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private class SendSMSHttpAsyncTask extends AsyncTask<String, String, JSONObject> {

        Activity mActivity;

        public SendSMSHttpAsyncTask(Activity activity) {
            super();
            this.mActivity = activity;
        }


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(FavcyRegistration.this);
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
            //getMetadata(FavcyRegistration.this, "favcyapp_id");
            String favcyapp_secret = args[1];
            //getMetadata(FavcyRegistration.this, "favcyapp_secret");

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("mobile_number", mobile_number));

            json = FavcyJsonParser.makeHttpRequest("http://www.favcy.com/favcy-rest/v1/verifymobile", "POST", params, favcyapp_id, favcyapp_secret);

            //   String error_code="";
            // check log cat from response
            // Log.d("Favcy App Reg", json.toString());

            // check for success tag
           /* try {
               // error_code = json.getString("error");
               /// String vcode = json.getString("vcode");


            } catch(JSONException e){
                 Log.e("log_tag", "Error parsing data "+e.toString());
            }
*/
            return json;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(JSONObject json) {
            // dismiss the dialog once done

            Log.d("Favcy App", "Progress Dissmiss"+json);
            Intent intent = new Intent(FavcyRegistration.this, FavcyVerify.class);
            Bundle extras = new Bundle();
            boolean error_code = true;


            try {
                error_code = json.getBoolean("error");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            if (type.equals("Facebook")) {

                extras.putString("social_account", type);
                extras.putString("email", email_id);
                extras.putString("mobile_number", mobile_number);
                extras.putString("facebook_id", facebook_id);
                extras.putString("firstname", f_name);
                extras.putString("m_name", m_name);
                extras.putString("lastname", l_name);
                extras.putString("fb_gender", fb_gender);
                extras.putString("img_url", profile_image);
                extras.putString("fullname", full_name);
                extras.putString("fbtoken", fbtoken);

            } else {

                extras.putString("email", email_id);
                extras.putString("mobile_number", email_id);
                extras.putString("social_account", type);
                extras.putString("fullname", t_full_name);
                extras.putString("twitter_id", twitter_id);
                extras.putString("twitter_name", twitter_name);
                extras.putString("t_full_name", t_full_name);


            }
            extras.putString("app_id", favcyapp_id);
            extras.putString("app_secret", favcyapp_secret);
            intent.putExtras(extras);
            if (error_code == false) {
                startActivityForResult(intent, 4);
                progress.dismiss();
               // mActivity.finish();
            } else {
               // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG)
               //         .show();

            }


        }


    }



    private class Checkuser extends AsyncTask<String, String, JSONObject> {

        Activity mActivity;

        public Checkuser(Activity activity) {
            super();
            this.mActivity = activity;
        }


        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(FavcyRegistration.this);
            progress.setMessage("Loading..");
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.show();
        }

        /**
         * Creating User
         */
        protected JSONObject doInBackground(String... args) {

            String favcyapp_id = args[0];
            String favcyapp_secret = args[1];
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email_id));
            params.add(new BasicNameValuePair("app_id", favcyapp_id));
            json = FavcyJsonParser.makeHttpRequest("http://www.favcy.com/favcy-rest/v1/checkuser", "POST", params, favcyapp_id, favcyapp_secret);
            return json;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(JSONObject json) {
            // dismiss the dialog once done
          //Log.i("favcy reg", ""+json);
            progress.dismiss();
          if(json!=null) {

              try {
                  Boolean status = json.getBoolean("error");
                  if(!status) {


                      et_mobile_number.setText(json.getString("mobile"));
                  }
              } catch (JSONException e) {
                  e.printStackTrace();
              }



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
