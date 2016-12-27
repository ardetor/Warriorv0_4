package com.accypiter.warriorv0_4;

import java.util.ArrayList;

public class BodyPart {
    //CONSTANTS
    public static final int NORMAL = 0;
    public static final int JOINT = 1;
    public static final int THUMB = 2;
    public static final int FINGER_1 = 3;
    public static final int FINGER_2 = 4;
    public static final int FINGER_3 = 5;
    public static final int FINGER_4 = 6;


    //Admin
    String name;
    String Name;
    int type;

    //Parent reference
    BodyLimb parent;

    //Child organs
    ArrayList<BodyOrgan> organs;

    //Hitbox
    double size;

    //Internal damage statistics
    double damageSharp;
    double damageBleed;
    double damageBlunt;
    boolean severed;
    boolean fractured;

    //Susceptibility to damage relative to other Body parts.
    double factorSharp;
    double factorBleed;
    double factorBlunt;

    //Threshold of damage in a single hit before qualifying to target organs as well
    double thresholdSharp;
    double thresholdBlunt;



    public BodyPart(String name, String Name, int type, BodyLimb parent, double size, double factorSharp,
                    double factorBleed, double factorBlunt, double thresholdSharp, double thresholdBlunt){
        this.name = name;
        this.Name = Name;
        this.type = type;

        this.parent = parent;
        this.organs = new ArrayList<BodyOrgan>();

        this.size = size;

        this.damageSharp = 0;
        this.damageBleed = 0;
        this.damageBlunt = 0;
        this.severed = false;
        this.fractured = false;

        this.factorSharp = factorSharp;
        this.factorBleed = factorBleed;
        this.factorBlunt = factorBlunt;

        this.thresholdSharp = thresholdSharp;
        this.thresholdBlunt = thresholdBlunt;

    }

    public BodyPart(String name, int type, BodyLimb parent, double size, double factorSharp,
                        double factorBleed, double factorBlunt, double thresholdSharp, double thresholdBlunt){
        this(name, name.substring(0,1).toUpperCase() + name.substring(1), type, parent, size, factorSharp, factorBleed, factorBlunt, thresholdSharp, thresholdBlunt);

    }



}
