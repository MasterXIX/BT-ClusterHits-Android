package com.example.battletechclusterhits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class ManualClusterHitActivity extends AppCompatActivity {
    private int weaponDamage;
    private int weaponSize;
    private int weaponGroupSize;
    private int clusterModifier;
    private int facingIndex;

    private int hHits;
    private int ctHits;
    private int rtHits;
    private int ltHits;
    private int raHits;
    private int laHits;
    private int rlHits;
    private int llHits;

    private int hDam;
    private int ctDam;
    private int rtDam;
    private int ltDam;
    private int raDam;
    private int laDam;
    private int rlDam;
    private int llDam;

    private int criticals;
    private int damageDealt;

    private boolean streakMissiles;

    //Y axis is cluster roll - 2 in order to get the correct place in the array for the chart
    //for X axis, weapon sizes 2-7 are -2, sizes 9 and 10 are -3, size 12 is -4, size 15 is -6,
    //size 20 is -10, size 30 is -19, size 40 is -28
    int[][] clusterTable = {
            {1,1,1,1,2,2,3,3,4,5,6,10,12},
            {1,1,2,2,2,2,3,3,4,5,6,10,12},
            {1,1,2,2,3,3,4,4,5,6,9,12,18},
            {1,2,2,3,3,4,5,6,8,9,12,18,24},
            {1,2,2,3,4,4,5,6,8,9,12,18,24},
            {1,2,3,3,4,4,5,6,8,9,12,18,24},
            {2,2,3,3,4,4,5,6,8,9,12,18,24},
            {2,2,3,4,5,6,7,8,10,12,16,24,32},
            {2,3,3,4,5,6,7,8,10,12,16,24,32},
            {2,3,4,5,6,7,9,10,12,15,20,30,40},
            {2,3,4,5,6,7,9,10,12,15,20,30,40}
    };

    //The X axis is facing, so Left is 0, Center/Rear is 1, Right is 2
    //The Y axis is the location roll - 2
    String[][] hitLocationTable = {
            {"LT", "CT", "RT"},
            {"LL", "RA", "RL"},
            {"LA", "RA", "RA"},
            {"LA", "RL", "RA"},
            {"LL", "RT", "RL"},
            {"LT", "CT", "RT"},
            {"CT", "LT", "CT"},
            {"RT", "LL", "LT"},
            {"RA", "LA", "LA"},
            {"RL", "LA", "LL"},
            {"HD", "HD", "HD"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_cluster_hit);

        //get weapon, damage, facing and cluster modifier from the passed intent
        Intent intent = getIntent();

        String weapon = intent.getStringExtra("weapon");
        weaponSize = intent.getIntExtra("weaponValue", -1);
        String facing = intent.getStringExtra("facing");
        clusterModifier = intent.getIntExtra("clusterModifier", 0);
        streakMissiles = intent.getBooleanExtra("streakMissiles", false);


        setWeaponProperties(weapon, intent.getIntExtra("variableDamage", -1));
        setFacingProperty(facing);

        if (streakMissiles) {
            ((EditText) findViewById(R.id.etxtClusterRoll)).setText("12");
            ((EditText) findViewById(R.id.etxtClusterRoll)).setVisibility(View.INVISIBLE);
        }


        //print all intent based variables to the information log
        Log.i("CHA::weapon", weapon);
        Log.i("CHA::weapon size", String.valueOf(weaponSize));
        Log.i("CHA::weapon damage", String.valueOf(weaponDamage));
        Log.i("CHA::groupings", String.valueOf(weaponGroupSize));
        Log.i("CHA::cluster modifier", String.valueOf(clusterModifier));
        Log.i("CHA::facing", facing);
        Log.i("CHA::facingIndex", String.valueOf(facingIndex));
    }



    public void rollClusterHit(View view) {
        EditText rollVal = (EditText) findViewById(R.id.etxtClusterRoll);

        if (!rollVal.getText().toString().isEmpty()) {
            int roll = Integer.parseInt(rollVal.getText().toString());

            if (!(checkClusterRoll(roll))) {
                rollVal.setText(null);
                rollVal.setHint("Roll must be between 2 and 12");
            } else {
                roll = addClusterModifier(roll, clusterModifier);
                int hits = checkClusterTable(roll, weaponSize);

                calculateDamage((hits * weaponDamage));
                populateDamageOnChart();
            }
        }
        else {
            rollVal.setHint("please enter value");
        }
    }



    private void calculateDamage(int totalDamage) {
        resetGlobalDamage();
        int runningTotal = 0;
        int lastCluster;

        while (runningTotal != totalDamage ) {
            if ((runningTotal + weaponGroupSize) >= totalDamage) {
                lastCluster = (totalDamage) - runningTotal;
                applyDamageToLocation(lastCluster);
                runningTotal = runningTotal + lastCluster;
            }
            else {
                applyDamageToLocation(weaponGroupSize);
                runningTotal = runningTotal + weaponGroupSize;
            }
        }

        damageDealt = totalDamage;
    }

    private void applyDamageToLocation(int damage) {
        int locationRoll = randomLocationRoll();
        String hitLocation = checkLocationTable(locationRoll);

        //If through armor critical is rolled, add 1 to number of crits
        if (locationRoll == 2) {
            criticals = criticals + 1;
        }

        //Based on the location rolled and returned from the chart, add the damage and increment
        //the number of hits in that location
        if (hitLocation.matches("CT") ) {
            ctDam = ctDam + damage;
            ctHits = ctHits + 1;
        }
        else if (hitLocation.matches("HD") ) {
            hDam = hDam + damage;
            hHits = hHits + 1;
        }
        else if (hitLocation.matches("LT") ) {
            ltDam = ltDam + damage;
            ltHits = ltHits + 1;
        }
        else if (hitLocation.matches("RT") ) {
            rtDam = rtDam + damage;
            rtHits = rtHits + 1;
        }
        else if (hitLocation.matches("LA") ) {
            laDam = laDam + damage;
            laHits = laHits + 1;
        }
        else if (hitLocation.matches("RA") ) {
            raDam = raDam + damage;
            raHits = raHits + 1;
        }
        else if (hitLocation.matches("LL") ) {
            llDam = llDam + damage;
            llHits = llHits + 1;
        }
        else if (hitLocation.matches("RL") ) {
            rlDam = rlDam + damage;
            rlHits = rlHits + 1;
        }
    }

    private void resetGlobalDamage() {
        hHits = 0;
        ctHits = 0;
        rtHits = 0;
        ltHits = 0;
        raHits = 0;
        laHits = 0;
        rlHits = 0;
        llHits = 0;

        hDam = 0;
        ctDam = 0;
        rtDam = 0;
        ltDam = 0;
        raDam = 0;
        laDam = 0;
        rlDam = 0;
        llDam = 0;

        criticals = 0;
    }

    private void populateDamageOnChart() {
        TextView viewer;

        viewer = (TextView) findViewById(R.id.txtCrit);
        viewer.setText("Through Armor Criticals: ".concat(String.valueOf(criticals)));

        viewer = (TextView) findViewById(R.id.txtHeadHits);
        viewer.setText("Hits: ".concat(String.valueOf(hHits)));

        viewer = (TextView) findViewById(R.id.txtHeadDamage);
        viewer.setText("Damage: ".concat(String.valueOf(hDam)));

        viewer = (TextView) findViewById(R.id.txtCTHits);
        viewer.setText("Hits: ".concat(String.valueOf(ctHits)));

        viewer = (TextView) findViewById(R.id.txtCTDamage);
        viewer.setText("Damage: ".concat(String.valueOf(ctDam)));

        viewer = (TextView) findViewById(R.id.txtRTHits);
        viewer.setText("Hits: ".concat(String.valueOf(rtHits)));

        viewer = (TextView) findViewById(R.id.txtRTDamage);
        viewer.setText("Damage: ".concat(String.valueOf(rtDam)));

        viewer = (TextView) findViewById(R.id.txtLTHits);
        viewer.setText("Hits: ".concat(String.valueOf(ltHits)));

        viewer = (TextView) findViewById(R.id.txtLTDamage);
        viewer.setText("Damage: ".concat(String.valueOf(ltDam)));

        viewer = (TextView) findViewById(R.id.txtRAHits);
        viewer.setText("Hits: ".concat(String.valueOf(raHits)));

        viewer = (TextView) findViewById(R.id.txtRADamage);
        viewer.setText("Damage: ".concat(String.valueOf(raDam)));

        viewer = (TextView) findViewById(R.id.txtLAHits);
        viewer.setText("Hits: ".concat(String.valueOf(laHits)));

        viewer = (TextView) findViewById(R.id.txtLADamage);
        viewer.setText("Damage: ".concat(String.valueOf(laDam)));

        viewer = (TextView) findViewById(R.id.txtRLHits);
        viewer.setText("Hits: ".concat(String.valueOf(rlHits)));

        viewer = (TextView) findViewById(R.id.txtRLDamage);
        viewer.setText("Damage: ".concat(String.valueOf(rlDam)));

        viewer = (TextView) findViewById(R.id.txtLLHits);
        viewer.setText("Hits: ".concat(String.valueOf(llHits)));

        viewer = (TextView) findViewById(R.id.txtLLDamage);
        viewer.setText("Damage: ".concat(String.valueOf(llDam)));

        viewer = (TextView) findViewById(R.id.txtTotalDamage);
        viewer.setText("Total Damage Dealt: ".concat(String.valueOf(damageDealt)));
    }



    private int randomLocationRoll() {
        Random dice1 = new Random();
        Random dice2 = new Random();
        int die1 = dice1.nextInt(7);
        int die2 = dice2.nextInt(7);

        while (die1 == 0) {
            die1 = dice1.nextInt(7);
        }
        while (die2 == 0) {
            die2 = dice2.nextInt(7);
        }
        return (die1 + die2);
    }

    private boolean checkClusterRoll(int clusterRoll) {
        if (clusterRoll < 2 || clusterRoll > 12) {
            return false;
        }
        else {
            return true;
        }
    }

    private int checkClusterTable(int clusterRoll, int weaponSize) {
        return clusterTable[prepRollsForTables(clusterRoll)][prepWeaponForTable(weaponSize)];
    }

    private String checkLocationTable(int roll) {
        return hitLocationTable[prepRollsForTables(roll)][facingIndex];
    }

    private int addClusterModifier(int roll, int modifier) {
        int modified = roll + modifier;

        if (modified > 12) {
            modified = 12;
        }
        else if (modified < 2) {
            modified = 2;
        }

        return modified;
    }



    private int prepRollsForTables(int roll) {
        return (roll - 2);
    }

    private int prepWeaponForTable(int weaponsize) {
        int tableVal;

        if (weaponsize >= 2 && weaponsize <= 7) {
            tableVal = weaponsize - 2;
        }
        else if (weaponsize < 12) {
            tableVal = weaponsize - 3;
        }
        else if (weaponsize == 12) {
            tableVal = weaponsize - 4;
        }
        else if (weaponsize == 15) {
            tableVal = weaponsize - 6;
        }
        else if (weaponsize == 20) {
            tableVal = weaponsize - 10;
        }
        else if (weaponsize == 30) {
            tableVal = weaponsize - 19;
        }
        else if (weaponsize == 40) {
            tableVal = weaponsize - 28;
        }
        else {
            tableVal = -1;
        }

        return tableVal;
    }



    private void setWeaponProperties(String weapon, int variableDamage) {

        //Certain weapons have variable damage, so set pass the stat through when necessary
        if (weapon.matches("ATM") || weapon.matches("UAC") || weapon.matches("RAC") ) {
            setClusterProperties(weapon, variableDamage );
        }
        else {
            setClusterProperties(weapon, 0);
        }
    }

    private void setClusterProperties(String weapon, int variableDamage) {

            if (weapon.matches("LRM") ) {
                weaponDamage = 1;
                weaponGroupSize = 5;
            }
            else if (weapon.matches("SRM") ) {
                weaponDamage = 2;
                weaponGroupSize = 2;
            }
            else if (weapon.matches("MRM") ) {
                weaponDamage = 1;
                weaponGroupSize = 5;
            }
            else if (weapon.matches("ATM") ) {
                weaponDamage = variableDamage;
                weaponGroupSize = 5;
            }
            else if (weapon.matches("LBX") ) {
                weaponDamage = 1;
                weaponGroupSize = 1;
            }
            else if (weapon.matches("UAC") ) {
                weaponDamage = variableDamage;
                weaponGroupSize = variableDamage;
            }
            else if (weapon.matches("RAC") ) {
                weaponDamage = variableDamage;
                weaponGroupSize = variableDamage;
            }
            else if (weapon.matches("SBGauss") ) {
                weaponDamage = 1;
                weaponGroupSize = 1;
            }
            else if (weapon.matches("HAG") ) {
                weaponDamage = 1;
                weaponGroupSize = 5;
            }
    }

    private void setFacingProperty(String facing) {
        if (facing.matches("L") ) {
            facingIndex = 0;
        }
        else if (facing.matches("R") ) {
            facingIndex = 2;
        }
        else {
            facingIndex = 1;
        }
    }

}
