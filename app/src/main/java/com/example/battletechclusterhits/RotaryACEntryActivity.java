package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RotaryACEntryActivity extends AppCompatActivity {
    private String weaponType = "RAC";
    private String targetFacing = "";
    private int weaponValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotary_acentry);
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnRACFacingLeft);
            Button right = (Button) findViewById(R.id.btnRACFacingRight);
            Button center = (Button) findViewById(R.id.btnRACFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }


        if(weaponValue <= 2) {
            Button two = (Button) findViewById(R.id.btnRACSize2);
            Button five = (Button) findViewById(R.id.btnRACSize5);

            two.setBackgroundColor(getColor(R.color.highlightMistake));
            five.setBackgroundColor(getColor(R.color.highlightMistake));
        }



        EditText racRounds = (EditText) findViewById(R.id.etxtRACNumFired);


        if (racRounds.getText().toString().isEmpty()) {
            racRounds.setBackgroundColor(getColor(R.color.highlightMistake));
            racRounds.setHint("please enter a value 1 - 6");
        }
        else {
            int rounds = Integer.parseInt(racRounds.getText().toString());

            if (rounds < 1 || rounds > 6) {
                racRounds.setBackgroundColor(getColor(R.color.highlightMistake));
                racRounds.setHint("please enter a value 1 - 6");
                racRounds.setText("");
            }
        }
    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnRACFacingLeft);
        Button center = (Button) findViewById(R.id.btnRACFacingCenter);
        Button right = (Button) findViewById(R.id.btnRACFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    public void buttonWeaponSelection(View view) {
        Button selected = (Button) view;

        Button RAC2 = (Button) findViewById(R.id.btnRACSize2);
        Button RAC5 = (Button) findViewById(R.id.btnRACSize5);

        RAC2.setBackgroundColor(android.R.drawable.btn_default);
        RAC5.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        weaponValue = getWeaponValue(selected.getText().toString());
    }

    private int getWeaponValue(String weapon) {
        if (weapon.matches("RAC/2")) {
            return 2;
        }
        else if(weapon.matches("RAC/5")) {
            return 5;
        }
        else {
            return -1;
        }

    }

    private boolean checkRACRounds(String rounds) {
        //this method is called from an if condition where true causes error handling to run,
        //thus we want the negative condition to return true and the good condition to return false
        if (!rounds.isEmpty()) {
            int checkRoundsFired = Integer.parseInt(rounds);
            if (checkRoundsFired >= 1 && checkRoundsFired <= 6) {
                return false;
            }
        }
        return true;
    }

    public void buttonManualClusterHits(View view) {
        EditText racNumFired = (EditText) findViewById(R.id.etxtRACNumFired);
        String roundsFired = racNumFired.getText().toString();

        if (targetFacing.isEmpty() || weaponValue < 2 || checkRACRounds(roundsFired)) {
            highlightMistake();
        }
        else {
            Intent intent = new Intent(this, ManualClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            //weaponValue is used for most weapons to determine which cluster column to roll on,
            //so use 2 for all UAC weapons
            intent.putExtra("weaponValue", Integer.parseInt(roundsFired));
            intent.putExtra("variableDamage", weaponValue);
            startActivity(intent);
        }
    }

    public void buttonAutomaticClusterHits(View view) {
        EditText racNumFired = (EditText) findViewById(R.id.etxtRACNumFired);
        String roundsFired = racNumFired.getText().toString();

        if (targetFacing.isEmpty() || weaponValue < 2 || checkRACRounds(roundsFired)) {
            highlightMistake();
        }
        else {
            Intent intent = new Intent(this, AutomaticClusterHitActivity.class);
            intent.putExtra("weapon", weaponType);
            intent.putExtra("facing", targetFacing);
            //weaponValue is used for most weapons to determine which cluster column to roll on,
            //so use 2 for all UAC weapons
            intent.putExtra("weaponValue", Integer.parseInt(roundsFired));
            intent.putExtra("variableDamage", weaponValue);
            startActivity(intent);
        }
    }
}
