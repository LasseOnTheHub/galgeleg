package com.lbirk.galgespil;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import Logic.Galgelogik;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button guessButton;
    EditText wordEdittext;
    TextView statustextView;
    TextView WordtextView;
    ImageView galgeimageView;
    TextView guessedWord;
    Galgelogik galgelogik;
    SharedPreferences prefs;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        guessButton = (Button) findViewById(R.id.guessButton);
        wordEdittext = (EditText) findViewById(R.id.guessWordeditText);
        statustextView = (TextView) findViewById(R.id.statustextView);
        WordtextView = (TextView) findViewById(R.id.WordtextView);
        galgeimageView = (ImageView) findViewById(R.id.galgeimageView);
        guessedWord = (TextView) findViewById(R.id.guessedWordtextView);
        guessButton.setOnClickListener(GameActivity.this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        galgelogik = new Galgelogik();
        getAsyncWords();
        WordtextView.setText(galgelogik.getSynligtOrd());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.guessButton:
                galgelogik.gætBogstav(wordEdittext.getText().toString().toLowerCase());
                wordEdittext.setText("");
                if (galgelogik.erSidsteBogstavKorrekt()) {
                    statustextView.setText("Du gættede korrekt!");
                    WordtextView.setText(galgelogik.getSynligtOrd());

                    if (galgelogik.erSpilletVundet()) {
                        statustextView.setText("Du har vundet spillet!");
                        restartGameDialog("Du vandt");
                    }
                    updateGuessedLetters();
                } else {
                    statustextView.setText("Du har nu gættet forkert " + galgelogik.getAntalForkerteBogstaver() + " gange");

                    updateGuessedLetters();
                    if (galgelogik.erSpilletTabt()) {
                        statustextView.setText("Du har nu tabt spillet!");
                        restartGameDialog("Du tabte");
                    } else {
                        int drawableId = getResources().getIdentifier("forkert" + galgelogik.getAntalForkerteBogstaver(), "drawable", getPackageName());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            galgeimageView.setImageDrawable(getResources().getDrawable(drawableId, getApplicationContext().getTheme()));
                        } else {
                            galgeimageView.setImageDrawable(getResources().getDrawable(drawableId));
                        }
                    }

                }
                break;
        }
    }


    private void updateGuessedLetters() {
        String usedWords = "Gættede bogstaver: ";
        for (String s : galgelogik.getBrugteBogstaver()) {
            usedWords += s + ", ";
        }
        guessedWord.setText(usedWords);
    }

    private void restartGameDialog(String status) {
        if (prefs.contains("gameCount"))
        {
            int count = prefs.getInt("gameCount",0);
            count++;
            prefs.edit().putInt("gameCount",count).apply();
        }
        else
        {
            prefs.edit().putInt("gameCount",1).apply();
        }

                new AlertDialog.Builder(this)
                .setTitle(status)
                .setMessage("Det korrekte ord: "+ galgelogik.getOrdet() + "\nSpil igen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


    public void getAsyncWords() {
        statustextView.setText("Henter ord fra DRs server....");
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    galgelogik.hentOrdFraDr();
                    return "Ordene blev korrekt hentet fra DR's server";
                } catch (Exception e) {
                    System.out.print(StackTraceElement.class);
                    return "Ordene blev ikke hentet korrekt:";
                }
            }

            @Override
            protected void onPostExecute(Object resultat) {
                statustextView.setText("resultat: \n" + resultat);
            }
        }.execute();
    }

}

