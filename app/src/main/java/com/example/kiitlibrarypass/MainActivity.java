package com.example.kiitlibrarypass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private DatabaseReference rootref;
    private FirebaseUser currentuser;
    private Button generate,generated;
    private TextView etName,etRoll,date,time,cancelpass;
    private String currentuserid;
    private Context context;
    private AlertDialog.Builder alertdialogbuilder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth=FirebaseAuth.getInstance();
        currentuser=mauth.getCurrentUser();
        generate=findViewById(R.id.btGenratePass);
        generated=findViewById(R.id.btGenratedPass);
        rootref = FirebaseDatabase.getInstance().getReference();
        etName=findViewById(R.id.tvStudentName);
        etRoll=findViewById(R.id.tvStudentRoll);
        date=findViewById(R.id.tvDate);
        time=findViewById(R.id.tvTime);
        cancelpass=findViewById(R.id.tvcancelpass);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date1 = new Date();

                String strDateFormat = "hh:mm:ss a";

                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

                String formattedDate= dateFormat.format(date1);
                String onlyhr=formattedDate.substring(0,2);
               int finalhr=Integer.parseInt(onlyhr);


                if((finalhr>=7)) {
                    generate.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake));
                    Toast.makeText(MainActivity.this,"Pass generation time exceeded",Toast.LENGTH_LONG).show();
                    generate.setText("Pass Generation failed!!");
                    generate.setBackgroundColor(Color.RED);
                }
                else
                {

                    generate.setVisibility(View.INVISIBLE);
                    generated.setVisibility(View.VISIBLE);
                    fetchstudentdetails();
                    Calendar calfordate = Calendar.getInstance();
                    SimpleDateFormat currentdateformat = new SimpleDateFormat(" dd MMM,yyyy");
                    String currentdate = currentdateformat.format(calfordate.getTime());
                    Calendar calfortime = Calendar.getInstance();
                    SimpleDateFormat currenttimeformat = new SimpleDateFormat("hh:mm a");
                    String currenttime = currenttimeformat.format(calfortime.getTime());
                    date.setText(currentdate);
                    time.setText(currenttime);
                }
            }
        });


        cancelpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertdialogbuilder =new AlertDialog.Builder(MainActivity.this);
                inflater= LayoutInflater.from(MainActivity.this);
                View view=inflater.inflate(R.layout.conformationdialog,null);
                Button nobtn=(Button) view.findViewById(R.id.nobtn);
                Button yesbtn=(Button) view.findViewById(R.id.yesbtn);
                alertdialogbuilder.setView(view);
                alertDialog=alertdialogbuilder.create();
                alertDialog.show();
                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentuserid=mauth.getCurrentUser().getUid();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("StudentList").child(currentuserid);
                        reference.removeValue();
                        alertDialog.dismiss();
                        generated.setVisibility(View.INVISIBLE);
                        generate.setVisibility(View.VISIBLE);



                    }
                });


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentuser == null) {
            sendUsertologinactivity();
            finish();
        } else {
            fetchinfo();
            currentuserid = mauth.getCurrentUser().getUid();
            rootref.child("StudentList").child(currentuserid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ((dataSnapshot.exists()))
                    {
                        generated.setVisibility(View.VISIBLE);
                        generate.setVisibility(View.INVISIBLE);



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }



    private void fetchinfo() {
        currentuserid=mauth.getCurrentUser().getUid();
        rootref.child("Users").child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name"))) {
                    String retrieveusername = dataSnapshot.child("name").getValue().toString();
                    String roll=dataSnapshot.child("roll").getValue().toString();

                    etName.setText(retrieveusername);
                    etRoll.setText(roll);

                }
                else
                {
                    etName.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Please set and update your profile info",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Update:
            {
                Intent Registration_Intent=new Intent(MainActivity.this,UpdateUser.class);
                startActivity(Registration_Intent);
                break;
            }
            case R.id.Logout:
            {
                mauth.signOut();
                sendUsertologinactivity();
                finish();
                break;
            }
            case R.id.Guide:
            {
                Intent Guideline_Intent=new Intent(MainActivity.this,Guideline.class);
                startActivity(Guideline_Intent);
                break;
            }


        }
        return true;
    }
    private void sendUsertologinactivity() {
        Intent loginintent=new Intent(MainActivity.this,LoginActivity.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginintent);
        finish();
    }
    private void fetchstudentdetails() {
            currentuserid=mauth.getCurrentUser().getUid();
            rootref.child("Users").child(currentuserid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name"))) {
                        String retrieveusername = dataSnapshot.child("name").getValue().toString();
                        String roll=dataSnapshot.child("roll").getValue().toString();
                        String branch=dataSnapshot.child("branch").getValue().toString();
                        String year=dataSnapshot.child("year").getValue().toString();
                        String stream=dataSnapshot.child("stream").getValue().toString();
                        String hostel=dataSnapshot.child("hostel").getValue().toString();
                        String contact=dataSnapshot.child("contact").getValue().toString();


                    currentuserid=mauth.getCurrentUser().getUid();

                    HashMap<String,String> profilemap=new HashMap<>();
                    profilemap.put("uid",currentuserid);
                    profilemap.put("name",retrieveusername);
                    profilemap.put("roll",roll);
                    profilemap.put("year",year);
                    profilemap.put("stream",stream);
                    profilemap.put("branch",branch);
                    profilemap.put("hostel",hostel);
                    profilemap.put("contact",contact);

                    rootref.child("StudentList").child(currentuserid).setValue(profilemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Information sent", Toast.LENGTH_LONG).show();

                            } else {
                                String msg = task.getException().toString();
                                Toast.makeText(MainActivity.this, "Error" + msg, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
