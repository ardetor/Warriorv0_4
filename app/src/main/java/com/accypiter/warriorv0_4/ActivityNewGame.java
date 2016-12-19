package com.accypiter.warriorv0_4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ActivityNewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_new_game_toolbar);
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_about, menu);
        return true;
    }

    public void cancelNewGame(View view){
        //New game cancelled; return RESULT_CANCELLED and end activity.
        Intent emptyIntent = new Intent();
        setResult(RESULT_CANCELED, emptyIntent);
        finish();
    }

    public void confirmNewGame(View view){
        //Before allowing new game, check if savegame data currently exists
        boolean save_data_exists = SaveGame.load(getBaseContext());

        if (!save_data_exists){
            //Make new save game
            createNewSave();

        } else {  //SAVE DATA EXISTS!
            //Confirm if user really wants to overwrite data.
            //Start new ActivityNewGameConfirm
            Intent confirmNewGameIntent = new Intent(this, ActivityNewGameConfirm.class);
            startActivityForResult(confirmNewGameIntent, 1);
        }
    }


    protected void createNewSave(){
        //Create new save file in SaveGame.current
        SaveGame.startNewGame(this);

        //Done, savegame written to SaveGame.current and to file. Finish activity and send Result_OK.
        Intent emptyIntent = new Intent();
        setResult(RESULT_OK, emptyIntent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){  //requestCode 1: Confirm Start New Game
            if (resultCode == RESULT_CANCELED){
                //Do nothing
            }else if (resultCode == RESULT_OK){
                //Overwrite save file and start ActivitySummary
                createNewSave();
            }
        }
    }
}
