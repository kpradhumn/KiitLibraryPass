package com.example.kiitlibrarypass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText etUser,etPassword;
    TextView tvForgotpass,tvNewuser;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser=findViewById(R.id.etUser);
        etPassword=findViewById(R.id.etPassword);
        tvForgotpass=findViewById(R.id.tvForgotpass);
        tvNewuser=findViewById(R.id.tvNewuser);
        btLogin=findViewById(R.id.btLogin);

        tvNewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Registration_Intent=new Intent(LoginActivity.this,registration.class);
                startActivity(Registration_Intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUser.getText().toString().trim().length()<=6)
                    etUser.setError("User id is required");
                else if(etPassword.getText().toString().trim().length()==0)
                    etPassword.setError("password is required");
                else {
                    Intent Pass_gen_Intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(Pass_gen_Intent);
                }
            }
        });

    }
}
