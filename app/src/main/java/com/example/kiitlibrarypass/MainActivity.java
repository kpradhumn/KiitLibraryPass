package com.example.kiitlibrarypass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent Registration_Intent=new Intent(MainActivity.this,registration.class);
                startActivity(Registration_Intent);
                break;
            }
            case R.id.Logout:
            {
                Intent Home_Intent=new Intent(pass_gen.this,HomeActivity.class);
                startActivity(Home_Intent);
                break;
            }
            case R.id.Guide:
            {
                Intent Guideline_Intent=new Intent(pass_gen.this,guideline.class);
                startActivity(Guideline_Intent);
                break;
            }


        }
        return true;
    }
}