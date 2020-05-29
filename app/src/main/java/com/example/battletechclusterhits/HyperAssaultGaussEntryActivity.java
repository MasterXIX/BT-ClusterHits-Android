package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HyperAssaultGaussEntryActivity extends AppCompatActivity {
    private String weaponType = "HAG";
    private String targetFacing = "";
    private int weaponValue = -1;

    private int clusterModifier = -4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyper_assault_gauss_entry);
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("HAG 20")) {
            return 20;
        }
        else if(weapon.matches("HAG 30")) {
            return 30;
        }
        else if(weapon.matches("HAG 40")) {
            return 40;
        }
        else {
            return -1;
        }

    }

    private int getRangeModifier(String range) {
        if (range.matches("Short")) {
            return 2;
        }
        else if (range.matches("Medium")) {
            return 0;
        }
        else if (range.matches("Long")) {
            return -2;
        }
        else {
            return -4;
        }
    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnHAGFacingLeft);
        Button center = (Button) findViewById(R.id.btnHAGFacingCenter);
        Button right = (Button) findViewById(R.id.btnHAGFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button HAG20 = (Button) findViewById(R.id.btnHAGSize20);
        Button HAG30 = (Button) findViewById(R.id.btnHAGSize30);
        Button HAG40 = (Button) findViewById(R.id.btnHAGSize40);

        HAG20.setBackgroundColor(android.R.drawable.btn_default);
        HAG30.setBackgroundColor(android.R.drawable.btn_default);
        HAG40.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    public void buttonRangeSelection(View view) {
        Button selected = (Button) view;

        Button Short = (Button) findViewById(R.id.btnHAGRangeShort);
        Button Medium = (Button) findViewById(R.id.btnHAGRangeMedium);
        Button Long = (Button) findViewById(R.id.btnHAGRangeLong);

        Short.setBackgroundColor(android.R.drawable.btn_default);
        Medium.setBackgroundColor(android.R.drawable.btn_default);
        Long.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        clusterModifier = getRangeModifier(selected.getText().toString());
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnHAGFacingLeft);
            Button right = (Button) findViewById(R.id.btnHAGFacingRight);
            Button center = (Button) findViewById(R.id.btnHAGFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue <= 2) {
            Button twenty = (Button) findViewById(R.id.btnHAGSize20);
            Button thirty = (Button) findViewById(R.id.btnHAGSize30);
            Button forty = (Button) findViewById(R.id.btnHAGSize40);

            twenty.setBackgroundColor(getColor(R.color.highlightMistake));
            thirty.setBackgroundColor(getColor(R.color.highlightMistake));
            forty.setBackgroundColor(getColor(R.color.highlightMistake));
        }

        if (clusterModifier < -2) {
            Button Short = (Button) findViewById(R.id.btnHAGRangeShort);
            Button Medium = (Button) findViewById(R.id.btnHAGRangeMedium);
            Button Long = (Button) findViewById(R.id.btnHAGRangeLong);

            Short.setBackgroundColor(getColor(R.color.highlightMistake));
            Medium.setBackgroundColor(getColor(R.color.highlightMistake));
            Long.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }

    public void buttonManualClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue < 2 || clusterModifier < -2) {
            highlightMistake();
        }
        else {
            Intent intent = new Intent(this, ManualClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            intent.putExtra("weaponValue", weaponValue);
            intent.putExtra("clusterModifier", clusterModifier);
            startActivity(intent);
        }
    }

    public void buttonAutomaticClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue < 2 || clusterModifier < -2) {
            highlightMistake();
        }
        else {
            Intent intent = new Intent(this, AutomaticClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            intent.putExtra("weaponValue", weaponValue);
            intent.putExtra("clusterModifier", clusterModifier);
            startActivity(intent);
        }
    }

}
