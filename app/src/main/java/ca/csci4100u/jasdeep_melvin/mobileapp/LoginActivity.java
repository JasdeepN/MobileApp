package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    final private String TAG = "LoginActivity";
    final private String INVALIDEMAIL = "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.";
    final private String EMAILEXISTS = "com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted.";
    final private String INVALIDPASSWORD = "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The password is invalid or the user does not have a password.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        firebaseAuth.addAuthStateListener(authListener);

    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    public void login(View v) {
        EditText username = (EditText) findViewById(R.id.login_email_edit);
        EditText password = (EditText) findViewById(R.id.login_password_edit);
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        if(usernameText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(getBaseContext(), R.string.login_empty_toast, Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(usernameText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Log.d(TAG, task.getException().toString());
                            if(task.getException().toString().equals(INVALIDEMAIL)){
                                Toast.makeText(getBaseContext(), R.string.login_invalidEmail_toast, Toast.LENGTH_SHORT).show();
                            } else if (task.getException().toString().equals(EMAILEXISTS)){
                                Toast.makeText(getBaseContext(), R.string.login_invalidEmail_toast, Toast.LENGTH_SHORT).show();
                            } else if  (task.getException().toString().equals(INVALIDPASSWORD)) {
                                Toast.makeText(getBaseContext(), R.string.login_invalidPassword_toast, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), R.string.login_loginFail_toast, Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            finish();
                        }
                    }
                });
    }


}
