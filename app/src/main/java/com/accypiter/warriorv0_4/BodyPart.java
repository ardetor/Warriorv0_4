package com.accypiter.warriorv0_4;

import android.icu.util.VersionInfo;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class BodyPart implements Serializable{

    Body.Species species;


    //Displayables
    String designation;
    String name;

    //Related Parts
    BodyPart parent;
    BodyPart child;

    //Type
    int limb_type;
    int part_type;
    int index;

    //Organ-related
    boolean isOrgan;
    BodyPart organ;


    //Related items
    Wieldable wieldable; //only allowed if child == null and type = arm for HUMAN.
    Armor armor;

    //Hit chance
    double size;

    //Damage stats
    double[] damage;               //Tracks current damage to this Part. [0] is sharp, [1] is blunt,
                                   // [2] is bleed, [3] is burned, [4] is rotting. [4] cannot be
                                   // cured by self-healing. Must be magically dealt with.


    boolean[] severeDamage;         //Tracks if severe damage has been done. If true, this limb is ineffective.
                                    //0 is sharp, 1 is blunt.

    //Characteristic stats
    double[] multiplier;           //Susceptibility of this Part to this type of damage.
                                      //Applied before damage is added to damageXYZ.


    double[] thresholdSevere;      //The damage in one hit beyond which a severe hit is triggered.
                                   //Damage will be doubled on a severe hit which toggles the relevant bool.
                                      //COUNTED BEFOREEEE MULTIPLIER

    double[] organDeferment;       //If this Part is an organ, this amount of damage will be pushed up to parent.



    String[] severeName;


    public BodyPart(Body.Species species, @Nullable BodyPart parent, String designation, int limb_type, int index){
        //Constructor for normal body parts, not for organs
        this.species = species;
        this.designation = designation;
        this.parent = parent;
        this.limb_type = limb_type;
        this.index = index;
        this.isOrgan = false;
        this.organDeferment = null;
        this.wieldable = null;
        this.armor = null;
        this.damage = new double[] {0,0,0,0,0};
        this.severeDamage = new boolean[] {false, false};

        if (this.limb_type == LIMB_ARM){
            this.severeName = new String[]{"severed","fractured"};
            switch(index){
                case 0:
                    this.name = "shoulder";
                    break;
                case 1:
                    this.name = "upper arm";
                    break;
                case 2:
                    this.name = "elbow";
                    break;
                case 3:
                    this.name = "lower arm";
                    break;
                case 4:
                    this.name = "wrist";
                    break;
                case 5:
                    this.name = "hand";
                    break;
                default:
                    this.name = "error_arm";
                    break;
            }
            if (index%2 == 0){
                this.part_type = PART_JOINT;
                this.size = (-1) - (0.4 * index);
                this.multiplier = new double[]{1.25   + (0.05  * index),
                                               1.4 + (0.1 * index),
                                               1.4 + (0.075  * index)};
                this.thresholdSevere = new double[]{5 - (0.2 * index),
                                                    4 - (0.2 * index)};

            } else {
                if (index == 5){
                    this.part_type = PART_NO_ORGAN;
                } else {
                    this.part_type = PART_SEGMENT;
                }

                this.size = (0.75) - (0.25 * index);
                this.multiplier = new double[]{0.90   + (0.05 * index),
                                               1      + (0.05 * index),
                                               0.85   + (0.05 * index)};
                this.thresholdSevere = new double[]{7.25 - (0.25 * index),
                                                    6    - (0.25 * index)};
            }

        } else if (limb_type == LIMB_LEG){
            this.severeName = new String[]{"severed","fractured"};
            switch(index){
                case 0:
                    this.name = "hip";
                    break;
                case 1:
                    this.name = "upper leg";
                    break;
                case 2:
                    this.name = "knee";
                    break;
                case 3:
                    this.name = "lower leg";
                    break;
                case 4:
                    this.name = "ankle";
                    break;
                case 5:
                    this.name = "foot";
                    break;
                default:
                    this.name = "error_leg";
                    break;
            }
            if (index%2 == 0){
                this.part_type = PART_JOINT;
                this.size = (-0.6) - (0.4 * index);
                this.multiplier = new double[]{1.15   + (0.05  * index),
                        1.25 + (0.1 * index),
                        1.25 + (0.075  * index)};
                this.thresholdSevere = new double[]{5.2 - (0.2 * index),
                        4.2 - (0.2 * index)};

            } else {
                if (index == 5){
                    this.part_type = PART_NO_ORGAN;
                } else {
                    this.part_type = PART_SEGMENT;
                }
                this.size = (1) - (0.25 * index);
                this.multiplier = new double[]{0.85   + (0.05 * index),
                                               0.9    + (0.05 * index),
                                               0.8    + (0.05 * index)};
                this.thresholdSevere = new double[]{7.5 - (0.25 * index),
                                                    6.25   - (0.25 * index)};
            }

        }

        else if (limb_type == LIMB_CORE){
            switch(index){
                case 0:
                    this.name = "torso";
                    this.part_type = PART_SEGMENT;
                    this.size = 1.5;
                    this.multiplier = new double[]{1,
                                                   1,
                                                   1};
                    this.thresholdSevere = new double[]{9,
                                                        7};
                    this.severeName = new String[]{"bisected","crushed"};
                    break;

                case 1:
                    this.name = "neck";
                    this.part_type = PART_SEGMENT;
                    this.size = -1.5;
                    this.multiplier = new double[]{1.3,
                                                   1.6,
                                                   1.2};
                    this.thresholdSevere = new double[]{5,
                                                        5};
                    this.severeName = new String[]{"decapitated","broken"};
                    break;

                case 2:
                    this.name = "head";
                    this.part_type = PART_HEAD;
                    this.size = 0.1;
                    this.multiplier = new double[]{1,
                                                   0.9,
                                                   1.3};
                    this.thresholdSevere = new double[]{8,
                                                        6};
                    this.severeName = new String[]{"bisected","fractured"};
                    break;
            }

        }

        //Accounting for species
        this.size *= species.speciesSize();

        for (int i = 0; i < 2; i++) {
            this.multiplier[i] *= species.susceptibility(i);
            this.thresholdSevere[i] /= species.susceptibility(i);
        }
        this.multiplier[2] *= species.susceptibility(2);



        //Adding organ
        if (this.part_type == PART_SEGMENT || this.part_type == PART_JOINT) {
            this.organ = new BodyPart(species, this, this.limb_type, this.part_type, this.index);
        } else {
            this.organ = null;
        }
    }

    public BodyPart(Body.Species species, BodyPart parent, int limb_type, int parent_part_type, int index){
        //Used only for organs, called by the normal constructor.
        this.species = species;
        this.parent = parent;
        this.child = null;
        this.limb_type = limb_type;
        this.index = index;
        this.isOrgan = true;
        this.organ = null;
        this.wieldable = null;
        this.armor = null;
        this.damage = new double[]{0,0,0,0,0};
        this.severeDamage = new boolean[]{false,false};

        switch(parent_part_type){
            case PART_SEGMENT:
                this.name = "artery";
                this.part_type = ORGAN_ARTERY;
                this.thresholdSevere = new double[]{9999,9999};
                this.severeName = new String[]{"error_artery_should_not_be_severable","error_artery_should_not_be_severable"};

                //Sets prefix of this organ to the full name of its parent, accounting for exception of heart
                if (parent.name.equals("torso")){
                    this.name = "heart";
                    this.designation = null;
                } else if (parent.designation == null || parent.designation.equals("")){
                    this.designation = parent.name;
                } else {
                    this.designation = parent.designation + " " + parent.name;
                }

                //Deals with heart/neck vs limb arteries
                switch (parent.limb_type){
                    case LIMB_CORE:
                        this.size = -2.5 - (1 * index);
                        this.multiplier = new double[]{0,
                                                       20 - (15 * index),
                                                       0};
                        this.organDeferment = new double[]{4 - (2.5 * index),
                                                           4 - (2.5 * index)};
                        break;

                    default:
                        this.size = -3 - (0.5 * index);
                        this.multiplier = new double[]{0,
                                                       3.5 - (0.5 * index),
                                                       0};
                        this.organDeferment = new double[]{0.5,0.5};
                        break;
                }
                break;

            case PART_JOINT:
                this.name = "ligament";
                this.part_type = ORGAN_LIGAMENT;
                this.designation = parent.designation + " " + parent.name;

                this.size = (-4) - (0.25 * index);
                this.multiplier = new double[]{0,0,0};
                this.thresholdSevere = new double[]{2.5 - (0.25 * index),
                                                    9999};
                this.organDeferment = new double[]{0,0};
                this.severeName = new String[]{"severed", "error_ligament_should_not_be_fracturable"};
                break;

        }

  /*
        name
        parttype
       size
    double[] multiplier;
 double[] thresholdSevere;      //The damage in one hit beyond which a severe hit is triggered.
  double[] organDeferment;       //If this Part is an organ, this amount of damage will be pushed up to parent.
 String[] severeName;*/

    }



    public static final int LIMB_CORE = 0;
    public static final int LIMB_ARM = 1;
    public static final int LIMB_LEG = 2;

    public static final int PART_SEGMENT = 10;
    public static final int PART_JOINT = 11;
    public static final int PART_HEAD = 12; //For now, this means "do not add organ". Later, means "add organ Eye"
    public static final int PART_NO_ORGAN = 13;
    public static final int ORGAN_ARTERY = 14;
    public static final int ORGAN_LIGAMENT = 15;


    public double getPartHealth(){
        //Returns a fraction from 0 to 1 representing the health of a particular body part.
        // 0 means disabled, 1 means perfect condition.

        if (this.isSeverelyDamaged()) {
            return 0; // If broken/severed straight away go to red
        }

        double reference_health = 10;
        double damage = this.getTotalDamage();
        if (damage > reference_health){
            damage = reference_health;
        }

        return 1 - (damage / reference_health);
    }

    public double getTotalDamage(){
        //Iterates over a BodyPart's damage statistic and returns the sum.
        //Bleeding damage only counts as one-fifth the damage.
        double damage = 0;
        for (double individual_damage : this.damage) {
            damage += individual_damage;
        }
        damage -= this.damage[2] * 0.8;
        return damage;
    }

    public boolean isSeverelyDamaged(){
        return this.severeDamage[0] || this.severeDamage[1];
    }

    public BodyPart getChild(int depth){
        if (depth == 0){
            return this;
        } else {
            return this.child.getChild(depth - 1);
        }
    }

    public String getPartName(){
        if (this.designation == null || this.designation.equals("")){
            return this.name;
        } else {
            return this.designation + " " + this.name;
        }
    }

    public String GetPartName(){
        return Util.capitalize(this.getPartName());
    }

}





























