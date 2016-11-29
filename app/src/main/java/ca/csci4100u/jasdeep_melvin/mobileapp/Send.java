package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class Send extends AppCompatActivity {
    private String data;
    private ChatDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        helper = new ChatDBHelper(this);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("byteArray");
        data = Base64.encodeToString(byteArray, Base64.DEFAULT);
        ImageView image = (ImageView)findViewById(R.id.previewImage);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bitmap);
    }

    public void send(View v){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue(data);
        helper.updateYourMessage(helper.getChat(FirebaseAuth.getInstance().getCurrentUser().getEmail()), data);
        Intent intent = new Intent();
        intent.putExtra("sent", true);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }

    public void cancel(View v){
        Intent intent = new Intent();
        intent.putExtra("sent", false);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }

}
