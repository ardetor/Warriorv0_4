package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

public class ActivitySummary extends AppCompatActivity {
    //DO NOT CHANGE THIS TO SAVEGAME.UPDATE. I HAVE NO IDEA WHY BUT IT WILL CRASH THE ACTIVITY.
    //Probably due to simultaneous writing. Not sure, though.
    public SaveGame save = SaveGame.current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_standard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            Intent openSettingsIntent = new Intent(this, ActivityPreferences.class);
            startActivity(openSettingsIntent);
            return true;

        } else if (id == R.id.menu_refresh) {
            this.recreate();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        save = SaveGame.update(this);
        if (save.magic_enabled){
            enableMagicButton();
        }

        refreshScreen();
    }

    @Override
    public void onPause(){
        SaveGame.update(this);
        super.onPause();
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

    protected void refreshScreen(){
        updateHealthCurrent();
        updateHealthBody();
        debugger();
    }

    protected void startFight(View view){
        //Remember to set enemy in SaveGame before calling this function
        save.in_fight = true;
        Intent startFightIntent = new Intent();
        setResult(RESULT_OK, startFightIntent);
        finish();

    }

    protected void enableMagicButton(){
        ImageButton magicButton = (ImageButton) findViewById(R.id.activity_summary_button_magic);
        magicButton.setEnabled(true);
        magicButton.setImageResource(R.drawable.icon_magic);
        magicButton.setContentDescription(getString(R.string.activity_summary_button_magic));
    }

    public void startActivityJournal(View view){
        Intent openJournalIntent = new Intent(this, ActivityJournal.class);
        startActivity(openJournalIntent);
    }

    public void testJournal(View view){
        JournalEntry journalEntry = new JournalEntry(new Date(), JournalEntry.TYPE_SKILL_LEVEL_UP, "Skill increase", "Blunt weapon skill has increased.", "You have reached Level 23 in Blunt weaponry. 93.235 experience points to next level.");
        SaveGame.addJournalEntry(this, journalEntry);
    }

    public void testJournal2(View view){
        JournalEntry journalEntry = new JournalEntry(new Date(), JournalEntry.TYPE_NORMAL, "Item purchase", "Potion purchased for 625 copper pieces.");
        SaveGame.addJournalEntry(this, journalEntry);
    }

/*    public void testJournal2(View view){
        for(int i = 0; i < 100; ++i) {
            JournalEntry journalEntry = new JournalEntry(new Date(), JournalEntry.TYPE_NORMAL, "asdf2", "asdfasdf2", "asdfasdfasdf2");
            SaveGame.addJournalEntry(this, journalEntry);
        }
    }*/
}

/*
//Sample code area







*/