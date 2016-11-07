package com.lbirk.galgespil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button gameButton;
    Button helpButton;
    Button settingsButton;
    Button startStatsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameButton = (Button) findViewById(R.id.startGameButton);
        helpButton = (Button) findViewById(R.id.startHelpButton);
        settingsButton = (Button) findViewById(R.id.startSettingsButton);
        startStatsButton = (Button) findViewById(R.id.startStatsButton);
        gameButton.setOnClickListener(MainActivity.this);
        helpButton.setOnClickListener(MainActivity.this);
        settingsButton.setOnClickListener(MainActivity.this);
        startStatsButton.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.startGameButton:
                Intent gameIntent = new Intent(this,GameActivity.class);
                startActivity(gameIntent);
                break;
            case R.id.startHelpButton:
                Intent helpIntent = new Intent(this,HelpActivity.class);
                startActivity(helpIntent);
                break;
            case R.id.startSettingsButton:
                Intent SettingsIntent = new Intent(this,SettingsActivity.class);
                startActivity(SettingsIntent);
                break;
            case R.id.startStatsButton:
                Intent StatsIntent = new Intent(this,StatsActivity.class);
                startActivity(StatsIntent);
                break;
        }

    }
}
