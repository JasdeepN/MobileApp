package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button go_to_canvas_button;
    Button log_out_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        go_to_canvas_button = (Button)findViewById(R.id.open_canvas_button);
        go_to_canvas_button.setOnClickListener(MainActivity.this);

        log_out_button = (Button)findViewById(R.id.logout_button);
        log_out_button.setOnClickListener(MainActivity.this);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onClick(android.view.View target){
        if(target.equals(go_to_canvas_button)) {
            Intent i = new Intent(target.getContext(), Draw.class);
            target.getContext().startActivity(i);
        } else if (target.equals(log_out_button)){
            this.finish();
            return;
        }
    }
}
