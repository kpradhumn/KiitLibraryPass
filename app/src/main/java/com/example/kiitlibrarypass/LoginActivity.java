package com.example.kiitlibrarypass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText etUser,etPassword;
    TextView tvForgotpass,tvNewuser;
    Button btLogin;
    private FirebaseAuth mauth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mauth= FirebaseAuth.getInstance();
        etUser=findViewById(R.id.etUser);
        etPassword=findViewById(R.id.etPassword);
        tvForgotpass=findViewById(R.id.tvForgotpass);
        tvNewuser=findViewById(R.id.tvNewuser);
        btLogin=findViewById(R.id.btLogin);
        loadingbar=new ProgressDialog(this);

        tvNewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUsertoregactivity();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowusertologin();


            }

        });


    }
    private void allowusertologin() {
        String useremail=etUser.getText().toString();
        useremail=useremail.concat("@kiit.ac.in");
        String userpwd=etPassword.getText().toString();
        if(etUser.getText().toString().trim().length()<=6)
            etUser.setError("User id is required");
        else if(etPassword.getText().toString().trim().length()==0)
            etPassword.setError("password is required");

        else {

            loadingbar.setTitle("Signing in");
            loadingbar.setMessage("Please wait while we are signing you in");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();



                mauth.signInWithEmailAndPassword(useremail, userpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mauth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "Logged in successfuly", Toast.LENGTH_LONG).show();
                                loadingbar.dismiss();
                                sendUsertoMainactivity();
                            }
                            else {
                                loadingbar.dismiss();
                                Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();
                            }
                            //finish();
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            loadingbar.dismiss();
                        }


                    }
                });

            }

        }

    private void sendUsertoregactivity() {

        Intent logininten=new Intent(LoginActivity.this,registration.class);
        startActivity(logininten);


    }
    private void sendUsertoMainactivity() {

        Intent mainintent=new Intent(LoginActivity.this,MainActivity.class);

        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();


    }

}

