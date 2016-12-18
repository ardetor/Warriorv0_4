package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class ActivitySummary extends AppCompatActivity {
    public static SaveGame save = SaveGame.current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            Intent openSettingsIntent = new Intent(this, ActivityPreferences.class);
            startActivity(openSettingsIntent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume(){
        super.onResume();
        //Refresh views
        refresh();
    }

    public void onPause(){
        SaveGame.save(getBaseContext());
    }

    protected void refresh(){
        //Remove all views from main RelativeLayout
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_summary_rellay);
        relativeLayout.removeAllViews();

        //Place buttons at bottom of screen

    }
}
