package com.ardetor.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

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
        getMenuInflater().inflate(R.menu.menu_activity_blank, menu);
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

    public void armOverwrite(View view){
        Button armButton = (Button) findViewById(R.id.activity_new_game_confirm_button_arm);
        Button overwriteButton = (Button) findViewById(R.id.activity_new_game_confirm_button_overwrite);
        if (!overwriteButton.isEnabled()) {
            armButton.setText(R.string.activity_new_game_confirm_button_disarm);
            overwriteButton.setEnabled(true);
            overwriteButton.setTextColor(0xFFFF0000);
        } else {
            armButton.setText(R.string.activity_new_game_confirm_button_arm);
            overwriteButton.setEnabled(false);
            overwriteButton.setTextColor(0x55FF0000);
        }
    }

}
