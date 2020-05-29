package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SIlverBulletGaussEntryActivity extends AppCompatActivity {
    private String weaponType = "SBGauss";
    private String targetFacing = "";
    private int weaponValue = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silver_bullet_gauss_entry);
    }

    public void buttonFacingSelection(View view) {
        Button selected = (Button) view;

        Button left = (Button) findViewById(R.id.btnSBGFacingLeft);
        Button center = (Button) findViewById(R.id.btnSBGFacingCenter);
        Button right = (Button) findViewById(R.id.btnSBGFacingRight);

        left.setBackgroundColor(android.R.drawable.btn_default);
        center.setBackgroundColor(android.R.drawable.btn_default);
        right.setBackgroundColor(android.R.drawable.btn_default);

        selected.setBackgroundColor(getColor(R.color.Selected));
        targetFacing = selected.getText().toString();
    }

    private void highlightMistake() {
        if (targetFacing.isEmpty()) {
            Button left = (Button) findViewById(R.id.btnSBGFacingLeft);
            Button right = (Button) findViewById(R.id.btnSBGFacingRight);
            Button center = (Button) findViewById(R.id.btnSBGFacingCenter);

            left.setBackgroundColor(getColor(R.color.highlightMistake));
            right.setBackgroundColor(getColor(R.color.highlightMistake));
            center.setBackgroundColor(getColor(R.color.highlightMistake));
        }
    }

    public void buttonManualClusterHits(View view) {
        if (targetFacing.isEmpty()) {
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
        if (targetFacing.isEmpty()) {
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
}
