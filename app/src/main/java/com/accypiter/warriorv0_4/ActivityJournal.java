package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityJournal extends AppCompatActivity implements View.OnClickListener {
    public SaveGame save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_summary, menu);
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
    protected void onResume(){
        super.onResume();
        save = SaveGame.update(this);

        //Update displayed journal entries
        //First, clear views from rellay
        LinearLayout mainLinear = (LinearLayout) findViewById(R.id.activity_journal_linlay);
        mainLinear.removeAllViews();
        //Next, populate the relativeLayout with members of save.journal
        for (int counter = 0; counter < save.journal.size(); counter++){
            JournalEntry journalEntry = save.journal.get(counter);
            LinearLayout outerLinear = new LinearLayout(this);
            outerLinear.setOrientation(LinearLayout.VERTICAL);
            outerLinear.setBackgroundColor(journalEntry.getColor());

        }
    }

    @Override
    protected void onPause(){
        save = SaveGame.update(this);
        super.onPause();
    }


    public String getRelativeDate(JournalEntry entry){
        //Returns a string with something like "4 mins ago, 10.16AM"
        return (String) DateUtils.getRelativeDateTimeString(this,entry.date.getTime(),DateUtils.SECOND_IN_MILLIS,DateUtils.DAY_IN_MILLIS*2,0);
    }

    public void onClick(View view){
        //Handles clicks on journal entries in this page. Opens ActivityJournalLong to display full journal entry content.

    }

}
