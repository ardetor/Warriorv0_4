package com.accypiter.warriorv0_4;

import java.io.Serializable;

/**
 * The general class for bodies and health.
 */

public class Body implements Serializable {
    //Species constants
    public static final int SPECIES_HUMAN = 0;


    //Base constants, human
    public static final double HUMAN_WEIGHT_BASE = 50;
    public static final double HUMAN_TORSO_SIZE = 1;
    public static final double HUMAN_BLOOD_BASE = 1.5;  //In litres, the amount that can afford to lose. 30% of 5 litres = 1.5L.

    public static final double HUMAN_FACTOR_SHARP = 1;
    public static final double HUMAN_FACTOR_BLEED = 1;
    public static final double HUMAN_FACTOR_BLUNT = 1;

    public static final double HUMAN_TORSO_THRESHOLD_SHARP = 10;
    public static final double HUMAN_TORSO_THRESHOLD_BLUNT = 10;




    //Species
    int species;


    //Parameters
    double weight_base;
    boolean has_vision;

    //Blood
    double blood_true_max;
    double blood_max;
    double blood_current;


    //The body itself
    BodyBase base;



    //Constructor
    Body(int species) {
        this.species = species;

        //Construct differently based on species
        if (species == SPECIES_HUMAN) {
            //Parameters
            this.weight_base = HUMAN_WEIGHT_BASE;
            this.has_vision = true;

            //Blood
            this.blood_true_max = HUMAN_BLOOD_BASE;
            this.blood_max = HUMAN_BLOOD_BASE;
            this.blood_current = HUMAN_BLOOD_BASE;

            //Next, generate BodyBase to begin building body.
            this.base = new BodyBase("torso", this, HUMAN_TORSO_SIZE, HUMAN_FACTOR_SHARP,
                    HUMAN_FACTOR_BLEED, HUMAN_FACTOR_BLUNT, HUMAN_TORSO_THRESHOLD_SHARP,
                    HUMAN_TORSO_THRESHOLD_BLUNT);




        }

    }




}