package com.app.favcy.favcyutility;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Parth on 5/30/2015.
 * website: http://www..com
 */

public class FavcyJsonParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public static JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params, String favcyapp_id, String favcyapp_secret) {
        String responseStr = null;

        Log.i("FavcyJsonParser", ""+url);
        try {
            if (method == "POST") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                // Url Encoding the POST parameters
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                    httpPost.addHeader("app_id", favcyapp_id);
                    httpPost.addHeader("app_secret", favcyapp_secret);

                    httpPost.getEntity();
                    HttpResponse response = httpClient.execute(httpPost);
                    responseStr = "";
                    int responseCode = response.getStatusLine().getStatusCode();
                    Log.i("FavcyJsonParser response code", ""+responseCode + response);
                    switch (responseCode) {
                        default:
                            HttpEntity entity = response.getEntity();
                            if (entity != null) {
                                String responseBody = EntityUtils.toString(entity);
                                responseStr = responseBody;
                            }
                            break;
                    }
                    Log.i("FavcyJsonParser response code", ""+responseStr);
                } catch (Exception e) {
                    Log.i("FeettttavcyJsonParser", ""+e.toString());
                    e.printStackTrace();
                }
                // Making HTTP Request
            } else if (method == "GET") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        json = responseStr.toString();
        Log.i("FeettttavcyJsonParser", ""+json);
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.i("json parser", ""+e.toString()+ "errpr parsing");
            e.printStackTrace();
        }

        Log.i("json parser", ""+jObj);
        return jObj;
    }

    public static JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params, String favcyapp_id, String favcyapp_secret, String request_url) {
        String responseStr = null;
        try {
            if (method == "POST") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                // Url Encoding the POST parameters
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                    httpPost.addHeader("app_id", favcyapp_id);
                    httpPost.addHeader("app_secret", favcyapp_secret);
                    httpPost.addHeader("request_url", request_url);
                    HttpResponse response = httpClient.execute(httpPost);
                    responseStr = "";
                    int responseCode = response.getStatusLine().getStatusCode();
                    switch (responseCode) {
                        default:
                            HttpEntity entity = response.getEntity();
                            if (entity != null) {
                                String responseBody = EntityUtils.toString(entity);
                                responseStr = responseBody;
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Making HTTP Request
            } else if (method == "GET") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        json = responseStr.toString();
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
        }
        return jObj;
    }



}