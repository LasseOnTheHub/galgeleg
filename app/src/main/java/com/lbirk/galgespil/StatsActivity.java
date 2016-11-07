package com.lbirk.galgespil;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class StatsActivity extends AppCompatActivity {

    TextView gameCountView;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        gameCountView = (TextView) findViewById(R.id.gamesCountTextView);

        if (prefs.contains("gameCount"))
        {
            String count =  String.valueOf(prefs.getInt("gameCount",0));
            gameCountView.setText(count);
        }
        else
        {
            prefs.edit().putInt("gameCount",0).apply();
            gameCountView.setText(prefs.getInt("gameCount",0));
        }

    }
}
