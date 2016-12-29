package com.accypiter.warriorv0_4;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Body is the top-level object describing a character's physical state.
 * It contains an ArrayList of roots, which are the first body parts in a particular section of the
 *      body (shoulder, hip, neck).
 * The roots are identifiable by being the only Parts with a parent value of null.
 * An organ is a Part that is a leaf; i.e. it has an empty ArrayList<Part> children.
 *
 * Body contains a function evaluateRoot or equivalent, which traverses a root and determines
 * how functional it is.
 *
 * BodyParts contain a Weapon reference, but it is left null UNLESS the Part has a null parent, i.e.
 * it is a root.
 *
 * BodyParts also contain a root_type integer, which states the type of limb it is, and is also
 * null UNLESS it is a root.
 *
 * All body parts contain an armour reference.
 *
 */

public class Body implements Serializable {
    Species species;

    ArrayList<BodyPart> roots;

    //Vision related
    boolean has_vision;             //If no vision, accuracy -2. Is true if functioning eyes > 0.
    ArrayList<BodyPart> eyes;       //Carries references to eyes.

    double weight;                  //Equals species base weight, plus a fraction of strength

    //Blood
    double blood_true_max;
    double blood_max;
    double blood_current;


    //Constructor
    Body(Species species) {
        this.species = species;

        this.weight = species.baseWeight();
        this.has_vision = true;

        //Blood
        this.blood_true_max = species.baseBlood();
        this.blood_max = species.baseBlood();
        this.blood_current = species.baseBlood();

        //Construct torso
        //BodyPart torso = new BodyPart();


        //Construct heads


        //Construct arms


        //Construct legs

    }


    interface Species {
        String getSpecies();

        int numberHeads();
        int numberArms();
        int numberLegs();

        double baseWeight();
        double baseBlood();

        double speciesSize();

        double susceptibility(int attack_type);


    }

    public static class Human implements Species {
        public String getSpecies() { return "human"; }

        public int numberHeads() { return 1; }
        public int numberArms() { return 2; }
        public int numberLegs() { return 2; }

        public double baseWeight() { return 50; }
        public double baseBlood() { return 1.5; }

        public double speciesSize() { return 1; }

        public double susceptibility(int attack_type) { return 1; }
    }
}