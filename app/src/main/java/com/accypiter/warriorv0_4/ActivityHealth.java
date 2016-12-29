package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityHealth extends AppCompatActivity {
    public SaveGame save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
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
        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        save = SaveGame.update(this);

        updateHealthScreen();
    }

    @Override
    public void onPause(){
        save = SaveGame.update(this);
        super.onPause();
    }

    public void updateHealthScreen(){
        //Clear mainlay
        LinearLayout mainLinear = (LinearLayout) findViewById(R.id.activity_health_linlay_main);
        mainLinear.removeAllViews();

        //For each root in Body.roots, traverse it until there are no more children. Display organs if available.
        for (BodyPart bodyPart : save.body.roots){
            while (bodyPart != null){





                bodyPart = bodyPart.child;
            }



        }
    }


    public LinearLayout viewBodyPart(BodyPart bodyPart){
        int alpha_title = 0x77;
        int alpha_detail = 0x44;

        double damageSum = 0;
        for (double individual_damage : bodyPart.damage){
            damageSum += individual_damage;
        }

        LinearLayout containerLinear = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView titleText = new TextView(this);
        titleText.setBackgroundColor(Util.colorScale(damageSum, 100, alpha_title));



        return containerLinear;
    }

}
