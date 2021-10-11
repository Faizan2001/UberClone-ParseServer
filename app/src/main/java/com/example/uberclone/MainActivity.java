package com.example.uberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    String userType = "rider";

    public void redirectActivity() {

        if (ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")) {

            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);

        } else {

            if (ParseUser.getCurrentUser().get("riderOrDriver").equals("driver")) {


                Intent intent = new Intent(getApplicationContext(), ViewRequestsActivity.class);

                startActivity(intent);
            }

        }

    }

    public void getStarted(View view) {


        Switch userTypeSwitch = (Switch) findViewById(R.id.switch1);

        Log.i("Switch value", String.valueOf(userTypeSwitch.isChecked()));


        if (userTypeSwitch.isChecked()) {

            userType = "driver";

        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                redirectActivity();

            }
        });



    }










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();

        if (ParseUser.getCurrentUser() == null) {

            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e == null) {
                        Log.i("Result", "Anonymous Log in Successful!");
                    }


                }
            });


        } else if (ParseUser.getCurrentUser() != null) {

            if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {
                Log.i("Result", "Redirecting as" + ParseUser.getCurrentUser().get("riderOrDriver"));

                redirectActivity();
            }

        }




        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}