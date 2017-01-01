package com.accypiter.warriorv0_4;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityHealthThird extends AppCompatActivity {
    public SaveGame save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_third);
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

        updateHealthThird();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        save = SaveGame.update(this);
        super.onStop();
    }

    public void updateHealthThird(){
        //Displays detailed information about a single BodyPart.

        //Tunable Constants
        int alpha_title = 0xA0;
        int alpha_detail = 0x30;
        int icon_dimension_dp = (int) (25 * Util.getDensity(this));
        int titlePadding = (int) (Util.getDensity(this) * 6);

        //First, clear Activity
        LinearLayout mainLinear = (LinearLayout) findViewById(R.id.activity_health_third_linlay_main);
        mainLinear.removeAllViews();

        //Get relevant body part
        int[] part_details = getIntent().getIntArrayExtra("activity_health_third_identifier");
        BodyPart bodyPart = save.body.roots.get(part_details[0]).getChild(part_details[1])
                .getOrganIfTrue(part_details[2] == 1);

        //Work out colour in advance:
        int color_title = Util.colorScale(bodyPart.getPartHealthScale(),alpha_title);
        int color_detail = Util.colorScale(bodyPart.getPartHealthScale(), alpha_detail);

        //Now display body part:
        //Create title TextView
        TextView titleText = new TextView(this);
        titleText.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);
        titleText.setText(bodyPart.GetFullPartName());
        titleText.setBackgroundColor(color_title);
        titleText.setTypeface(null, Typeface.BOLD);
        mainLinear.addView(titleText);


        //If severed, only show severed. Else continue.
        if (bodyPart.severeDamage[0]){
            //Create template detail LL, IV and TV
            LinearLayout detailLinear = new LinearLayout(this);
            detailLinear.setOrientation(LinearLayout.HORIZONTAL);
            detailLinear.setBackgroundColor(color_detail);
            detailLinear.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

            ImageView iconImage = new ImageView(this);
            LinearLayout.LayoutParams width_fixed_height_fixed = new LinearLayout.LayoutParams(icon_dimension_dp, icon_dimension_dp);
            iconImage.setLayoutParams(width_fixed_height_fixed);

            TextView detailText = new TextView(this);
            detailText.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);
            detailText.setTypeface(null,Typeface.BOLD);

            View colorView = new View(this);
            colorView.setLayoutParams(width_fixed_height_fixed);
            colorView.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

            //Display severed only
            iconImage.setImageResource(R.drawable.icon_sharp_severe);
            detailText.setText(bodyPart.getDamageSevereStatusText(0).toUpperCase());
            colorView.setBackgroundColor(Util.colorScale(0,alpha_title));

            detailLinear.addView(iconImage);
            detailLinear.addView(colorView);
            detailLinear.addView(detailText);

            mainLinear.addView(detailLinear);

        } else{
            //Continue to display the others
            if (bodyPart.severeDamage[1]){

                //Create template detail LL, IV and TV
                LinearLayout detailLinear = new LinearLayout(this);
                detailLinear.setOrientation(LinearLayout.HORIZONTAL);
                detailLinear.setBackgroundColor(color_detail);
                detailLinear.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                ImageView iconImage = new ImageView(this);
                LinearLayout.LayoutParams width_fixed_height_fixed = new LinearLayout.LayoutParams(icon_dimension_dp, icon_dimension_dp);
                iconImage.setLayoutParams(width_fixed_height_fixed);

                TextView detailText = new TextView(this);
                detailText.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);
                detailText.setTypeface(null,Typeface.BOLD);

                View colorView = new View(this);
                colorView.setLayoutParams(width_fixed_height_fixed);
                colorView.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                //Display fractured
                iconImage.setImageResource(R.drawable.icon_blunt_severe);
                detailText.setText(bodyPart.getDamageSevereStatusText(1).toUpperCase());
                colorView.setBackgroundColor(Util.colorScale(0,alpha_title));

                detailLinear.addView(iconImage);
                detailLinear.addView(colorView);
                detailLinear.addView(detailText);

                mainLinear.addView(detailLinear);

            }

            for(int damage_type = 0; damage_type < 4; damage_type++){

                //Create template detail LL, IV and TV
                LinearLayout detailLinear = new LinearLayout(this);
                detailLinear.setOrientation(LinearLayout.HORIZONTAL);
                detailLinear.setBackgroundColor(color_detail);
                detailLinear.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                ImageView iconImage = new ImageView(this);
                LinearLayout.LayoutParams width_fixed_height_fixed = new LinearLayout.LayoutParams(icon_dimension_dp, icon_dimension_dp);
                iconImage.setLayoutParams(width_fixed_height_fixed);

                TextView detailText = new TextView(this);
                detailText.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                View colorView = new View(this);
                colorView.setLayoutParams(width_fixed_height_fixed);
                colorView.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                //Display damage type and damage text
                iconImage.setImageResource(Util.getDamageTypeImage(damage_type));
                colorView.setBackgroundColor(Util.colorScale((bodyPart.getDamageHealthScale(damage_type)), alpha_title));
                detailText.setText(bodyPart.GetDamageStatusText(damage_type));

                detailLinear.addView(iconImage);
                detailLinear.addView(colorView);
                detailLinear.addView(detailText);

                mainLinear.addView(detailLinear);

            }

            if (bodyPart.damage[4] > 0){
                int damage_type = 4;

                //Create template detail LL, IV and TV
                LinearLayout detailLinear = new LinearLayout(this);
                detailLinear.setOrientation(LinearLayout.HORIZONTAL);
                detailLinear.setBackgroundColor(color_detail);
                detailLinear.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                ImageView iconImage = new ImageView(this);
                LinearLayout.LayoutParams width_fixed_height_fixed = new LinearLayout.LayoutParams(icon_dimension_dp, icon_dimension_dp);
                iconImage.setLayoutParams(width_fixed_height_fixed);

                TextView detailText = new TextView(this);
                detailText.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                View colorView = new View(this);
                colorView.setLayoutParams(width_fixed_height_fixed);
                colorView.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                //Display damage type and damage text
                iconImage.setImageResource(Util.getDamageTypeImage(damage_type));
                colorView.setBackgroundColor(Util.colorScale((bodyPart.getDamageHealthScale(damage_type)), alpha_title));
                detailText.setText(bodyPart.GetDamageStatusText(damage_type));

                detailLinear.addView(iconImage);
                detailLinear.addView(colorView);
                detailLinear.addView(detailText);

                mainLinear.addView(detailLinear);




                //Create template detail LL, IV and TV
                LinearLayout detailLinear2 = new LinearLayout(this);
                detailLinear2.setOrientation(LinearLayout.HORIZONTAL);
                detailLinear2.setBackgroundColor(color_detail);
                detailLinear2.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                ImageView iconImage2 = new ImageView(this);
                iconImage2.setLayoutParams(width_fixed_height_fixed);

                TextView detailText2 = new TextView(this);
                detailText2.setPadding(titlePadding,titlePadding,titlePadding,titlePadding);

                iconImage2.setImageResource(Util.getDamageTypeImage(4));
                detailText2.setText(R.string.activity_health_third_message_dark);

                detailLinear2.addView(iconImage2);
                detailLinear2.addView(detailText2);

                mainLinear.addView(detailLinear2);
            }
        }








    }

}
