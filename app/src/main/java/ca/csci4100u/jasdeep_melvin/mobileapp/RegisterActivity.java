package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    AccountDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        helper = new AccountDBHelper(this);
    }

    public void cancel(View v) {
        this.finish();
    }

    public void register(View v) {

        TextView username = (TextView) findViewById(R.id.register_username_edit);
        TextView password = (TextView) findViewById(R.id.register_password_edit);
        TextView passwordConfirm = (TextView) findViewById(R.id.register_passwordConfirm_edit);

        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String confirmText = password.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(usernameText, passwordText);
        auth.createUserWithEmailAndPassword()

        if(password.getText().toString().trim().length() <= 0 || username.getText().toString().trim().length() <= 0){
            Toast.makeText(getBaseContext(), R.string.register_blank_toast, Toast.LENGTH_SHORT).show();
            return;
        }else if(!password.getText().toString().equals(passwordConfirm.getText().toString())){
            Toast.makeText(getBaseContext(), R.string.register_passwordNoMatch_toast, Toast.LENGTH_SHORT).show();
            return;
        } else if (helper.usernameExists(username.getText().toString())) {
            Toast.makeText(getBaseContext(), R.string.register_userExists_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        helper.addNewAccount(username.getText().toString(), password.getText().toString());
        this.finish();
    }
}

