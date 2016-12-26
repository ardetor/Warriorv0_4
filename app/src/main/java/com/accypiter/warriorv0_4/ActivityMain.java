package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityMain extends AppCompatActivity {

    public static boolean first_time = true;
    protected final int CODE_START_NEW_GAME = 0;
    protected final int CODE_OK_TO_AUTOSTART = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //Check if first time on this screen
        if(first_time) {
            first_time = false;
            //If user opted to in Preferences, skip title screen and go to ActivitySummary
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean skip_title_screen = sharedPreferences.getBoolean("preference_skip_title_screen", false);

            if (skip_title_screen) {
                if (SaveGame.load(getBaseContext())){ //if load is successful
                    startGame();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_norefresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            Intent openSettingsIntent = new Intent(this, ActivityPreferences.class);
            startActivity(openSettingsIntent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //DO NOT SAVE GAME TO FILE. Only do that within the game.
    }

    public void buttonLoadGame(View view){
        //Load game from file and proceed to ActivitySummary.
        if (SaveGame.load(getBaseContext())){ //Load successful: start game
            startGame();
        } else { //Load unsuccessful: exception thrown
            TextView infoText = (TextView) findViewById(R.id.activity_main_infotext);
            infoText.setText(R.string.activity_main_loadgame_failure);
        }
    }

    public void buttonNewGame(View view){
        //Create new game: launch ActivityNewGame. This is to make it harder to accidentally overwrite an old game.
        //First clear FileNotFoundException text from infotext.
        TextView infoText = (TextView) findViewById(R.id.activity_main_infotext);
        infoText.setText("");

        //Temporary startActivity, to be replaced by forResult
        Intent openNewGameActivityIntent = new Intent(this, ActivityNewGame.class);
        startActivityForResult(openNewGameActivityIntent, CODE_START_NEW_GAME);
    }

    public void buttonAbout(View view){
        //Launch ActivityAbout.
        //First clear FileNotFoundException text from infotext.
        TextView infoText = (TextView) findViewById(R.id.activity_main_infotext);
        infoText.setText("");
        Intent openAbout = new Intent(this, ActivityAbout.class);
        startActivity(openAbout);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CODE_START_NEW_GAME){  // requestCode 0: Start New Game
            if (resultCode == RESULT_CANCELED){
                //Do nothing
            } else if (resultCode == RESULT_OK){
                //Launch ActivitySummary
                startNewGame();
            }

        } else if (requestCode == CODE_OK_TO_AUTOSTART){
            if (resultCode == RESULT_CANCELED){
                //Do nothing
            } else if (resultCode == RESULT_OK){
                //Autostart
                startGame();
            }
        }
    }

    protected void startGame(){
        //Start ActivitySummary OR ActivityFight
        //TODO: ADD CHEAT DETECTION
        if (!SaveGame.current.in_fight) {
            startActivitySummary();
        } else {
            startActivityFight();
        }
    }

    protected void startActivitySummary(){
        startActivityForResult(new Intent(this, ActivitySummary.class), CODE_OK_TO_AUTOSTART);
    }

    protected void startActivityFight(){
        startActivityForResult(new Intent(this, ActivityFight.class), CODE_OK_TO_AUTOSTART);
    }

    protected void startNewGame(){
        //Later, replace with startActivityForResult to the tutorial, which when finished leads back here and starts game.
        startGame();
    }

}

