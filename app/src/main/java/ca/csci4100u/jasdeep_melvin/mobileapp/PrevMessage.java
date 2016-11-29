package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class PrevMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_message);
        ChatDBHelper helper = new ChatDBHelper(this);
        Chat chat = helper.getChat(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        byte[] byteArray = Base64.decode(chat.getYourLastMessage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = (ImageView)findViewById(R.id.prevMessage_image);
        image.setImageBitmap(bitmap);
    }
}
