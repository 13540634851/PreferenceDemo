package com.can.testpreference;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Display the fragment as the main content.
        //if (savedInstanceState == null) {
        //    getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
        //            new TestFragment()).commit();
        //}


    }



}
