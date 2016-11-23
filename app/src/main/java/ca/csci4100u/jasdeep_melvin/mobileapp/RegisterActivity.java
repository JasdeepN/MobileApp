package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void cancel(View v) {
        this.finish();
    }

    public void register(View v) {

    }
}

