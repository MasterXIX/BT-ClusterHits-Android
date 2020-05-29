package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class AdvancedTacticalMissileEntryActivity extends AppCompatActivity {
    private String weaponType = "ATM";
    private String targetFacing = "";
    private int weaponValue = -1;
    private int clusterModifier;
    private int variableDamage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_tactical_missile_entry);
    }

    private void updateClusterModifier() {
        clusterModifier = 2;

        CheckBox ams = (CheckBox) findViewById(R.id.cbxATMAMS);
        CheckBox ecm = (CheckBox) findViewById(R.id.cbxATMECM);

        if (ams.isChecked()) {
            clusterModifier = clusterModifier - 4;
        }

        if (ecm.isChecked()) {
            clusterModifier = clusterModifier - 2;
        }
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("ATM 3")) {
            return 3;
        }
        else if(weapon.matches("ATM 6")) {
            return 6;
        }
        else if(weapon.matches("ATM 9")) {
            return 19;
        }
        else if (weapon.matches("ATM 12")) {
            return 12;
        }
        else {
            return -1;
        }

    }

    private int getMissileTypeValue(String missileType) {
        int damage;

        if (missileType.matches("HE")) {
            damage = 3;
        }
        else if (missileType.matches("STD")) {
            damage = 2;
        }
        else if (missileType.matches("ER")) {
            damage = 1;
        }
        else {
            damage = 0;
        }

        return damage;
    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnATMFacingLeft);
        Button center = (Button) findViewById(R.id.btnATMFacingCenter);
        Button right = (Button) findViewById(R.id.btnATMFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button  atm3 = (Button) findViewById(R.id.btnATMSize3);
        Button  atm6 = (Button) findViewById(R.id.btnATMSize6);
        Button  atm9 = (Button) findViewById(R.id.btnATMSize9);
        Button  atm12 = (Button) findViewById(R.id.btnATMSize12);

        atm3.setBackgroundColor(android.R.drawable.btn_default);
        atm6.setBackgroundColor(android.R.drawable.btn_default);
        atm9.setBackgroundColor(android.R.drawable.btn_default);
        atm12.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    public void buttonMissileTypeSelection(View view) {
        Button selected = (Button) view;

        Button  he = (Button) findViewById(R.id.btnATMTypeHE);
        Button  std = (Button) findViewById(R.id.btnATMTypeSTD);
        Button  er = (Button) findViewById(R.id.btnATMTypeER);

        he.setBackgroundColor(android.R.drawable.btn_default);
        std.setBackgroundColor(android.R.drawable.btn_default);
        er.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        variableDamage = getMissileTypeValue(selected.getText().toString());
    }

    public void buttonManualClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue == -1 || variableDamage == 0) {
            highlightMistake();
        }
        else {
            updateClusterModifier();

            Intent intent = new Intent(this, ManualClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            intent.putExtra("weaponValue", weaponValue);
            intent.putExtra("clusterModifier", clusterModifier);
            intent.putExtra("variableDamage", variableDamage);
            startActivity(intent);
        }
    }

    public void buttonAutomaticClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue == -1 || variableDamage == 0) {
            highlightMistake();
        }
        else {
            updateClusterModifier();

            Intent intent = new Intent(this, AutomaticClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            intent.putExtra("weaponValue", weaponValue);
            intent.putExtra("clusterModifier", clusterModifier);
            intent.putExtra("variableDamage", variableDamage);
            startActivity(intent);
        }
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnATMFacingLeft);
            Button right = (Button) findViewById(R.id.btnATMFacingRight);
            Button center = (Button) findViewById(R.id.btnATMFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue == -1) {
            Button atm3 = (Button) findViewById(R.id.btnATMSize3);
            Button atm6 = (Button) findViewById(R.id.btnATMSize6);
            Button atm9 = (Button) findViewById(R.id.btnATMSize9);
            Button atm12 = (Button) findViewById(R.id.btnATMSize12);

            atm3.setBackgroundColor(getColor(R.color.highlightMistake));
            atm6.setBackgroundColor(getColor(R.color.highlightMistake));
            atm9.setBackgroundColor(getColor(R.color.highlightMistake));
            atm12.setBackgroundColor(getColor(R.color.highlightMistake));
        }

        if (variableDamage == 0) {
            Button he = (Button) findViewById(R.id.btnATMTypeHE);
            Button std = (Button) findViewById(R.id.btnATMTypeSTD);
            Button er = (Button) findViewById(R.id.btnATMTypeER);

            he.setBackgroundColor(getColor(R.color.highlightMistake));
            std.setBackgroundColor(getColor(R.color.highlightMistake));
            er.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }

}
