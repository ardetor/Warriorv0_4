package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class ActivityNewGameConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_about, menu);
        return true;
    }

    public void cancelOverwrite(View view){
        Intent emptyIntent = new Intent();
        setResult(RESULT_CANCELED, emptyIntent);
        finish();
    }

    public void confirmOverwrite(View view){
        Intent emptyIntent = new Intent();
        setResult(RESULT_OK, emptyIntent);
        finish();
    }
}
