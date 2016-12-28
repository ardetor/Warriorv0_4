package com.accypiter.warriorv0_4;

import android.icu.util.VersionInfo;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class BodyPart{

    //Related Parts
    BodyPart parent;
    BodyPart child;

    Body.Species species;

    //Organ-related
    boolean isOrgan;
    ArrayList<BodyPart> organs; //is empty IFF Bodypart is an organ. All normal parts have organs.

    //Displayables
    String designation;
    String name;

    //Limb type
    int type;

    //Related items
    Wieldable wieldable; //only allowed if child == null and type = arm for HUMAN.
    Armor armor;

    //Hit chance
    double size;

    double damageSharp;               //Tracks current damage to this Part
    double damageHaem;
    double damageBlunt;

    double multiplierSharp;           //Susceptibility of this Part to this type of damage.
    double multiplierHaem;            //Applied before damage is added to damageXYZ.
    double multiplierBlunt;

    double thresholdSevereSharp;      //The damage in one hit beyond which a severe hit is triggered.
    double thresholdSevereBlunt;      //Damage will be doubled on a severe hit which toggles the relevant bool.
                                      //COUNTED BEFOREEEE MULTIPLIER

    boolean isSevereSharp;            //Tracks if severe damage has been done. If true, this limb is ineffective.
    boolean isSevereBlunt;

    String severeSharpName;
    String severeBluntName;

    double organDefermentSharp;       //If this Part is an organ, this amount of damage will be pushed up to parent.
    double organDefermentBlunt;


    /**
     *
     * species effects
     */


    public BodyPart(@Nullable BodyPart parent, Body.Species species, int type, @Nullable String designation){
        this.parent = parent;
        this.child = null;

        this.species = species;

        this.organs = new ArrayList<BodyPart>();

        this.designation = designation;

        this.type = type;

        this.wieldable = null;
        this.armor = null;

        this.damageSharp = 0;
        this.damageHaem = 0;
        this.damageBlunt = 0;

        this.isSevereSharp = false;
        this.isSevereBlunt = false;

        switch(type){
            case TORSO:
                this.isOrgan = false;
                this.name = "torso";
                this.size = 1;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 1.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 9;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4;

                this.severeSharpName = "bisected";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case HEART:
                this.isOrgan = true;
                this.name = "heart";
                this.size = -2;

                this.multiplierSharp = species.factorSharp() * 5;
                this.multiplierHaem = species.factorHaem() * 10;
                this.multiplierBlunt = species.factorBlunt() * 5;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "shredded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 3;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 3;

            case LUNGS:
                this.isOrgan = true;
                this.name = "lungs";
                this.size = -1.5;

                this.multiplierSharp = species.factorSharp() * 4;
                this.multiplierHaem = species.factorHaem() * 8;
                this.multiplierBlunt = species.factorBlunt() * 5;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "shredded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 3;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 3;

            case LIVER:
                this.isOrgan = true;
                this.name = "liver";
                this.size = -1.4;

                this.multiplierSharp = species.factorSharp() * 2;
                this.multiplierHaem = species.factorHaem() * 2.3;
                this.multiplierBlunt = species.factorBlunt() * 2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "shredded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 3;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 3;

            case VISCERA:
                this.isOrgan = true;
                this.name = "viscera";
                this.size = -1.2;

                this.multiplierSharp = species.factorSharp() * 1.8;
                this.multiplierHaem = species.factorHaem() * 2;
                this.multiplierBlunt = species.factorBlunt() * 1.6;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3.5;

                this.severeSharpName = "shredded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1.4;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case NECK:
                this.isOrgan = false;
                this.name = "neck";
                this.size = -2;

                this.multiplierSharp = species.factorSharp() * 1.5;
                this.multiplierHaem = species.factorHaem() * 2;
                this.multiplierBlunt = species.factorBlunt() * 1.5;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 4;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "severed";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case NECK_ARTERY:
                this.isOrgan = true;
                this.name = "artery (neck)";
                this.size = -3.7;

                this.multiplierSharp = species.factorSharp() * 2;
                this.multiplierHaem = species.factorHaem() * 5;
                this.multiplierBlunt = species.factorBlunt() * 1.5;

                this.thresholdSevereSharp = species.factorSharp() * 3;
                this.thresholdSevereBlunt = species.factorBlunt() * 4;

                this.severeSharpName = "opened";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 0.5;
                this.organDefermentBlunt = 0.5;

            case NECK_VEIN:
                this.isOrgan = true;
                this.name = "vein (neck)";
                this.size = -3.6;

                this.multiplierSharp = species.factorSharp() * 2;
                this.multiplierHaem = species.factorHaem() * 3;
                this.multiplierBlunt = species.factorBlunt() * 1.5;

                this.thresholdSevereSharp = species.factorSharp() * 2;
                this.thresholdSevereBlunt = species.factorBlunt() * 3;

                this.severeSharpName = "opened";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 0.5;
                this.organDefermentBlunt = 0.5;

            case NECK_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (neck)";
                this.size = -3.7;

                this.multiplierSharp = species.factorSharp() * 1.2;
                this.multiplierHaem = species.factorHaem() * 0.5;
                this.multiplierBlunt = species.factorBlunt() * 0.8;

                this.thresholdSevereSharp = species.factorSharp() * 3;
                this.thresholdSevereBlunt = species.factorBlunt() * 5;

                this.severeSharpName = "severed";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 0.5;
                this.organDefermentBlunt = 0.5;

            case HEAD:
                this.isOrgan = false;
                this.name = "head";
                this.size = 0.5;

                this.multiplierSharp = species.factorSharp() * 0.9;
                this.multiplierHaem = species.factorHaem() * 0.8;
                this.multiplierBlunt = species.factorBlunt() * 1.3;

                this.thresholdSevereSharp = species.factorSharp() * 5;
                this.thresholdSevereBlunt = species.factorBlunt() * 3;

                this.severeSharpName = "decapitated";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 0;
                this.organDefermentBlunt = 0;

            case EYE:
                this.isOrgan = true;
                this.name = "eye";
                this.size = -3;

                this.multiplierSharp = species.factorSharp() * 1.5;
                this.multiplierHaem = species.factorHaem() * 0.9;
                this.multiplierBlunt = species.factorBlunt() * 1.3;

                this.thresholdSevereSharp = species.factorSharp() * 1;
                this.thresholdSevereBlunt = species.factorBlunt() * 3;

                this.severeSharpName = "blinded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 0;
                this.organDefermentBlunt = 0;













































        }



        //toadd isorgan, name, size, multiplier, threshold, severe_name, organdefer
    }






    //BODYPART TYPES
    public static final int TORSO = 0;
    public static final int HEART = 1;
    public static final int LUNGS = 2;
    public static final int LIVER = 3;
    public static final int VISCERA = 4;
    public static final int NECK = 5;
    public static final int NECK_ARTERY = 6;
    public static final int NECK_VEIN = 7;
    public static final int NECK_LIGAMENT = 8;
    public static final int HEAD = 9;
    public static final int EYE = 10;
    public static final int NOSE = 11;
    public static final int JAW = 12;
    public static final int EARS = 13;
    public static final int SHOULDER = 14;
    public static final int SHOULDER_LIGAMENT = 15;
    public static final int UPPER_ARM = 16;
    public static final int UPPER_ARM_ARTERY = 17;
    public static final int UPPER_ARM_VEIN = 18;
    public static final int ELBOW = 19;
    public static final int ELBOW_LIGAMENT = 20;
    public static final int LOWER_ARM = 21;
    public static final int LOWER_ARM_ARTERY = 22;
    public static final int LOWER_ARM_VEIN = 23;
    public static final int WRIST = 24;
    public static final int WRIST_LIGAMENT = 25;
    public static final int HAND = 26;
    public static final int FINGER_FIRST = 27;
    public static final int FINGER_SECOND = 28;
    public static final int FINGER_THIRD = 29;
    public static final int FINGER_FOURTH = 30;
    public static final int THUMB = 31;
    public static final int HIP = 32;
    public static final int HIP_LIGAMENT = 33;
    public static final int UPPER_LEG = 34;
    public static final int UPPER_LEG_ARTERY = 35;
    public static final int UPPER_LEG_VEIN = 36;
    public static final int KNEE = 37;
    public static final int KNEE_LIGAMENT = 38;
    public static final int LOWER_LEG = 39;
    public static final int LOWER_LEG_ARTERY = 40;
    public static final int LOWER_LEG_VEIN = 41;
    public static final int ANKLE = 42;
    public static final int ANKLE_LIGAMENT = 43;
    public static final int FOOT = 44;
    public static final int FOOT_ARTERY = 45;
    public static final int FOOT_VEIN = 46;


}





























