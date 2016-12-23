package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivityFight extends AppCompatActivity {
    public static SaveGame save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume(){
        super.onResume();
        save = SaveGame.current;
    }

    public void endFight(View view){
        save.in_fight = false;
        Intent endFightIntent = new Intent();
        setResult(RESULT_OK, endFightIntent);
        finish();
    }
}
