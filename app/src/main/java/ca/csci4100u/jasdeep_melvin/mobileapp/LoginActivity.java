package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private AccountDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helper = new AccountDBHelper(this);
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    public void login(View v) {
        EditText username = (EditText)findViewById(R.id.login_username_edit);
        EditText password = (EditText)findViewById(R.id.login_password_edit);
        if(helper.loginPass(username.getText().toString(), password.getText().toString())) {
            this.finish();
            return;
        }
        Toast.makeText(getBaseContext(), R.string.login_loginFail_toast, Toast.LENGTH_SHORT).show();
    }
}
