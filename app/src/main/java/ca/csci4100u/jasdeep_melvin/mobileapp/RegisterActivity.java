package ca.csci4100u.jasdeep_melvin.mobileapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    AccountDBHelper helper;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    final private String TAG = "RegisterActivity";
    final private String INVALIDEMAIL = "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.";
    final private String EMAILEXISTS = "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        helper = new AccountDBHelper(this);
        firebaseAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        firebaseAuth.addAuthStateListener(authListener);
    }

    public void cancel(View v) {
        this.finish();
    }

    public void register(View v) {

        TextView email = (TextView) findViewById(R.id.register_email_edit);
        TextView password = (TextView) findViewById(R.id.register_password_edit);
        TextView passwordConfirm = (TextView) findViewById(R.id.register_passwordConfirm_edit);

        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String confirmText = passwordConfirm.getText().toString().trim();


        if(passwordText.length() <= 5){
            Toast.makeText(getBaseContext(), R.string.register_invalidPassword_toast, Toast.LENGTH_SHORT).show();
            return;
        } else if(!passwordText.equals(confirmText)) {
            Toast.makeText(getBaseContext(), R.string.register_passwordNoMatch_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()) {
                            Log.d(TAG, task.getException().toString());

                            if(task.getException().toString().equals(INVALIDEMAIL)){
                                Toast.makeText(getBaseContext(), R.string.register_invalidEmail_toast, Toast.LENGTH_SHORT).show();
                            } else if (task.getException().toString().equals(EMAILEXISTS)){
                                Toast.makeText(getBaseContext(), R.string.register_emailExists_toast, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), R.string.register_accountCreateFail_toast, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            finish();
                        }
                    }
                });

    }
}