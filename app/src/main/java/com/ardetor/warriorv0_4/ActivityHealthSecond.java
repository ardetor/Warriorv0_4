package com.ardetor.warriorv0_4;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityHealthSecond extends AppCompatActivity implements View.OnClickListener{
    public SaveGame save = SaveGame.current;

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
        super.onPause();
    }

    public void onStop(){
        save = SaveGame.update(this);
        super.onStop();
    }

    public void updateHealthSecond(){
        //Clear mainlay
        LinearLayout mainLinear = (LinearLayout) findViewById(R.id.activity_health_second_linlay_main);
        mainLinear.removeAllViews();

        //Find out which limb we will be looking at
        BodyPart root = save.body.roots.get(getIntent().getIntExtra("activity_health_second_identifier", 0));

        //For each root in Body.roots, traverse it until there are no more children. Display organs if available.
        for (BodyPart bodyPart = root; bodyPart != null ; bodyPart = bodyPart.child){
            mainLinear.addView(viewPart(bodyPart));

            /*
            //REMOVED ORGAN VIEW FROM ACTIVITYHEALTHSECOND.
             NOT IMPORTANT ENOUGH. MOVED TO ACTIVITYHEALTHTHIRD.

            if (bodyPart.organ != null){
                mainLinear.addView(viewPart(bodyPart.organ));
            }*/
        }
    }


    public LinearLayout viewPart(BodyPart bodyPart){
        /*
        * The structure of this view is:
        * TITLE TITLE TITLE
        * [sharp icon] [sharp damage indicator]      [Blunt D][Blunt DI]          [Bleed D]etc. etc.
        *
        * containerLinear is the thing containing the whole thing.
        * titleTextView is the TITLE TITLE TITLE thing.
        * statusLinear is the Linear containing the status row.
        * statusImage is the ImageView containing the damage icon.
        * statusView is the View containing the damage indicator.
        *
        * statusImages and statusViews have gravity toward their corresponding members. So that
        * they cluster together. I don't know how to explain this better. They clump in their groups
        * of two. If you don't get it you have bigger problems than this.
        *
        * If severe, the statusImage will show a red icon.
        * */



        //Constants
        int alpha_title = 0xA0;
        int alpha_detail = 0x30;
        int icon_dimension_dp = 25;
        int titlePadding = (int) (Util.getDensity(this) * 6);

        //Make containing LL
        LinearLayout containerLinear = new LinearLayout(this);
        LinearLayout.LayoutParams width_match_height_wrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerLinear.setLayoutParams(width_match_height_wrap);
        double density = Util.getDensity(this);
        containerLinear.setPadding(0,0,0,titlePadding);
        containerLinear.setOrientation(LinearLayout.VERTICAL);

        //Make title TextView
        TextView titleText = new TextView(this);
        titleText.setBackgroundColor(Util.colorScale(bodyPart.getPartHealthScaleIncludingOrgan(), alpha_title));
        titleText.setText(bodyPart.GetFullPartName());
        titleText.setTypeface(null, Typeface.BOLD);
        titleText.setPadding(titlePadding, titlePadding, titlePadding, titlePadding);
        titleText.setLayoutParams(width_match_height_wrap);

        //Add title to LL
        containerLinear.addView(titleText);

        //Construct statusLinear
        LinearLayout statusLinear = new LinearLayout(this);
        statusLinear.setLayoutParams(width_match_height_wrap);
        statusLinear.setBackgroundColor(Util.colorScale(bodyPart.getPartHealthScaleIncludingOrgan(), alpha_detail));
        statusLinear.setOrientation(LinearLayout.HORIZONTAL);
        statusLinear.setPadding(titlePadding, titlePadding, titlePadding, titlePadding);


        //Add icons and status text for the 4 main damage types
        for (int damage_type = 0; damage_type < 4; damage_type++){

            ImageView statusImage = new ImageView(this);
            View statusView = new View(this);
            LinearLayout.LayoutParams width_fixed_height_fixed = new LinearLayout.LayoutParams((int) (icon_dimension_dp * density), (int) (icon_dimension_dp * density));
            statusImage.setLayoutParams(width_fixed_height_fixed);

            //FOR DEBUGGING - Increments damage to a particular body part if its icon is clicked
            int[] tag = {getIntent().getIntExtra("activity_health_second_identifier", 0), bodyPart.getPartIndexInLimb(), damage_type, bodyPart.isOrgan?1:0};
            statusView.setTag(tag);
            statusView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) { //FOR DEBUGGING ONLY - REMOVE LATER
                    int a = ((int[]) view.getTag())[0];
                    int b = ((int[]) view.getTag())[1];
                    int c = ((int[]) view.getTag())[2];
                    int d = ((int[]) view.getTag())[3];
                    if (d == 0) {
                        save.body.roots.get(a).getChild(b).damage[c] += 1;
                    } else {
                        save.body.roots.get(a).getChild(b).organ.damage[c] += 1;
                    }
                    updateHealthSecond();
                }
            });
            //END DEBUGGING

            LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams((int) ((icon_dimension_dp - 2) * density), (int) ((icon_dimension_dp - 2) * density));
            statusView.setLayoutParams(width_fixed_height_fixed);

            View spacerView = new View(this);
            LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams((int) (10 * density), (int)(icon_dimension_dp * density));
            spacerView.setLayoutParams(spacerParams);

            switch (damage_type) { 
                case 0:
                    if (!bodyPart.severeDamage[0]) {
                        statusImage.setImageResource(R.drawable.icon_sharp);
                    } else {
                        statusImage.setImageResource(R.drawable.icon_sharp_severe);
                    }
                    break;

                case 1:
                    if (!bodyPart.severeDamage[1]) {
                        statusImage.setImageResource(R.drawable.icon_blunt);
                    } else {
                        statusImage.setImageResource(R.drawable.icon_blunt_severe);
                    }
                    break;

                case 2:
                    statusImage.setImageResource(R.drawable.icon_blood);
                    break;

                default: //case 3
                    statusImage.setImageResource(R.drawable.icon_burn);
                    break;
            }

            //statusView.setBackgroundColor(Util.invertedColorScale(bodyPart.damage[damage_type],5,alpha_title));
            statusView.setBackgroundColor(Util.colorScale(bodyPart.getDamageHealthScaleIncludingOrgan(damage_type), alpha_title));

            statusLinear.addView(statusImage);
            statusLinear.addView(statusView);
            if (damage_type != 3){
                statusLinear.addView(spacerView);
            }
        }
        containerLinear.addView(statusLinear);

        //Make containerLinear open HealthThird on click
        //Stores identifying information in tag[].
        //tag[0]: root index
        //tag[1]: self-explanatory; part index in limb.
        //tag[2]: whether or not have to get organ.
        int[] tag = {getIntent().getIntExtra("activity_health_second_identifier", 0), bodyPart.getPartIndexInLimb(), bodyPart.isOrgan?1:0};
        containerLinear.setTag(tag);
        containerLinear.setOnClickListener(this);



        return containerLinear;
    }

    @Override
    public void onClick(View view) {
        int[] tag = (int[]) view.getTag();

        Intent openHealthThirdIntent = new Intent(this, ActivityHealthThird.class);
        openHealthThirdIntent.putExtra("activity_health_third_identifier", tag);
        startActivity(openHealthThirdIntent);

    }

}
