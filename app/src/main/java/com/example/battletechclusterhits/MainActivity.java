package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToBallisticActivity(View view) {
        Intent intent = new Intent(this, BallisticTypeActivity.class);
        startActivity(intent);
    }

    public void goToMissileActivity(View view) {
        Intent intent = new Intent(this, MissileTypeActivity.class);
        startActivity(intent);
    }
}
