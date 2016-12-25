package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityJournal extends AppCompatActivity implements View.OnClickListener {
    public SaveGame save = SaveGame.current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        populateJournal();
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

    protected void populateJournal(){
        //Update displayed journal entries
        //First, clear views from rellay
        LinearLayout mainLinear = (LinearLayout) findViewById(R.id.activity_journal_linlay);
        mainLinear.removeAllViews();
        //Next, populate the relativeLayout with members of save.journal
        for (int counter = 0; counter < save.journal.size(); counter++){
            /*
            Each iteration creates a single journal entry view.
            The organisation is as follows:
            TITLE(BOLD)             TIME(ITALIC)
            MESSAGE MESSAGE MESSAGE MESSAGE
            The colour will depend on the type of journal entry.
            There will be padding around the four edges only; none between the header and the message.
             */

            JournalEntry journalEntry = save.journal.get(counter);
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            int horizontalPadding = (int) (8 * displayMetrics.density); //Used for intra-journal-entry padding.
            int verticalPadding = (int) (8 * displayMetrics.density);

            LinearLayout outerLinear = new LinearLayout(this);
            outerLinear.setOrientation(LinearLayout.VERTICAL);
            outerLinear.setBackgroundColor(journalEntry.getColor());
            LinearLayout.LayoutParams outerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            outerLinear.setLayoutParams(outerParams);
            outerLinear.setTag(counter);
            outerLinear.setOnClickListener(this);

            LinearLayout headerLinear = new LinearLayout(this);
            headerLinear.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams headerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerParams.setMargins(horizontalPadding,verticalPadding,horizontalPadding,0);
            headerLinear.setLayoutParams(headerParams);

            TextView titleText = new TextView(this);
            titleText.setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            titleText.setLayoutParams(titleParams);
            titleText.setText(journalEntry.message_title);

            TextView timeText = new TextView(this);
            timeText.setTypeface(null, Typeface.ITALIC);
            LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0);
            timeText.setLayoutParams(timeParams);
            timeText.setText(getRelativeDate(journalEntry));

            TextView messageText = new TextView(this);
            LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            messageParams.setMargins(horizontalPadding,0,horizontalPadding,verticalPadding);
            messageText.setLayoutParams(messageParams);
            messageText.setText(journalEntry.message_short);

            headerLinear.addView(titleText);
            headerLinear.addView(timeText);

            outerLinear.addView(headerLinear);
            outerLinear.addView(messageText);

            mainLinear.addView(outerLinear);
        }
    }

    @Override
    protected void onPause(){
        save = SaveGame.update(this);
        super.onPause();
    }


    public String getRelativeDate(JournalEntry journalEntry){
        //Returns a string with something like "4 mins ago, 10.16AM"
        return (String) DateUtils.getRelativeDateTimeString(this,journalEntry.date.getTime(),DateUtils.SECOND_IN_MILLIS,DateUtils.DAY_IN_MILLIS*2,0);
    }

    public void onClick(View view){
        //Handles clicks on journal entries in this page. Opens ActivityJournalLong to display full journal entry content.
        int journal_entry_number = (int) view.getTag();
        Intent viewJournalDetailIntent = new Intent(this, ActivityJournalDetail.class);
        viewJournalDetailIntent.putExtra("journal_entry_number", journal_entry_number);
        startActivity(viewJournalDetailIntent);
    }

}
