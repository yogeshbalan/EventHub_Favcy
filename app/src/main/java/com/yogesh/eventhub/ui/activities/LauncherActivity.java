package com.yogesh.eventhub.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.favcy.favcyactivity.FavcyLogin;

import me.gilo.eventhub.R;

public class LauncherActivity extends AppCompatActivity {

    Button login, Main;
    String favcyapp_id="A9FKZEJCMKADFGZ";
    String favcyapp_secret="FRTTKCD27J8DEQS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_view);

        /*startActivity(new Intent(getBaseContext(), SplashActivity.class));

        finish();*/

        login =(Button)findViewById(R.id.login);
        Main = (Button) findViewById(R.id.loginNEW);
        Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LauncherActivity.this, LandingPage.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LauncherActivity.this, FavcyLogin.class);
                i.putExtra("app_id",favcyapp_id);
                i.putExtra("app_secret", favcyapp_secret);
                startActivityForResult(i, 2);
            }
        });

    }
}
