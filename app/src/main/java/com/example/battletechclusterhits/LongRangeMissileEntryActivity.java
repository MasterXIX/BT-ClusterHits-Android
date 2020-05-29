package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class LongRangeMissileEntryActivity extends AppCompatActivity {
    private String weaponType = "LRM";
    private String targetFacing = "";
    private int weaponValue = -1;
    private int clusterModifier;
    private boolean streakMissiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_range_missile_entry);
    }

    private void updateClusterModifier() {
        clusterModifier = 0;
        streakMissiles = false;

        CheckBox art4 = (CheckBox) findViewById(R.id.cbxLRMArtemis4);
        CheckBox art5 = (CheckBox) findViewById(R.id.cbxLRMArtemis5);
        CheckBox narc = (CheckBox) findViewById(R.id.cbxLRMNarc);
        CheckBox streak = (CheckBox) findViewById(R.id.cbxLRMStreak);
        CheckBox ams = (CheckBox) findViewById(R.id.cbxLRMAMS);

        CheckBox ecm = (CheckBox) findViewById(R.id.cbxLRMECM);
        CheckBox angel = (CheckBox) findViewById(R.id.cbxLRMAngel);

        if (art4.isChecked() && !ecm.isChecked()) {
            clusterModifier = clusterModifier + 2;
        }

        if (art5.isChecked() && !ecm.isChecked()) {
            clusterModifier = clusterModifier + 3;
        }

        if (narc.isChecked() && !ecm.isChecked()) {
            clusterModifier = clusterModifier + 2;
        }

        if (streak.isChecked() && !angel.isChecked()) {
            streakMissiles = true;
        }

        if (ams.isChecked()) {
            clusterModifier = clusterModifier - 4;
        }
    }

    private void altAmmoDeactivate(View view) {
        ((CheckBox) findViewById(R.id.cbxLRMArtemis4)).setVisibility(View.INVISIBLE);
        ((CheckBox) findViewById(R.id.cbxLRMArtemis5)).setVisibility(View.INVISIBLE);
        ((CheckBox) findViewById(R.id.cbxLRMNarc)).setVisibility(View.INVISIBLE);
        ((CheckBox) findViewById(R.id.cbxLRMStreak)).setVisibility(View.INVISIBLE);

        view.setVisibility(View.VISIBLE);
    }

    private void altAmmoActivate() {
        ((CheckBox) findViewById(R.id.cbxLRMArtemis4)).setVisibility(View.VISIBLE);
        ((CheckBox) findViewById(R.id.cbxLRMArtemis5)).setVisibility(View.VISIBLE);
        ((CheckBox) findViewById(R.id.cbxLRMNarc)).setVisibility(View.VISIBLE);
        ((CheckBox) findViewById(R.id.cbxLRMStreak)).setVisibility(View.VISIBLE);
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("LRM 5")) {
            return 5;
        }
        else if(weapon.matches("LRM 10")) {
            return 10;
        }
        else if(weapon.matches("LRM 15")) {
            return 15;
        }
        else if (weapon.matches("LRM 20")) {
            return 20;
        }
        else {
            return -1;
        }
    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnLRMFacingLeft);
        Button center = (Button) findViewById(R.id.btnLRMFacingCenter);
        Button right = (Button) findViewById(R.id.btnLRMFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button  lrm5 = (Button) findViewById(R.id.btnLRMSize5);
        Button  lrm10 = (Button) findViewById(R.id.btnLRMSize10);
        Button  lrm15 = (Button) findViewById(R.id.btnLRMSize15);
        Button lrm20 = (Button) findViewById(R.id.btnLRMSize20);

        lrm5.setBackgroundColor(android.R.drawable.btn_default);
        lrm10.setBackgroundColor(android.R.drawable.btn_default);
        lrm15.setBackgroundColor(android.R.drawable.btn_default);
        lrm20.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    public void streakSelection(View view) {
        CheckBox selected = (CheckBox) view;

        CheckBox angel = (CheckBox) findViewById(R.id.cbxLRMAngel);

        //if streak is selected
        if (selected.isChecked()) {
            altAmmoDeactivate(view);
            angel.setVisibility(View.VISIBLE);
        }
        //if streak is de-selected
        else {
            altAmmoActivate();
            angel.setChecked(false);
            angel.setVisibility(View.INVISIBLE);
        }
    }

    public void artemisNarcSelection(View view) {
        CheckBox selected = (CheckBox) view;

        CheckBox ecm = (CheckBox) findViewById(R.id.cbxLRMECM);

        //if artemis 4 or 5 or narc is selected
        if (selected.isChecked()) {
            altAmmoDeactivate(view);
            ecm.setVisibility(View.VISIBLE);
        }
        //if artemis 4 or 5 or narc is de-selected
        else {
            altAmmoActivate();
            ecm.setChecked(false);
            ecm.setVisibility(View.INVISIBLE);
        }
    }

    public void buttonManualClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue == -1) {
            highlightMistake();
        }
        else {
            updateClusterModifier();

            Intent intent = new Intent(this, ManualClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            intent.putExtra("weaponValue", weaponValue);
            intent.putExtra("clusterModifier", clusterModifier);
            intent.putExtra("streakMissiles", streakMissiles);
            startActivity(intent);
        }
    }

    public void buttonAutomaticClusterHits(View view) {
        if (targetFacing.isEmpty() || weaponValue == -1) {
            highlightMistake();
        }
        else {
            updateClusterModifier();

            Intent intent = new Intent(this, AutomaticClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            intent.putExtra("weaponValue", weaponValue);
            intent.putExtra("clusterModifier", clusterModifier);
            intent.putExtra("streakMissiles", streakMissiles);
            startActivity(intent);
        }
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnLRMFacingLeft);
            Button right = (Button) findViewById(R.id.btnLRMFacingRight);
            Button center = (Button) findViewById(R.id.btnLRMFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue == -1) {
            Button lrm5 = (Button) findViewById(R.id.btnLRMSize5);
            Button lrm10 = (Button) findViewById(R.id.btnLRMSize10);
            Button lrm15 = (Button) findViewById(R.id.btnLRMSize15);
            Button lrm20 = (Button) findViewById(R.id.btnLRMSize20);

            lrm5.setBackgroundColor(getColor(R.color.highlightMistake));
            lrm10.setBackgroundColor(getColor(R.color.highlightMistake));
            lrm15.setBackgroundColor(getColor(R.color.highlightMistake));
            lrm20.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }

}
