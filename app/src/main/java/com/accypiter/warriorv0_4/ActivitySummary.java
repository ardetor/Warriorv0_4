package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        save = SaveGame.current;

        updateHealthCurrent();
        updateHealthBody();
        debugger();
    }

    public void onPause(){
        super.onPause();
        SaveGame.save(getBaseContext());
    }

    public void testHealthBody(View view){
        save.health_body -= 1.;
        save.health_current -= 1.;
        updateHealthCurrent();
        updateHealthBody();
        debugger();
    }

    public void testHealthCurrent(View view){
        save.health_current -= 1.;
        updateHealthCurrent();
        debugger();
    }

    public void updateHealthCurrent(){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        double screenWidth = displayMetrics.widthPixels;// / displayMetrics.density;
        int currentHealthLength = (int) (screenWidth * save.health_current / save.health_max);
        View view = findViewById(R.id.activity_summary_bar_health_current);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = currentHealthLength;
        view.setLayoutParams(layoutParams);
    }

    public void updateHealthBody(){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        double screenWidth = displayMetrics.widthPixels;// / displayMetrics.density;
        int currentHealthLength = (int) (screenWidth * save.health_body / save.health_max);
        View view = findViewById(R.id.activity_summary_bar_health_body);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = currentHealthLength;
        view.setLayoutParams(layoutParams);
    }

    public void debugger(){
        TextView debugger = (TextView) findViewById(R.id.activity_summary_debugger);
        debugger.setText(ClassUtils.toString(save));
    }
}
