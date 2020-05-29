package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MissileTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missile_type);
    }

    public void goToSRM(View view) {
        Intent intent = new Intent(this, ShortRangeMissileEntryActivity.class);
        startActivity(intent);
    }

    public void goToMRM(View view) {
        Intent intent = new Intent(this, MediumRangeMissileEntryActivity.class);
        startActivity(intent);
    }

    public void goToLRM(View view) {
        Intent intent = new Intent(this, LongRangeMissileEntryActivity.class);
        startActivity(intent);
    }

    public void goToATM(View view) {
        Intent intent = new Intent(this, AdvancedTacticalMissileEntryActivity.class);
        startActivity(intent);
    }
}
