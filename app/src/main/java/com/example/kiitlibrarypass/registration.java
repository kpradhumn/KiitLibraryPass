package com.example.kiitlibrarypass;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spStream;
    TextView tvName,tvRoll,tvContact,tvHoste,tvYear,tvStream,tvBranch;
    EditText etName,etRoll,etContact,etHostel,etYear,etBranch;
    Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        tvName=findViewById(R.id.tvName);
        tvRoll=findViewById(R.id.tvRoll);
        tvContact=findViewById(R.id.tvContact);
        tvHoste=findViewById(R.id.tvHostel);
        tvYear=findViewById(R.id.tvYear);
        tvStream=findViewById(R.id.tvStream);
        tvBranch=findViewById(R.id.tvBranch);

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

                if(etName.getText().toString().trim().length()==0)
                    etName.setError("Name is required");
                else if (etRoll.getText().toString().trim().length()==0)
                    etRoll.setError("Roll no. is required");
                else if(etContact.getText().toString().trim().length()==0)
                    etContact.setError("Contact No. is required");
                else if(etContact.getText().toString().trim().length()<=9)
                    etContact.setError("Enter correct contact no.");
                else if (etHostel.getText().toString().trim().length()==0)
                    etHostel.setError("Hostel namae is required");
                else if(etYear.getText().toString().trim().length()==0)
                    etYear.setError("Year is required");


                else if (etBranch.getText().toString().trim().length()==0)
                    etBranch.setError("Branch is required");
                else
                    Toast.makeText(registration.this,"Registration Sucsssfull",Toast.LENGTH_SHORT).show();

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
}
