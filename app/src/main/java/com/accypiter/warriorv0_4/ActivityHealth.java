package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityHealth extends AppCompatActivity implements View.OnClickListener {
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
        super.onPause();
    }

    public void onStop(){
        save = SaveGame.update(this);
        super.onStop();
    }

    public void updateHealthScreen(){
        //Clear mainlay
        LinearLayout mainLinear = (LinearLayout) findViewById(R.id.activity_health_linlay_main);
        mainLinear.removeAllViews();

        //For each root in Body.roots, traverse it until there are no more children. Display organs if available.
        for (int i = 0; i < 5; i++){
            mainLinear.addView(viewLimb(save.body, i));
        }
    }


    public LinearLayout viewLimb(Body body, int index){
        //Calculate colour of this view
        int alpha_title = 0xA0;
        int alpha_detail = 0x30;
        int padding = (int) (Util.getDensity(this) * 6);

        String limb_name = body.getLimbName(index);

        //Make containing LL
        LinearLayout containerLinear = new LinearLayout(this);
        LinearLayout.LayoutParams width_match_height_wrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerLinear.setLayoutParams(width_match_height_wrap);
        double density = Util.getDensity(this);
        containerLinear.setPadding(0,0,0,(int)(10*density));
        containerLinear.setOrientation(LinearLayout.VERTICAL);
        containerLinear.setTag(index);
        containerLinear.setOnClickListener(this);

        //Make title TextView
        TextView titleText = new TextView(this);
        titleText.setBackgroundColor(Util.colorScale(body.getLimbHealth(index), alpha_title));
        titleText.setText(Util.capitalize(limb_name));
        titleText.setTypeface(null, Typeface.BOLD);

        titleText.setPadding(padding, padding, padding, padding);
        titleText.setLayoutParams(width_match_height_wrap);

        //Add title to LL
        containerLinear.addView(titleText);

        //Add one TextView per child, with colour and stating OK or not.
        BodyPart now_working_on = body.roots.get(index);
        LinearLayout.LayoutParams partParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        while (now_working_on != null){
            double partHealth = now_working_on.getPartHealthScale();
            LinearLayout partLinear = new LinearLayout(this);
            partLinear.setOrientation(LinearLayout.HORIZONTAL);
            partLinear.setBackgroundColor(Util.colorScale(partHealth,alpha_detail));

            TextView partText = new TextView(this);
            partText.setText(now_working_on.GetPartName());
            partText.setPadding(padding, padding, padding, padding);

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
            statusText.setPadding(padding, padding, padding, padding);

            partLinear.addView(partText, partParams);
            partLinear.addView(statusText,statusParams);


            //Add the completed partLinear to containerLinear
            containerLinear.addView(partLinear);

            //Go to next BodyPart
            now_working_on = now_working_on.child;
        }

        return containerLinear;
    }

    public void onClick(View view){
        int index = (int) view.getTag();
        Intent openHealthSecondIntent = new Intent(this, ActivityHealthSecond.class);
        openHealthSecondIntent.putExtra("health_second_tag", index);
        startActivity(openHealthSecondIntent);
    }

}
