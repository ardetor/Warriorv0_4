package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityJournalDetail extends AppCompatActivity {
    public SaveGame save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        } else if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        save = SaveGame.update(this);

        //Find out which journal entry to display
        int journal_entry_number = getIntent().getIntExtra("journal_entry_number", -1);

        //Get relevant ids
        LinearLayout summaryLinear = (LinearLayout) findViewById(R.id.activity_journal_detail_linlay_summary);
        TextView titleText = (TextView) findViewById(R.id.activity_journal_detail_text_title);
        TextView timeText = (TextView) findViewById(R.id.activity_journal_detail_text_time);
        TextView shortText = (TextView) findViewById(R.id.activity_journal_detail_text_short);
        TextView longText = (TextView) findViewById(R.id.activity_journal_detail_text_long);

        //Handle if somehow the IntExtra is not found
        if (journal_entry_number < 0){
            longText.setText(R.string.activity_journal_detail_error_notfound);
        } else if (journal_entry_number >= save.journal.size()){
            longText.setText(R.string.activity_journal_detail_error_exceed);
        } else {
            JournalEntry journalEntry = save.journal.get(journal_entry_number);

            summaryLinear.setBackgroundColor(journalEntry.getColor());
            titleText.setText(journalEntry.message_title);
            timeText.setText(journalEntry.date.toString());
            shortText.setText(journalEntry.message_short);
            longText.setText(journalEntry.message_long);

        }


    }

    public void onStop(){
        save = SaveGame.update(this);
        super.onStop();
    }

}
