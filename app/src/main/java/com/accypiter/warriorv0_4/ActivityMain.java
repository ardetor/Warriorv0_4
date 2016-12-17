package com.accypiter.warriorv0_4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ActivityMain extends AppCompatActivity {

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause(){
        super.onPause();
        //DO NOT SAVE GAME TO FILE. Only do that within the game.
    }

    public void buttonLoadGame(View view){
        //Load game from file and proceed to ActivitySummary.
        try {
            FileInputStream fileIn = openFileInput(SaveGame.file_name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveGame.current = (SaveGame) in.readObject();
            in.close();
            fileIn.close();
        }catch(FileNotFoundException f){
            TextView infoText = (TextView) findViewById(R.id.activity_main_infotext);
            infoText.setText(R.string.activity_main_loadgame_failure);
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            c.printStackTrace();
        }
    }


    public void buttonNewGame(View view){
        //Create new game: launch ActivityNewGame. This is to make it harder to accidentally overwrite an old game.
        //First clear FileNotFoundException text from infotext.
        TextView infoText = (TextView) findViewById(R.id.activity_main_infotext);
        infoText.setText("");

        //Temporary startActivity, to be replaced by forResult
        startActivity(new Intent(this, ActivityNewGame.class));

    }

    public void buttonAbout(View view){
        //Launch ActivityAbout.
        //First clear FileNotFoundException text from infotext.
        TextView infoText = (TextView) findViewById(R.id.activity_main_infotext);
        infoText.setText("");
        Intent openAbout = new Intent(this, ActivityAbout.class);
        startActivity(openAbout);
    }
}

