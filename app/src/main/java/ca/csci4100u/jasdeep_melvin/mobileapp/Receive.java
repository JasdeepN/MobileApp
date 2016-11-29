package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Receive extends AppCompatActivity {

    Intent Notification_intent;
    NotificationManager notificationManager;
    Notification n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ca.csci4100u.jasdeep_melvin.mobileapp.R.layout.activity_recieve);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("message");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        set_up_notifications();


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("Tag", value);
                ImageView image = (ImageView) findViewById(ca.csci4100u.jasdeep_melvin.mobileapp.R.id.recieve_image_img);

                byte[] byteArray = Base64.decode(value, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                image.setImageBitmap(bitmap);
                notificationManager.notify(0, n);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("Receive", "Failed to read");
            }
        });
    }

    public void set_up_notifications (){
        Notification_intent = new Intent(this, Receive.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, Notification_intent, 0);

        n  = new Notification.Builder(this)
                .setContentTitle(String.format(getString(ca.csci4100u.jasdeep_melvin.mobileapp.R.string.notification_name)))
                .setContentText(String.format(getString(ca.csci4100u.jasdeep_melvin.mobileapp.R.string.notification_msg)))
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true).build();

    }
}

