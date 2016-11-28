package ca.csci4100u.jasdeep_melvin.mobileapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static ca.csci4100u.jasdeep_melvin.mobileapp.R.id.color_picker;
import static ca.csci4100u.jasdeep_melvin.mobileapp.R.id.fab;


/**
 * Created by jasde on 2016-11-23.
 */

public class Draw extends AppCompatActivity implements View.OnClickListener{
    Button clear_button;
    Button send_button;
    FloatingActionButton fab_button;
    FloatingActionButton color_pick_fab;

    Paint color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tool_menu_activity);

        clear_button = (Button) findViewById(R.id.clear_button);
        send_button = (Button) findViewById(R.id.send_button);
        fab_button = (FloatingActionButton) findViewById(fab);
        color_pick_fab = (FloatingActionButton) findViewById(color_picker);

        clear_button.setOnClickListener(this);
        fab_button.setOnClickListener(this);
        send_button.setOnClickListener(this);
        color_pick_fab.setOnClickListener(this);

        color = DrawingView.getPaint();
    }

    @Override
    public void onClick(View target){
        if (target.equals(clear_button)){
            Toast.makeText(getApplicationContext(), "clear", Toast.LENGTH_SHORT).show();
            DrawingView.getDrawView().clearDrawing();
        } else if (target.equals(send_button)){
            send();
            Toast.makeText(getApplicationContext(), "send", Toast.LENGTH_SHORT).show();
        } else if (target.equals(fab_button)) {
            DrawingView.getDrawView().saveDrawing();
            Toast.makeText(getApplicationContext(), "image saved to sd card", Toast.LENGTH_SHORT).show();
        } else if (target.equals(color_pick_fab)) {
            new DrawingView.ColorPicker(this, color.getColor()).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            DrawingView.getDrawView().saveDrawing();
        }
    }

    private void send() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);

    }
}
