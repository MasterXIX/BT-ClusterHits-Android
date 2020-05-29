package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LBXEntryActivity extends AppCompatActivity {
    private String weaponType = "LBX";
    private String targetFacing = "";
    private int weaponValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lbxentry);
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("LB 2-X")) {
            return 2;
        }
        else if(weapon.matches("LB 5-X")) {
            return 5;
        }
        else if(weapon.matches("LB 10-X")) {
            return 10;
        }
        else if (weapon.matches("LB 20-X")) {
            return 20;
        }
        else {
            return -1;
        }

    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnLBXFacingLeft);
        Button center = (Button) findViewById(R.id.btnLBXFacingCenter);
        Button right = (Button) findViewById(R.id.btnLBXFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button lb2 = (Button) findViewById(R.id.btnLBXSize2);
        Button lb5 = (Button) findViewById(R.id.btnLBXSize5);
        Button lb10 = (Button) findViewById(R.id.btnLBXSize10);
        Button lb20 = (Button) findViewById(R.id.btnLBXSize20);

        lb2.setBackgroundColor(android.R.drawable.btn_default);
        lb5.setBackgroundColor(android.R.drawable.btn_default);
        lb10.setBackgroundColor(android.R.drawable.btn_default);
        lb20.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    public void buttonManualClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue < 2) {
            highlightMistake();
        }
        else {
            Intent intent = new Intent(this, ManualClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            intent.putExtra("weaponValue", weaponValue);
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
            intent.putExtra("weaponValue", weaponValue);
            startActivity(intent);
        }
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnLBXFacingLeft);
            Button right = (Button) findViewById(R.id.btnLBXFacingRight);
            Button center = (Button) findViewById(R.id.btnLBXFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue <= 2) {
            Button two = (Button) findViewById(R.id.btnLBXSize2);
            Button five = (Button) findViewById(R.id.btnLBXSize5);
            Button ten = (Button) findViewById(R.id.btnLBXSize10);
            Button twenty = (Button) findViewById(R.id.btnLBXSize20);

            two.setBackgroundColor(getColor(R.color.highlightMistake));
            five.setBackgroundColor(getColor(R.color.highlightMistake));
            ten.setBackgroundColor(getColor(R.color.highlightMistake));
            twenty.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }
}
