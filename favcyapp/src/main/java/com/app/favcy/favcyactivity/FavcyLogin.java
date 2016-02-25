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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.favcy.R;
import com.app.favcy.favcyutility.FavcyJsonParser;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class FavcyLogin extends Activity  {


    private CallbackManager callbackManager;
    private Button facebook_button, twitter_button;
    public String favcyapp_id, favcyapp_secret, groupname;
    private ProgressDialog progress;
    private String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name, email_id, fbtoken;
    private String twitter_id, twitter_name, t_full_name, t_profile_image;
    private ProgressBar spinner;
    ImageView orgimage;
    JSONObject json=null;
    Handler mHandler=new Handler();
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES="favcypref";


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

        if(!isConnected()){
            Toast.makeText(FavcyLogin.this, getResources().getString(R.string.network_connection_error_favcy), Toast.LENGTH_SHORT).show();
            this.finish();
        }else {

            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {

                } else {
                    favcyapp_id = extras.getString("app_id");
                    favcyapp_secret = extras.getString("app_secret");
                }
            } else {
                favcyapp_id = (String) savedInstanceState.getSerializable("app_id");
                favcyapp_secret = (String) savedInstanceState.getSerializable("app_secret");
            }


            sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String member_id = sharedpreferences.getString("favcy_member", null);
            String email = sharedpreferences.getString("email", null);
            String name = sharedpreferences.getString("name", null);
            groupname = sharedpreferences.getString("groupname", null);
            String brandimg = sharedpreferences.getString("brandimg", null);
            String orgprofile = sharedpreferences.getString("orgprofile", null);
            String currencyname = sharedpreferences.getString("currencyname", null);
            String profile_pic = sharedpreferences.getString("profile_pic", null);
            fbtoken = sharedpreferences.getString("fbtoken", "none");
            String fb_id = sharedpreferences.getString("fb_id", "none");
            Integer currency_value = sharedpreferences.getInt("currency_value", 0);
            String fb_gender = sharedpreferences.getString("fb_gender", null);

            if (member_id != "" && member_id != null) {
                Log.d("Favcy Login", member_id + name + fb_id);
                /* Intent i = new Intent();
                 i.setAction("com.app.favcy.FAVCYUSERDATA");
                 i.putExtra("name", name);
                 i.putExtra("email", email);
                 i.putExtra("favcy_member", member_id);
                 i.putExtra("profile_pic", profile_pic);
                 i.putExtra("Login", "success");
                 i.putExtra("currency_value", currency_value);
                 sendBroadcast(i);*/
                Intent returnIntent = new Intent();
                returnIntent.putExtra("name", name);
                returnIntent.putExtra("email", email);
                returnIntent.putExtra("favcy_member", member_id);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("favcy_member", member_id);
                editor.commit();
                returnIntent.putExtra("profile_pic", profile_pic);
                returnIntent.putExtra("Login", "success");
                returnIntent.putExtra("currency_value", currency_value);
                returnIntent.putExtra("fb_id", fb_id);
                returnIntent.putExtra("fbtoken", fbtoken);
                returnIntent.putExtra("fb_gender", fb_gender);
                setResult(6, returnIntent);
                this.finish();

            }


            setContentView(R.layout.activity_favcy_login);

            facebook_button = (Button) findViewById(R.id.button2);
            twitter_button = (Button) findViewById(R.id.button);
            orgimage = (ImageView) findViewById(R.id.orgimage);
            progress = new ProgressDialog(FavcyLogin.this);
            progress.setMessage(getResources().getString(R.string.please_wait_favcy));
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            spinner = (ProgressBar) findViewById(R.id.imageloader);
            facebook_id = f_name = m_name = l_name = gender = profile_image = full_name = email_id = "";
            twitter_id = twitter_name = t_full_name = t_profile_image = "";


            if (groupname != null && brandimg != null && groupname != "" && brandimg != "") {

                new GetImageAsyncTask(FavcyLogin.this).execute(brandimg);
            } else {
                new GroupdataAsyncTask(FavcyLogin.this).execute(new String[]{favcyapp_id, favcyapp_secret});
            }


            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();

            //register callback object for facebook result
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                private ProfileTracker mProfileTracker;

                @Override
                public void onSuccess(LoginResult loginResult) {
                    progress.show();
                    Profile profile = null;
                    final Intent i = new Intent(FavcyLogin.this, FavcyRegistration.class);
                    fbtoken = loginResult.getAccessToken().getToken();
                    if (Profile.getCurrentProfile() == null) {
                        mProfileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile profile, Profile profile2) {

                                facebook_id = profile2.getId();
                                f_name = profile2.getFirstName();
                                m_name = profile2.getMiddleName();
                                l_name = profile2.getLastName();
                                full_name = profile2.getName();
                                profile_image = profile2.getProfilePictureUri(400, 400).toString();
                                mProfileTracker.stopTracking();

                                i.putExtra("type", "Facebook");
                                i.putExtra("facebook_id", facebook_id);
                                i.putExtra("fbtoken", fbtoken);
                                i.putExtra("f_name", f_name);
                                i.putExtra("m_name", m_name);
                                i.putExtra("l_name", l_name);
                                i.putExtra("full_name", full_name);
                                i.putExtra("profile_image", profile_image);
                            }

                        };
                        mProfileTracker.startTracking();


                    } else {
                        profile = Profile.getCurrentProfile();

                        if (profile != null) {

                            Log.d("Favcy App", "" + profile.getId() + " " + profile.getFirstName());
                            facebook_id = profile.getId();
                            f_name = profile.getFirstName();
                            m_name = profile.getMiddleName();
                            l_name = profile.getLastName();
                            full_name = profile.getName();
                            profile_image = profile.getProfilePictureUri(400, 400).toString();

                        }

                        i.putExtra("type", "Facebook");
                        i.putExtra("facebook_id", facebook_id);
                        i.putExtra("f_name", f_name);
                        i.putExtra("m_name", m_name);
                        i.putExtra("l_name", l_name);
                        i.putExtra("full_name", full_name);
                        i.putExtra("profile_image", profile_image);
                        i.putExtra("fbtoken", fbtoken);

                    }


                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {

                                    Log.i("Favcy App", "" + response + "" + object.toString());
                                    try {
                                        email_id = object.getString("email");
                                        //gender = object.getString("gender");

                                        String email = object.optString("email");

                                        i.putExtra("email_id", email_id);
                                        i.putExtra("gender", gender);

                                        Log.i("Favcy App", "gender" + gender + "" + object.toString());

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    progress.dismiss();
                                    i.putExtra("app_id", favcyapp_id);
                                    i.putExtra("app_secret", favcyapp_secret);
                                    i.putExtra("group_name", groupname);
                                    startActivityForResult(i, 3);
                                    // finish();
                                }


                            });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,link,email,picture");
                    request.setParameters(parameters);
                    request.executeAsync();


                }

                @Override
                public void onCancel() {
                    Toast.makeText(FavcyLogin.this, getResources().getString(R.string.login_canceled_favcy), Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(FavcyLogin.this, getResources().getString(R.string.login_failed_favcy), Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            });

            facebook_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isConnected()) {
                        Toast.makeText(FavcyLogin.this, getResources().getString(R.string.network_connection_error_favcy), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    LoginManager.getInstance().logInWithReadPermissions(FavcyLogin.this, Arrays.asList("public_profile", "email"));
                }
            });
        }
    }

    //for facebook & twitter callback result.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("FavcyLoginActivity", requestCode + "resultcode" + resultCode);

        if(requestCode == 3) {


            String name = data.getStringExtra("name");
            String email = data.getStringExtra("email");
            String member_id = data.getStringExtra("favcy_member");
            String profile_pic = data.getStringExtra("profile_pic");
            Integer currency_value = data.getIntExtra("currency_value", 0);
            String fb_id = data.getStringExtra("fb_id");
            String fbtoken = data.getStringExtra("fbtoken");
            String fb_gender = data.getStringExtra("fb_gender");
            Log.d("FavcyLoginActivity", "name"+currency_value+"email"+email + "memberid"+member_id+fb_id+fb_gender);

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
            setResult(RESULT_OK, returnIntent);
            this.finish();

        }else {
        callbackManager.onActivityResult(requestCode, resultCode, data);

             }


    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }




    protected class GroupdataAsyncTask extends AsyncTask<String, String, String> {

        Activity mActivity;

        public GroupdataAsyncTask(Activity activity) {
            super();
            this.mActivity = activity;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        protected String doInBackground(String... args) {


            String favcyapp_id = args[0];
            //getMetadata(FavcyLogin.this, "favcyapp_id");
            String favcyapp_secret = args[1];
            //getMetadata(FavcyLogin.this, "favcyapp_secret");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON Object
            // Note that create user url accepts POST method
            params.add(new BasicNameValuePair("app_id", favcyapp_id));
            json = FavcyJsonParser.makeHttpRequest("http://www.favcy.com/favcy-rest/v1/groupdata", "POST", params, favcyapp_id, favcyapp_secret);
            String message = "";
            String brandimg = "";

            try {
                String error_code = json.getString("error");
                if (error_code.equals(true)) {

                    message = json.getString("message");


                } else {


                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    groupname = json.getString("group_name");
                    brandimg = json.getString("brand_img");
                    String currencyname = json.getString("currency_name");
                    editor.putString("groupname", groupname);
                    editor.putString("brandimg", brandimg);
                    editor.putString("currencyname", currencyname);
                    editor.commit();

                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            return brandimg;
        }


        protected void onPostExecute(String brandimg) {
            // dismiss the dialog once done

            if (brandimg.length() > 0 && brandimg != null) {
                new GetImageAsyncTask(FavcyLogin.this).execute(brandimg);
            }
        }

    }




    class GetImageAsyncTask extends AsyncTask<String, String, Bitmap> {

        Activity mActivity;

        public GetImageAsyncTask(Activity activity) {
            super();
            this.mActivity = activity;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



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


                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }


            return bmp;
        }


        protected void onPostExecute(Bitmap loadimage) {
            // dismiss the dialog once done
            orgimage.setImageBitmap(loadimage);
            spinner.setVisibility(View.GONE);

        }

    }

}