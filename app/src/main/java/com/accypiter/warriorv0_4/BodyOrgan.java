package com.accypiter.warriorv0_4;

public class BodyOrgan {
    //Static types
    public static final int OTHER = -1;
    public static final int HEART = 0;
    public static final int LUNG = 1;
    public static final int LIVER = 2;
    public static final int INTESTINE = 3;
    public static final int EYE = 4;
    public static final int ARTERY = 5;
    public static final int VEIN = 6;
    

    //Admin
    String name;
    String Name;
    int type;

    //Hitbox
    double size;

    //Internal damage statistics
    double damageSharp;
    double damageBleed;
    double damageBlunt;

    //Susceptibility to damage relative to other Body parts.
    double factorSharp;
    double factorBleed;
    double factorBlunt;




    //Constructor
    public BodyOrgan(String name, String Name, int type, double size, double factorSharp,
                     double factorBleed, double factorBlunt){
        this.name = name;
        this.Name = Name;
        this.type = type;

        this.size = size;

        this.damageSharp = 0;
        this.damageBleed = 0;
        this.damageBlunt = 0;

        this.factorSharp = factorSharp;
        this.factorBleed = factorBleed;
        this.factorBlunt = factorBlunt;
    }

    public BodyOrgan(String name, int type, double size, double factorSharp,
                     double factorBleed, double factorBlunt){
        this(name, name.substring(0,1).toUpperCase() + name.substring(1), type, size, factorSharp, factorBleed, factorBlunt);
    }

}
