package com.example.kiitlibrarypass;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spStream;
    TextView tvName,tvRoll,tvContact,tvHoste,tvYear,tvStream,tvBranch,cnfpwdtv,pwdtv;
    EditText etName,etRoll,etContact,etHostel,etYear,etBranch,pwd,cnfpwd;
    Button btRegister;
    private FirebaseAuth mauth;
    private ProgressDialog loadingbar;
    private DatabaseReference rootrefernce;
    private String roll,setpwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mauth=FirebaseAuth.getInstance();
        loadingbar=new ProgressDialog(this);
        rootrefernce= FirebaseDatabase.getInstance().getReference();
        tvName=findViewById(R.id.tvName);
        tvRoll=findViewById(R.id.tvRoll);
        tvContact=findViewById(R.id.tvContact);
        tvHoste=findViewById(R.id.tvHostel);
        tvYear=findViewById(R.id.tvYear);
        tvStream=findViewById(R.id.tvStream);
        tvBranch=findViewById(R.id.tvBranch);
        cnfpwdtv=findViewById(R.id.cnfpwdtv);
        pwdtv=findViewById(R.id.pwd);
        cnfpwd=findViewById(R.id.cnfpwd);
        pwd=findViewById(R.id.pwdet);

        etName=findViewById(R.id.etName);
        etRoll=findViewById(R.id.etRoll);
        etContact=findViewById(R.id.etContact);
        etHostel=findViewById(R.id.etHostel);
        etYear=findViewById(R.id.etYear);
        etBranch=findViewById(R.id.etBranch);

        spStream=findViewById(R.id.spStream);

        btRegister=findViewById(R.id.btRegister);

        spStream.setOnItemSelectedListener(this);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag;

                if(etName.getText().toString().trim().length()==0)
                    etName.setError("Name is required");
                if (etRoll.getText().toString().trim().length()==0)
                    etRoll.setError("Roll no. is required");
                else
                {
                    roll=etRoll.getText().toString();
                    roll=roll.concat("@kiit.ac.in");}
                // Toast.makeText(registration.this,roll,Toast.LENGTH_LONG).show();
                if(etContact.getText().toString().trim().length()==0)
                {
                    etContact.setError("Contact No. is required");
                    flag=0;}
                if(etContact.getText().toString().trim().length()!=10)
                {
                    etContact.setError("Enter correct contact no.");
                    flag=0;}
                if (etHostel.getText().toString().trim().length()==0) {
                    etHostel.setError("Hostel namae is required");
                    flag = 0;
                }
                if(etYear.getText().toString().trim().length()==0)
                {
                    etYear.setError("Year is required");
                    flag=0;}


                if (etBranch.getText().toString().trim().length()==0)
                {
                    etBranch.setError("Branch is required");
                    flag=0;}
                if (pwd.getText().toString().trim().length()==0)
                {
                    pwd.setError("Please set password");
                    flag=0;}
                if(pwd.getText().toString().equals(cnfpwd.getText().toString())){
                    setpwd=cnfpwd.getText().toString();

                    flag=1;

                }
                else
                {
                    cnfpwd.setError("Password does not match");
                    flag=0;

                }
                if(flag==1)

                    createaccunt(roll,setpwd);



            }
        });

    }

    private void createaccunt(String roll, String setpwd) {

        loadingbar.setTitle("Creating new Account");
        loadingbar.setMessage("Please wait while we are creating account for you");
        loadingbar.setCanceledOnTouchOutside(true);
        loadingbar.show();
        mauth.createUserWithEmailAndPassword(roll,setpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String currentuserid=mauth.getCurrentUser().getUid();
                    rootrefernce.child("Users").child(currentuserid).setValue("");
                    Toast.makeText(registration.this,"Account created suuccsefuly",Toast.LENGTH_LONG).show();
                    loadingbar.dismiss();
                    sendUsertomaintivity();
                }
                else
                {
                    String message=task.getException().toString();
                    Toast.makeText(registration.this,message,Toast.LENGTH_LONG).show();
                    loadingbar.dismiss();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        ((TextView)spStream.getSelectedView()).setError("Stream is required");

    }

    private void sendUsertomaintivity() {

        Intent mainintent=new Intent(registration.this,MainActivity.class);

        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();

    }
}
