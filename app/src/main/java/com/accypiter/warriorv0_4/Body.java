package com.accypiter.warriorv0_4;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The general class for bodies and health.
 */

public class Body implements Serializable {
    //CONSTANTS
    public static final int SPECIES_HUMAN = 0;

    //Damage factors
    public static final double DEFAULT_FACTOR_SHARP = 1;
    public static final double DEFAULT_FACTOR_BLEED = 1;
    public static final double DEFAULT_FACTOR_BLUNT = 1;


    int species;
    BodyBase base;
    double weight_base;


    Body(int species) {
        if (species == SPECIES_HUMAN) {
            //First, generate weight. Standard for human is 50kg.
            this.weight_base = 50;

            //Next, generate BodyBase to begin building body.


        }

    }




}










/*

    public static class Part {
        Part parent;
        ArrayList<Part> children;
        ArrayList<Organ> organs;
        String name;
        String Name;
        double damageSharp;  //cut --> lacerated --> badly lacerated
        double damageBleed;  //bleeding --> haemorrhaging --> badly haemorrhaging
        double damageBlunt;  //sore --> bruised --> badly bruised
        boolean severed;
        boolean fractured;
        double damageFactorSharp;
        double damageFactorBleed;
        double damageFactorBlunt;

        Part(Part parent, String name, double damageFactorSharp, double damageFactorBleed, double damageFactorBlunt){
            this.parent = parent;
            this.children = new ArrayList<Part>();
            this.organs = new ArrayList<Organ>();
            this.name = name;
            this.Name = name.substring(0,1).toUpperCase() + name.substring(1);
            this.damageSharp = 0;
            this.damageBleed = 0;
            this.damageBlunt = 0;
            this.severed = false;
            this.fractured = false;
        }

        public void addChild(Part part){

        }

        public void addOrgan(Organ organ){

        }
    }















    public static class Organ {
        Part parent;
        String name;
        String Name;
        double damageSharp;
        double damageBleed;
        double damageBlunt;



        Organ(){

        }

    }

}
*/