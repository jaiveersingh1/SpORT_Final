package com.example.jsboygenius.sport_bt_test;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.apache.commons.lang3.StringUtils;

public class LevelSelectActivity extends AppCompatActivity {
    private String selectedSport;
    private TextView mCurrentSport;
    private ImageView mCurrentSportIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedSport = getIntent().getStringExtra("SELECTED_SPORT");

        mCurrentSport = (TextView) findViewById(R.id.currentSport);
        mCurrentSportIcon = (ImageView) findViewById(R.id.currentSportIcon);

        mCurrentSport.setText(StringUtils.capitalize(selectedSport));

        if("basketball".equals(selectedSport))
        {
            mCurrentSport.setTextColor(0xAAFF9800);
            mCurrentSportIcon.setImageResource(R.drawable.basketball_icon);
        }
        else if ("soccer".equals(selectedSport))
        {
            mCurrentSport.setTextColor(0xAA2196F3);
            mCurrentSportIcon.setImageResource(R.drawable.soccer_icon);
        }
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
}
