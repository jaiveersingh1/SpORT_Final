package com.example.jsboygenius.sport_bt_test;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static com.example.jsboygenius.sport_bt_test.AdminActivity.btSend;
import static com.example.jsboygenius.sport_bt_test.AdminActivity.btSocket;

public class InstructionsActivity extends AppCompatActivity {
    private String selectedSport;
    private int selectedLevel;
    private TextView mCurrentSport;
    private TextView mCurrentLevel;
    private TextView mModeInstructions;
    private ImageView mModeInstructionsImg;
    private Button mBtnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedSport = getIntent().getStringExtra("SELECTED_SPORT");
        selectedLevel = getIntent().getIntExtra("SELECTED_LEVEL", 1);

        mCurrentSport = (TextView) findViewById(R.id.currentSport);
        mCurrentLevel = (TextView) findViewById(R.id.currentLevel);
        mModeInstructions = (TextView) findViewById(R.id.modeInstructions);
        mModeInstructionsImg = (ImageView) findViewById(R.id.modeInstructionsImg);
        mBtnBack = (Button) findViewById(R.id.backBtn);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSend("BK: ", getApplicationContext());
                finish();
            }
        });
        mCurrentSport.setText(StringUtils.capitalize(selectedSport));
        if("basketball".equals(selectedSport))
        {
            mCurrentSport.setTextColor(0xAAFF9800);
        }
        else if ("soccer".equals(selectedSport))
        {
            mCurrentSport.setTextColor(0xAA2196F3);
        }

        switch(selectedLevel)
        {
            case 1:
                mCurrentLevel.setText(getResources().getText(R.string.level_one));
                mCurrentLevel.setTextColor(getResources().getColor(R.color.easy));
                mModeInstructions.setText(getResources().getText(R.string.push_instructions));
                mModeInstructionsImg.setImageResource(R.drawable.push);
                break;
            case 2:
                mCurrentLevel.setText(getResources().getText(R.string.level_two));
                mCurrentLevel.setTextColor(getResources().getColor(R.color.medium));
                if("soccer".equals(selectedSport))
                {
                    mModeInstructions.setText(getResources().getText(R.string.pan_instructions));
                    mModeInstructionsImg.setImageResource(R.drawable.pan);
                }
                else
                {
                    mModeInstructions.setText(getResources().getText(R.string.tilt_instructions));
                    mModeInstructionsImg.setImageResource(R.drawable.tilt);
                }
                break;
            case 3:
                mCurrentLevel.setText(getResources().getText(R.string.level_three));
                mCurrentLevel.setTextColor(getResources().getColor(R.color.hard));
                mModeInstructions.setText(getResources().getText(R.string.full_instructions));
                mModeInstructionsImg.setImageResource(R.drawable.full);
                break;
        }
        btSend("SP: "+ selectedSport.substring(0, 4) + selectedLevel, getApplicationContext());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                btSend("BK:", getApplicationContext());
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
