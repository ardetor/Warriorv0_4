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
                this.size = 1.3;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 1.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 10;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 7;

                this.severeSharpName = "bisected";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case HEART:
                this.isOrgan = true;
                this.name = "heart";
                this.size = -2;

                this.multiplierSharp = species.factorSharp() * 2;
                this.multiplierHaem = species.factorHaem() * 10;
                this.multiplierBlunt = species.factorBlunt() * 2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "shredded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 4;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 4;

            case LUNGS:
                this.isOrgan = true;
                this.name = "lungs";
                this.size = -1.5;

                this.multiplierSharp = species.factorSharp() * 1.5;
                this.multiplierHaem = species.factorHaem() * 2;
                this.multiplierBlunt = species.factorBlunt() * 1.3;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "shredded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 4;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 4;

            case LIVER:
                this.isOrgan = true;
                this.name = "liver";
                this.size = -1.4;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1.2;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 4;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4;

                this.severeSharpName = "shredded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 3;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 3;

            case VISCERA:
                this.isOrgan = true;
                this.name = "viscera";
                this.size = -1.2;

                this.multiplierSharp = species.factorSharp() * 1.3;
                this.multiplierHaem = species.factorHaem() * 1.5;
                this.multiplierBlunt = species.factorBlunt() * 1.4;

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

                this.multiplierSharp = species.factorSharp() * 0.3;
                this.multiplierHaem = species.factorHaem() * 4;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5;

                this.severeSharpName = "opened";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0.5;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0.5;

            case NECK_VEIN:
                this.isOrgan = true;
                this.name = "vein (neck)";
                this.size = -3.6;

                this.multiplierSharp = species.factorSharp() * 0.3;
                this.multiplierHaem = species.factorHaem() * 2;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5;

                this.severeSharpName = "opened";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0.5;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0.5;

            case NECK_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (neck)";
                this.size = -3.7;

                this.multiplierSharp = species.factorSharp() * 0.3;
                this.multiplierHaem = species.factorHaem() * 0.2;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0.5;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0.5;

            case HEAD:
                this.isOrgan = false;
                this.name = "head";
                this.size = 0.5;

                this.multiplierSharp = species.factorSharp() * 0.9;
                this.multiplierHaem = species.factorHaem() * 0.8;
                this.multiplierBlunt = species.factorBlunt() * 1.3;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 7;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5;

                this.severeSharpName = "decapitated";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case EYE:
                this.isOrgan = true;
                this.name = "eye";
                this.size = -3.2;

                this.multiplierSharp = species.factorSharp() * 0.8;
                this.multiplierHaem = species.factorHaem() * 0.7;
                this.multiplierBlunt = species.factorBlunt() * 0.8;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4;

                this.severeSharpName = "blinded";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case NOSE:
                this.isOrgan = true;
                this.name = "nose";
                this.size = -2;

                this.multiplierSharp = species.factorSharp() * 0.4;
                this.multiplierHaem = species.factorHaem() * 0.7;
                this.multiplierBlunt = species.factorBlunt() * 1.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case JAW:
                this.isOrgan = true;
                this.name = "jaw";
                this.size = -1.5;

                this.multiplierSharp = species.factorSharp() * 0.5;
                this.multiplierHaem = species.factorHaem() * 0.5;
                this.multiplierBlunt = species.factorBlunt() * 1.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 5;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case EAR:
                this.isOrgan = true;
                this.name = "ear";
                this.size = -2.2;

                this.multiplierSharp = species.factorSharp() * 0.7;
                this.multiplierHaem = species.factorHaem() * 0.5;
                this.multiplierBlunt = species.factorBlunt() * 0.9;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case SHOULDER:
                this.isOrgan = false;
                this.name = "shoulder";
                this.size = -1.2;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1.1;
                this.multiplierBlunt = species.factorBlunt() * 1.3;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 6;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.5;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case SHOULDER_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (shoulder)";
                this.size = -3.9;

                this.multiplierSharp = species.factorSharp() * 0.2;
                this.multiplierHaem = species.factorHaem() * 0.1;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.7;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.7;

                this.severeSharpName = "severed";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case UPPER_ARM:
                this.isOrgan = false;
                this.name = "upper arm";
                this.size = 0;

                this.multiplierSharp = species.factorSharp() * 1.1;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 6.7;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5.6;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case UPPER_ARM_ARTERY:
                this.isOrgan = true;
                this.name = "artery (upper arm)";
                this.size = -3.5;

                this.multiplierSharp = species.factorSharp() * 0.7;
                this.multiplierHaem = species.factorHaem() * 3;
                this.multiplierBlunt = species.factorBlunt() * 0.4;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.5;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.5;

                this.severeSharpName = "severed";
                this.severeBluntName = "crushed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case UPPER_ARM_VEIN:
                this.isOrgan = true;
                this.name = "vein (upper arm)";
                this.size = -3.4;

                this.multiplierSharp = species.factorSharp() * 0.7;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 0.4;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.5;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.5;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case ELBOW:
                this.isOrgan = false;
                this.name = "elbow";
                this.size = -1.8;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1.2;
                this.multiplierBlunt = species.factorBlunt() * 1.5;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 5.5;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.4;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case ELBOW_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (elbow)";
                this.size = -4.1;

                this.multiplierSharp = species.factorSharp() * 0.2;
                this.multiplierHaem = species.factorHaem() * 0.1;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.6;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case LOWER_ARM:
                this.isOrgan = false;
                this.name = "lower arm";
                this.size = -0.2;

                this.multiplierSharp = species.factorSharp() * 1.2;
                this.multiplierHaem = species.factorHaem() * 1.1;
                this.multiplierBlunt = species.factorBlunt() * 1.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 6.2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5.0;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case LOWER_ARM_ARTERY:
                this.isOrgan = true;
                this.name = "artery (lower arm)";
                this.size = -4.1;

                this.multiplierSharp = species.factorSharp() * 0.5;
                this.multiplierHaem = species.factorHaem() * 2;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.2;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case LOWER_ARM_VEIN:
                this.isOrgan = true;
                this.name = "vein (lower arm)";
                this.size = -4.0;

                this.multiplierSharp = species.factorSharp() * 0.4;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.2;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case WRIST:
                this.isOrgan = false;
                this.name = "wrist";
                this.size = -1.9;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1.2;
                this.multiplierBlunt = species.factorBlunt() * 1.5;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 5.1;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3.8;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case WRIST_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (wrist)";
                this.size = -4.4;

                this.multiplierSharp = species.factorSharp() * 0.2;
                this.multiplierHaem = species.factorHaem() * 0.1;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.1;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case HAND:
                this.isOrgan = false;
                this.name = "hand";
                this.size = -0.8;

                this.multiplierSharp = species.factorSharp() * 1.3;
                this.multiplierHaem = species.factorHaem() * 1.2;
                this.multiplierBlunt = species.factorBlunt() * 1.3;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 5.7;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.9;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case FINGER_FIRST:
                this.isOrgan = true;
                this.name = "first finger";
                this.size = -2.5;

                this.multiplierSharp = species.factorSharp() * 1.1;
                this.multiplierHaem = species.factorHaem() * 0.9;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3.5;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3.3;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case FINGER_SECOND:
                this.isOrgan = true;
                this.name = "second finger";
                this.size = -2.4;

                this.multiplierSharp = species.factorSharp() * 1.1;
                this.multiplierHaem = species.factorHaem() * 0.9;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3.6;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3.4;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case FINGER_THIRD:
                this.isOrgan = true;
                this.name = "third finger";
                this.size = -2.5;

                this.multiplierSharp = species.factorSharp() * 1.1;
                this.multiplierHaem = species.factorHaem() * 0.9;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3.5;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3.3;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case FINGER_FOURTH:
                this.isOrgan = true;
                this.name = "fourth finger";
                this.size = -2.7;

                this.multiplierSharp = species.factorSharp() * 1.1;
                this.multiplierHaem = species.factorHaem() * 0.9;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3.0;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 2.8;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case THUMB:
                this.isOrgan = true;
                this.name = "thumb";
                this.size = -3;

                this.multiplierSharp = species.factorSharp() * 1.1;
                this.multiplierHaem = species.factorHaem() * 0.9;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3.5;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 3.3;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case HIP:
                this.isOrgan = false;
                this.name = "hip";
                this.size = -1.1;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1.3;
                this.multiplierBlunt = species.factorBlunt() * 1.6;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 6;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case HIP_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (hip)";
                this.size = -3.6;

                this.multiplierSharp = species.factorSharp() * 0.3;
                this.multiplierHaem = species.factorHaem() * 0.2;
                this.multiplierBlunt = species.factorBlunt() * 0.3;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case UPPER_LEG:
                this.isOrgan = false;
                this.name = "upper leg";
                this.size = 0.7;

                this.multiplierSharp = species.factorSharp() * 1.1;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 7;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 6;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case UPPER_LEG_ARTERY:
                this.isOrgan = true;
                this.name = "artery (upper leg)";
                this.size = -3.4;

                this.multiplierSharp = species.factorSharp() * 0.7;
                this.multiplierHaem = species.factorHaem() * 3;
                this.multiplierBlunt = species.factorBlunt() * 0.4;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.9;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.8;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case UPPER_LEG_VEIN:
                this.isOrgan = true;
                this.name = "vein (upper leg)";
                this.size = -3.2;

                this.multiplierSharp = species.factorSharp() * 0.7;
                this.multiplierHaem = species.factorHaem() * 1.5;
                this.multiplierBlunt = species.factorBlunt() * 0.4;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.9;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.8;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case KNEE:
                this.isOrgan = false;
                this.name = "knee";
                this.size = -1.5;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1.2;
                this.multiplierBlunt = species.factorBlunt() * 1.5;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 5.7;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.5;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case KNEE_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (knee)";
                this.size = -3.9;

                this.multiplierSharp = species.factorSharp() * 0.3;
                this.multiplierHaem = species.factorHaem() * 0.2;
                this.multiplierBlunt = species.factorBlunt() * 0.3;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.6;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.5;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case LOWER_LEG:
                this.isOrgan = false;
                this.name = "lower leg";
                this.size = 0.2;

                this.multiplierSharp = species.factorSharp() * 1.2;
                this.multiplierHaem = species.factorHaem() * 1.1;
                this.multiplierBlunt = species.factorBlunt() * 1.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 6.3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5.5;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case LOWER_LEG_ARTERY:
                this.isOrgan = true;
                this.name = "artery (lower leg)";
                this.size = -3.8;

                this.multiplierSharp = species.factorSharp() * 0.6;
                this.multiplierHaem = species.factorHaem() * 2;
                this.multiplierBlunt = species.factorBlunt() * 0.4;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.4;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.3;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case LOWER_LEG_VEIN:
                this.isOrgan = true;
                this.name = "vein (lower leg)";
                this.size = -3.6;

                this.multiplierSharp = species.factorSharp() * 0.6;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 0.4;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.4;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.3;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case ANKLE:
                this.isOrgan = false;
                this.name = "ankle";
                this.size = -1.7;

                this.multiplierSharp = species.factorSharp() * 1;
                this.multiplierHaem = species.factorHaem() * 1.2;
                this.multiplierBlunt = species.factorBlunt() * 1.7;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 5.3;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.3;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case ANKLE_LIGAMENT:
                this.isOrgan = true;
                this.name = "ligament (ankle)";
                this.size = -4;

                this.multiplierSharp = species.factorSharp() * 0.3;
                this.multiplierHaem = species.factorHaem() * 0.2;
                this.multiplierBlunt = species.factorBlunt() * 0.3;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.4;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.3;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case FOOT:
                this.isOrgan = false;
                this.name = "foot";
                this.size = -0.55;

                this.multiplierSharp = species.factorSharp() * 1.2;
                this.multiplierHaem = species.factorHaem() * 1;
                this.multiplierBlunt = species.factorBlunt() * 1.1;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 6;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 5;

                this.severeSharpName = "severed";
                this.severeBluntName = "fractured";

                this.organDefermentSharp = 1 / species.factorSharp() * 0;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 0;

            case FOOT_ARTERY:
                this.isOrgan = true;
                this.name = "artery (foot)";
                this.size = -4.1;

                this.multiplierSharp = species.factorSharp() * 0.5;
                this.multiplierHaem = species.factorHaem() * 1.5;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.2;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;

            case FOOT_VEIN:
                this.isOrgan = true;
                this.name = "vein (foot)";
                this.size = -4.0;

                this.multiplierSharp = species.factorSharp() * 0.5;
                this.multiplierHaem = species.factorHaem() * 0.7;
                this.multiplierBlunt = species.factorBlunt() * 0.2;

                this.thresholdSevereSharp = 1 / species.factorSharp() * 2.2;
                this.thresholdSevereBlunt = 1 / species.factorBlunt() * 4.2;

                this.severeSharpName = "severed";
                this.severeBluntName = "smashed";

                this.organDefermentSharp = 1 / species.factorSharp() * 1;
                this.organDefermentBlunt = 1 / species.factorBlunt() * 1;


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
    public static final int EAR = 13;
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





























