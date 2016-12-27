package com.accypiter.warriorv0_4;

import java.util.ArrayList;

public class BodyBase {
    //Admin
    String name;
    String Name;

    //Parent reference
    Body parent;

    //Related parts
    ArrayList<BodyLimb> limbs;

    //Sub-parts
    ArrayList<BodyOrgan> organs;

    //Internal damage statistics
    double damageSharp;
    double damageBleed;
    double damageBlunt;

    //Susceptibility to damage relative to other Body parts.
    double factorSharp;
    double factorBleed;
    double factorBlunt;

    //Threshold of damage in a single hit before qualifying to target organs as well
    double thresholdSharp;
    double thresholdBlunt;



}
