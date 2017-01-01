package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    public void onResume() {
        super.onResume();
        save = SaveGame.update(this);
        if (save.magic_enabled) {
            enableMagicButton();
        }


        //Debugging things
        refreshScreen();
    }

    @Override
    public void onPause() {
        //DO NOT UPDATE SAVEGAME IN ONPAUSE(). CAUSES SOME WEIRD PROBLEM WHERE AFTER FORCE STOP, SAVE FILE BECOMES NULL.
        //No idea why it happens, but the solution is to update in onStop instead. My guess would be that
        //force stop tries to run onPause, but only gets halfway i.e. the writing to save is interrupted and
        //the save file becomes corrupted, causing problems when next read. I don't know if movement to onStop
        //may cause future problems but I don't see any right now and I don't see any way to tell if it will
        //ever happen.
        super.onPause();
    }

    public void onStop(){
        save = SaveGame.update(this);
        super.onStop();
    }

    protected void enableMagicButton() {
        ImageButton magicButton = (ImageButton) findViewById(R.id.activity_summary_button_magic);
        magicButton.setEnabled(true);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        magicButton.setLayoutParams(layoutParams);
        magicButton.setContentDescription(getString(R.string.activity_summary_button_magic));
    }

    protected void startFight(View view) {
        //Remember to set enemy in SaveGame before calling this function
        save.in_fight = true;
        Intent startFightIntent = new Intent();
        setResult(RESULT_OK, startFightIntent);
        finish();

    }


    public void startActivityJournal(View view) {
        Intent openJournalIntent = new Intent(this, ActivityJournal.class);
        startActivity(openJournalIntent);
    }

    public void startActivityHealth(View view){
        Intent openHealthIntent = new Intent(this, ActivityHealth.class);
        startActivity(openHealthIntent);
    }








    //DEBUGGING-RELATED THINGS

    public void testHealthCurrent(View view) {
        save.body.blood_current -= 0.02;
        save.body.roots.get(2).getChild(2).organ.sever();
        save.body.roots.get(0).damage[4] += 1;
        updateHealthCurrent();
        debugger();
    }

    public void updateHealthCurrent() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        double screenWidth = displayMetrics.widthPixels;// / displayMetrics.density;
        int currentHealthLength = (int) (screenWidth * save.body.blood_current / save.body.blood_max);
        View view = findViewById(R.id.activity_summary_bar_blood_current);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = currentHealthLength;
        view.setLayoutParams(layoutParams);
    }

    public void debugger() {
        TextView debugger = (TextView) findViewById(R.id.activity_summary_debugger);
        debugger.setText(Util.toString(save));
    }

    protected void refreshScreen() {
        updateHealthCurrent();
        debugger();
    }

    public void testJournal(View view) {
        JournalEntry journalEntry = new JournalEntry(new Date(), JournalEntry.TYPE_SKILL_LEVEL_UP, "Skill increase", "Blunt weapon skill has increased.", "You have reached Level 23 in Blunt weaponry. 93.235 experience points to next level.");
        SaveGame.addJournalEntry(this, journalEntry);
    }

    public void testJournal2(View view) {
        JournalEntry journalEntry = new JournalEntry(new Date(), JournalEntry.TYPE_NORMAL, "Item purchase", "Potion purchased for 625 copper pieces.");
        SaveGame.addJournalEntry(this, journalEntry);
    }

    public void toggleMagic(View view){
        save.magic_enabled = !save.magic_enabled;
    }

}

/*
//Sample code area







*/