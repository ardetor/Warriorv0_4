package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityHealthSecond extends AppCompatActivity {
    public SaveGame save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_second);
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

        updateHealthSecond();
    }

    @Override
    public void onPause(){
        save = SaveGame.update(this);
        super.onPause();
    }

    public void updateHealthSecond(){
        //Clear mainlay
        LinearLayout mainLinear = (LinearLayout) findViewById(R.id.activity_health_second_linlay_main);
        mainLinear.removeAllViews();

        //Find out which limb we will be looking at
        BodyPart root = save.body.roots.get(getIntent().getIntExtra("health_second_tag", 0));

        //For each root in Body.roots, traverse it until there are no more children. Display organs if available.
        for (BodyPart bodyPart = root; bodyPart != null ; bodyPart = bodyPart.child){
            mainLinear.addView(viewLimbSecond(bodyPart));
            if (bodyPart.organ != null){
                mainLinear.addView(viewLimbSecond(bodyPart.organ));
            }
        }
    }


    public LinearLayout viewLimbSecond(BodyPart bodyPart){
        //Calculate colour of this view
        int alpha_title = 0xA0;
        int alpha_detail = 0x30;

        //Make containing LL
        LinearLayout containerLinear = new LinearLayout(this);
        LinearLayout.LayoutParams width_match_height_wrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerLinear.setLayoutParams(width_match_height_wrap);
        double density = Util.getDensity(this);
        containerLinear.setPadding(0,0,0,(int)(10*density));
        containerLinear.setOrientation(LinearLayout.VERTICAL);

        //Make title TextView
        TextView titleText = new TextView(this);
        titleText.setBackgroundColor(Util.colorScale(bodyPart.getPartHealth(), alpha_title));
        titleText.setText(bodyPart.GetPartName());
        titleText.setTypeface(null, Typeface.BOLD);
        int titlePadding = (int) (Util.getDensity(this) * 4);
        titleText.setPadding(titlePadding, titlePadding, titlePadding, titlePadding);
        LinearLayout.LayoutParams width_fill_height_wrap = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        titleText.setLayoutParams(width_fill_height_wrap);


        //Add severe icons
        //LinearLayout.LayoutParams width_wrap_height_match
        if (bodyPart.severeDamage[0]){
            ImageView icon = new ImageView(this);
            icon.setMaxHeight((int) (15 * density));
            //to be replaced by a function
        }


        //Add title to LL
        containerLinear.addView(titleText);



        /*

        //Add one TextView per child, with colour and stating OK or not.
        BodyPart now_working_on = body.roots.get(index);
        LinearLayout.LayoutParams partParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        while (now_working_on != null){
            double partHealth = now_working_on.getPartHealth();
            LinearLayout partLinear = new LinearLayout(this);
            partLinear.setOrientation(LinearLayout.HORIZONTAL);
            partLinear.setBackgroundColor(Util.colorScale(partHealth,alpha_detail));

            TextView partText = new TextView(this);
            partText.setText(Util.GetPartName(now_working_on));
            partText.setPadding(titlePadding, titlePadding, titlePadding, titlePadding);

            TextView statusText = new TextView(this);
            String status;
            if (partHealth == 1){
                status = "Perfect";
            } else if (partHealth > 0.9){
                status = "Good";
            } else if (partHealth > 0.5){
                status = "Poor";
            } else if (partHealth > 0){
                status = "Very poor";
            } else { //partHealth == 1
                status = "Critical";
            }
            statusText.setText(status);
            statusText.setPadding(titlePadding, titlePadding, titlePadding, titlePadding);

            partLinear.addView(partText, partParams);
            partLinear.addView(statusText,statusParams);


            //Add the completed partLinear to containerLinear
            containerLinear.addView(partLinear);

            //Go to next BodyPart
            now_working_on = now_working_on.child;
        }
*/
        return containerLinear;


    }

}
