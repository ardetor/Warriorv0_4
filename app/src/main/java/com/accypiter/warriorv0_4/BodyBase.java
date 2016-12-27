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

    //Hitbox size
    double size;

    //Internal damage statistics
    double damageSharp;  //cut --> lacerated --> badly lacerated
    double damageBleed;  //bleeding --> haemorrhaging --> badly haemorrhaging
    double damageBlunt;  //sore --> bruised --> badly bruised

    //Susceptibility to damage relative to other Body parts.
    double factorSharp;
    double factorBleed;
    double factorBlunt;

    //Threshold of damage in a single hit before qualifying to target organs as well
    double thresholdSharp;
    double thresholdBlunt;









    //Constructor
    public BodyBase(String name, String Name, Body parent, double size, double factorSharp, double factorBleed,
                    double factorBlunt, double thresholdSharp, double thresholdBlunt){

        this.name = name;
        this.Name = Name;

        this.parent = parent;
        this.limbs = new ArrayList<BodyLimb>();
        this.organs = new ArrayList<BodyOrgan>();

        this.size = size;

        this.damageSharp = 0;
        this.damageBleed = 0;
        this.damageBlunt = 0;

        this.factorSharp = factorSharp;
        this.factorBleed = factorBleed;
        this.factorBlunt = factorBlunt;
        this.thresholdSharp = thresholdSharp;
        this.thresholdBlunt = thresholdBlunt;

    }

    public BodyBase(String name, Body parent, double size, double factorSharp, double factorBleed,
                    double factorBlunt, double thresholdSharp, double thresholdBlunt){
        this(name, name.substring(0,1).toUpperCase() + name.substring(1), parent, size, factorSharp, factorBleed, factorBlunt, thresholdSharp, thresholdBlunt);
    }

















}
