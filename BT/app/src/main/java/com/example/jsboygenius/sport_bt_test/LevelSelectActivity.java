package com.example.jsboygenius.sport_bt_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.apache.commons.lang3.StringUtils;

public class LevelSelectActivity extends AppCompatActivity {
    private String selectedSport;
    private int selectedLevel;
    private TextView mCurrentSport;
    private ImageView mCurrentSportIcon;
    private Button mBtLevel1;
    private Button mBtLevel2;
    private Button mBtLevel3;
    private Button mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedSport = getIntent().getStringExtra("SELECTED_SPORT"); //So now we have to grab that P.S.

        mCurrentSport = (TextView) findViewById(R.id.currentSport);
        mCurrentSportIcon = (ImageView) findViewById(R.id.currentSportIcon);
        mBtLevel1 = (Button) findViewById(R.id.levelOneBtn);
        mBtLevel2 = (Button) findViewById(R.id.levelTwoBtn);
        mBtLevel3 = (Button) findViewById(R.id.levelThreeBtn);

        mCurrentSport.setText(StringUtils.capitalize(selectedSport));

        if("basketball".equals(selectedSport))
        {
            mCurrentSport.setTextColor(0xAAFF9800); //Because it's the little things that matter...
            mCurrentSportIcon.setImageResource(R.drawable.basketball_icon); //Change text color + images to match current sport
        }
        else if ("soccer".equals(selectedSport))
        {
            mCurrentSport.setTextColor(0xAA2196F3);
            mCurrentSportIcon.setImageResource(R.drawable.soccer_icon);
        }

        mBtLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedLevel = 1;
                launchInstructions();
            }
        });
        mBtLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedLevel = 2;
                launchInstructions();
            }
        });
        mBtLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedLevel = 3;
                launchInstructions();
            }
        });
        mBtnBack = (Button) findViewById(R.id.backBtn);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void launchInstructions() {
        Intent intent = new Intent(this, InstructionsActivity.class); //headed to instructions now
        intent.putExtra("SELECTED_SPORT", selectedSport);
        intent.putExtra("SELECTED_LEVEL", selectedLevel); //Like a game of telephone, we alter the delivery message a little bit and then pass it on
        startActivity(intent);
    }
}
