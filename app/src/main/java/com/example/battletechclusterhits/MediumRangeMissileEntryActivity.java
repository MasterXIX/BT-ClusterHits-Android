package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MediumRangeMissileEntryActivity extends AppCompatActivity {
    private String weaponType = "MRM";
    private String targetFacing = "";
    private int weaponValue = -1;
    private int clusterModifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_range_missile_entry);
    }

    private void updateClusterModifier() {
        clusterModifier = 0;

        CheckBox apollo = (CheckBox) findViewById(R.id.cbxMRMApollo);
        CheckBox ams = (CheckBox) findViewById(R.id.cbxMRMAMS);
        CheckBox ecm = (CheckBox) findViewById(R.id.cbxMRMECM);

        if (apollo.isChecked() && !ecm.isChecked()) {
            clusterModifier = clusterModifier - 1;
        }

        if (ams.isChecked()) {
            clusterModifier = clusterModifier - 4;
        }
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("MRM 10")) {
            return 10;
        }
        else if(weapon.matches("MRM 20")) {
            return 20;
        }
        else if(weapon.matches("MRM 30")) {
            return 30;
        }
        else if (weapon.matches("MRM 40")) {
            return 40;
        }
        else {
            return -1;
        }

    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnMRMFacingLeft);
        Button center = (Button) findViewById(R.id.btnMRMFacingCenter);
        Button right = (Button) findViewById(R.id.btnMRMFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button  mrm10 = (Button) findViewById(R.id.btnMRMSize10);
        Button  mrm20 = (Button) findViewById(R.id.btnMRMSize20);
        Button  mrm30 = (Button) findViewById(R.id.btnMRMSize30);
        Button  mrm40 = (Button) findViewById(R.id.btnMRMSize40);

        mrm10.setBackgroundColor(android.R.drawable.btn_default);
        mrm20.setBackgroundColor(android.R.drawable.btn_default);
        mrm30.setBackgroundColor(android.R.drawable.btn_default);
        mrm40.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    public void checkboxApolloSelection(View view) {
        CheckBox apollo = (CheckBox) findViewById(R.id.cbxMRMApollo);
        CheckBox ecm = (CheckBox) findViewById(R.id.cbxMRMECM);

        if (apollo.isChecked()) {
            ecm.setVisibility(ecm.VISIBLE);
        }
        else {
            ecm.setVisibility(ecm.INVISIBLE);
            ecm.setChecked(false);
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
            startActivity(intent);
        }
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnMRMFacingLeft);
            Button right = (Button) findViewById(R.id.btnMRMFacingRight);
            Button center = (Button) findViewById(R.id.btnMRMFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue == -1) {
            Button mrm10 = (Button) findViewById(R.id.btnMRMSize10);
            Button mrm20 = (Button) findViewById(R.id.btnMRMSize20);
            Button mrm30 = (Button) findViewById(R.id.btnMRMSize30);
            Button mrm40 = (Button) findViewById(R.id.btnMRMSize40);

            mrm10.setBackgroundColor(getColor(R.color.highlightMistake));
            mrm20.setBackgroundColor(getColor(R.color.highlightMistake));
            mrm30.setBackgroundColor(getColor(R.color.highlightMistake));
            mrm40.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }

}
