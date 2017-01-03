package com.ardetor.warriorv0_4;

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

    double weight;                  //Equals species base weight, plus a fraction of strength

    //Blood
    double blood_true_max;
    double blood_max;
    double blood_current;

    //Blood Regen
    double blood_regen_base;         //Should not change
    double blood_regen_variable;         //Multiplier
    long blood_regen_variable_duration;  //How long before mult resets to 1

    //Health regen
    double health_regen_base;        //Should not change
    double health_regen_variable;        //Multiplier
    long health_regen_variable_duration;      //How long before mult resets to 1

    //Constructor
    Body(Species species) {
        this.species = species;

        this.weight = species.baseWeight();

        //Blood
        this.blood_true_max = species.baseBlood();
        this.blood_max = species.baseBlood();
        this.blood_current = species.baseBlood();

        this.blood_regen_base = species.baseBloodRegen();  //Will regen one fifth of max blood per period (default 1 hour)
        this.blood_regen_variable = 0;                     //starts at zero, goes up temporarily when medicine is taken.
        this.blood_regen_variable_duration = 0;            //Duration remaining, in milliseconds, of the multiplier.

        this.health_regen_base =  species.baseHealthRegen();
        this.health_regen_variable = 0;
        this.health_regen_variable_duration = 0;

        //CONSTRUCT BODY!
        this.roots = new ArrayList<BodyPart>();

        //generate torso root
        BodyPart torso = new BodyPart(this.species, null, null, BodyPart.LIMB_CORE, 0);
        this.roots.add(torso);
        extendPart(torso, 2); //populate torso

        BodyPart leftArm = new BodyPart(this.species, null, "left", BodyPart.LIMB_ARM, 0);
        this.roots.add(leftArm);
        extendPart(leftArm, 5);

        BodyPart rightArm = new BodyPart(this.species, null, "right", BodyPart.LIMB_ARM, 0);
        this.roots.add(rightArm);
        extendPart(rightArm, 5);

        BodyPart leftLeg = new BodyPart(this.species, null, "left", BodyPart.LIMB_LEG, 0);
        this.roots.add(leftLeg);
        extendPart(leftLeg, 5);

        BodyPart rightLeg = new BodyPart(this.species, null, "right", BodyPart.LIMB_LEG, 0);
        this.roots.add(rightLeg);
        extendPart(rightLeg, 5);

    }

    public BodyPart extendPart(BodyPart parent){
        BodyPart child = new BodyPart(parent.species, parent, parent.designation, parent.limb_type, parent.index + 1);
        parent.child = child;

        return child;
    }

    public BodyPart extendPart(BodyPart parent, int extent){
        BodyPart now_working_on = parent;
        for (int i = 0; i < extent; i++){
            now_working_on = extendPart(now_working_on);
        }
        return parent;
    }


    public static double getLimbHealthScale(BodyPart root){
        //Given a root, traverse and aggregate the damage, and give fractional effectiveness of that limb.
        //Accounts for severe damage.
        //Perfect health is a 1. If 0, the limb cannot attack.
        double reference_health = 8.5;
        double limb_aggregate_damage = 0;
        int number_severe = 0;

        BodyPart now_working_on = root;
        while (now_working_on != null){
            //Add one to reference health for each body part or organ
            reference_health += 0.2;

            //Add aggregate damage^2
            limb_aggregate_damage += Math.pow(now_working_on.partAggregateDamage(), 2);

            //Track severe damage
            if (now_working_on.isSeverelyDamaged()){
                number_severe += 1;
            }

            //Repeat for organ
            if (now_working_on.organ != null){
                reference_health += 1;

                limb_aggregate_damage += Math.pow(now_working_on.organ.partAggregateDamage(), 2);

                if (now_working_on.organ.isSeverelyDamaged()){
                    number_severe += 1;
                }
            }
            now_working_on = now_working_on.child;
        }
        //Square root to recover aggregate damage
        limb_aggregate_damage = Math.sqrt(limb_aggregate_damage);

        //This adds 50%-max-health damage for the first severe, 25% for the next, 12.5% for the third, and so on.
        limb_aggregate_damage += reference_health - (reference_health * Math.pow(0.5, number_severe));

        //Handles damage over limit
        if (limb_aggregate_damage > reference_health){
            return 0;
        }

        return 1 - (limb_aggregate_damage / reference_health);
    }

    public double getLimbHealth(int limb_index){
        return getLimbHealthScale(this.roots.get(limb_index));
    }

    public String getLimbName(int root_index){
        return this.species.getLimbName(root_index);
    }


    public void recoverBody(double damage_recovered){
        int numberLimbs =  this.species.numberHeads() + this.species.numberArms() + this.species.numberLegs();

        for (int i = 0; i < numberLimbs; i++){
            this.roots.get(i).recoverLimb(damage_recovered);
        }
    }




    //Species related
    interface Species {
        String getSpecies();

        int numberHeads();
        int numberArms();
        int numberLegs();

        double baseWeight();
        double baseBlood();
        double baseBloodRegen();
        double baseHealthRegen();

        double speciesSize();

        double susceptibility(int attack_type);

        String getLimbName(int root_index);
    }

    public static class Human implements Species, Serializable{
        public String getSpecies() { return "human"; }

        public int numberHeads() { return 1; }
        public int numberArms() { return 2; }
        public int numberLegs() { return 2; }

        public double baseWeight() { return 50; }
        public double baseBlood() { return 1.5; }
        public double baseBloodRegen() { return 0.2 * baseBlood(); }
        public double baseHealthRegen() { return 2; }


        public double speciesSize() { return 1; }

        public double susceptibility(int attack_type) { return 1; }

        public String getLimbName(int root_index) {
            switch (root_index) {
                case 0:
                    return "head";
                case 1:
                    return "left arm";
                case 2:
                    return "right arm";
                case 3:
                    return "left leg";
                default:
                    return "right leg";
            }
        }
    }
}