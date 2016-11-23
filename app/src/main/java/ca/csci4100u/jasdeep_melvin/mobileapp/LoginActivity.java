package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    public void login(View v) {

    }
}
