package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ShortRangeMissileEntryActivity extends AppCompatActivity {
    private String weaponType = "SRM";
    private String targetFacing = "";
    private int weaponValue = -1;
    private int clusterModifier;
    private boolean streakMissiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_range_missile_entry);
    }

    private void updateClusterModifier() {
        clusterModifier = 0;
        streakMissiles = false;

        CheckBox art4 = (CheckBox) findViewById(R.id.cbxSRMArtemis4);
        CheckBox art5 = (CheckBox) findViewById(R.id.cbxSRMArtemis5);
        CheckBox narc = (CheckBox) findViewById(R.id.cbxSRMNarc);
        CheckBox streak = (CheckBox) findViewById(R.id.cbxSRMStreak);
        CheckBox ams = (CheckBox) findViewById(R.id.cbxSRMAMS);

        CheckBox ecm = (CheckBox) findViewById(R.id.cbxSRMECM);
        CheckBox angel = (CheckBox) findViewById(R.id.cbxSRMAngel);

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
        ((CheckBox) findViewById(R.id.cbxSRMArtemis4)).setVisibility(View.INVISIBLE);
        ((CheckBox) findViewById(R.id.cbxSRMArtemis5)).setVisibility(View.INVISIBLE);
        ((CheckBox) findViewById(R.id.cbxSRMNarc)).setVisibility(View.INVISIBLE);
        ((CheckBox) findViewById(R.id.cbxSRMStreak)).setVisibility(View.INVISIBLE);

        view.setVisibility(View.VISIBLE);
    }

    private void altAmmoActivate() {
        ((CheckBox) findViewById(R.id.cbxSRMArtemis4)).setVisibility(View.VISIBLE);
        ((CheckBox) findViewById(R.id.cbxSRMArtemis5)).setVisibility(View.VISIBLE);
        ((CheckBox) findViewById(R.id.cbxSRMNarc)).setVisibility(View.VISIBLE);
        ((CheckBox) findViewById(R.id.cbxSRMStreak)).setVisibility(View.VISIBLE);
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("SRM 2")) {
            return 2;
        }
        else if(weapon.matches("SRM 4")) {
            return 4;
        }
        else if(weapon.matches("SRM 6")) {
            return 6;
        }
        else {
            return -1;
        }
    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnSRMFacingLeft);
        Button center = (Button) findViewById(R.id.btnSRMFacingCenter);
        Button right = (Button) findViewById(R.id.btnSRMFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button  srm2 = (Button) findViewById(R.id.btnSRMSize2);
        Button  srm4 = (Button) findViewById(R.id.btnSRMSize4);
        Button  srm6 = (Button) findViewById(R.id.btnSRMSize6);

        srm2.setBackgroundColor(android.R.drawable.btn_default);
        srm4.setBackgroundColor(android.R.drawable.btn_default);
        srm6.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    public void streakSelection(View view) {
        CheckBox selected = (CheckBox) view;

        CheckBox angel = (CheckBox) findViewById(R.id.cbxSRMAngel);

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

        CheckBox ecm = (CheckBox) findViewById(R.id.cbxSRMECM);

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
            Button left = (Button) findViewById(R.id.btnSRMFacingLeft);
            Button right = (Button) findViewById(R.id.btnSRMFacingRight);
            Button center = (Button) findViewById(R.id.btnSRMFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue == -1) {
            Button srm2 = (Button) findViewById(R.id.btnSRMSize2);
            Button srm4 = (Button) findViewById(R.id.btnSRMSize4);
            Button srm6 = (Button) findViewById(R.id.btnSRMSize6);

            srm2.setBackgroundColor(getColor(R.color.highlightMistake));
            srm4.setBackgroundColor(getColor(R.color.highlightMistake));
            srm6.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }

}
