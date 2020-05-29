package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BallisticTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballistic_type);
    }

    public void goToLBXDataActivity(View view) {
        Intent intent = new Intent(this, LBXEntryActivity.class);
        startActivity(intent);
    }

    public void goToSBGDataActivity(View view) {
        Intent intent = new Intent(this, SIlverBulletGaussEntryActivity.class);
        startActivity(intent);
    }

    public void goToUltraACActivity(View view) {
        Intent intent = new Intent(this, UltraACEntryActivity.class);
        startActivity(intent);
    }

    public void goToRotaryACActivity(View view) {
        Intent intent = new Intent(this, RotaryACEntryActivity.class);
        startActivity(intent);
    }

    public void goToHyperAssaultGaussActivity(View view) {
        Intent intent = new Intent(this, HyperAssaultGaussEntryActivity.class);
        startActivity(intent);
    }
}
