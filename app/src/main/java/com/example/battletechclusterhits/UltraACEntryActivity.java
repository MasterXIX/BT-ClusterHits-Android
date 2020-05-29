package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UltraACEntryActivity extends AppCompatActivity {
    private String weaponType = "UAC";
    private String targetFacing = "";
    private int weaponValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultra_acentry);
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnUACFacingLeft);
            Button right = (Button) findViewById(R.id.btnUACFacingRight);
            Button center = (Button) findViewById(R.id.btnUACFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue <= 2) {
            Button two = (Button) findViewById(R.id.btnUACSize2);
            Button five = (Button) findViewById(R.id.btnUACSize5);
            Button ten = (Button) findViewById(R.id.btnUACSize10);
            Button twenty = (Button) findViewById(R.id.btnUACSize20);

            two.setBackgroundColor(getColor(R.color.highlightMistake));
            five.setBackgroundColor(getColor(R.color.highlightMistake));
            ten.setBackgroundColor(getColor(R.color.highlightMistake));
            twenty.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnUACFacingLeft);
        Button center = (Button) findViewById(R.id.btnUACFacingCenter);
        Button right = (Button) findViewById(R.id.btnUACFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button lb2 = (Button) findViewById(R.id.btnUACSize2);
        Button lb5 = (Button) findViewById(R.id.btnUACSize5);
        Button lb10 = (Button) findViewById(R.id.btnUACSize10);
        Button lb20 = (Button) findViewById(R.id.btnUACSize20);

        lb2.setBackgroundColor(android.R.drawable.btn_default);
        lb5.setBackgroundColor(android.R.drawable.btn_default);
        lb10.setBackgroundColor(android.R.drawable.btn_default);
        lb20.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("UAC/2")) {
            return 2;
        }
        else if(weapon.matches("UAC/5")) {
            return 5;
        }
        else if(weapon.matches("UAC/10")) {
            return 10;
        }
        else if (weapon.matches("UAC/20")) {
            return 20;
        }
        else {
            return -1;
        }

    }

    public void buttonManualClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue < 2) {
            highlightMistake();
        }
        else {
            Intent intent = new Intent(this, ManualClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            //weaponValue is used for most weapons to determine which cluster column to roll on,
            //so use 2 for all UAC weapons
            intent.putExtra("weaponValue", 2);
            intent.putExtra("variableDamage", weaponValue);
            startActivity(intent);
        }
    }

    public void buttonAutomaticClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue < 2) {
            highlightMistake();
        }
        else {
            Intent intent = new Intent(this, AutomaticClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            //weaponValue is used for most weapons to determine which cluster column to roll on,
            //so use 2 for all UAC weapons
            intent.putExtra("weaponValue", 2);
            intent.putExtra("variableDamage", weaponValue);
            startActivity(intent);
        }
    }
}
