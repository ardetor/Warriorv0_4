package com.ardetor.warriorv0_4;

import java.io.Serializable;

public class Skills implements Serializable {
    //Configurable constants



    //These are all the raw XP levels.
    //Conversion to effectiveness/level is continuous, not discrete, and is done at the function level.
    public double[] mana; //Carries 4 doubles, representing agnitia, inertia, etheria, materia


    public double[] skill;



    //Constructor
    Skills(){
        this.mana = new double[] {6,6,6,6}; //Magics start at level 3 each out of 32

        this.skill = new double[] {0,0,0,0,0,0};
    }

    public double getManaLevel(int mana_type){
        //XP to next level is proportional to level. i.e. follows triangle number sequence.
        //This formula is the positive solution to the quadratic equation:
        // XP = k * 0.5 * (level)(level-1)

        double multiplier = 2;

        return 0.5 + Math.sqrt(0.25 + (2 * multiplier * mana[mana_type]));
    }

    public double getIntManaLevel(int mana_type){
        return (int) getManaLevel(mana_type);
    }

    public double getManaLevel(){
        //Returns the sum of all Mana levels
        double totalMana = 0;
        for (double each_mana : this.mana){
            totalMana += each_mana;
        }
        return totalMana;
    }

    public void giveManaXP(int mana_type, int mana_spent){
        this.mana[mana_type] += mana_spent;
    }

    public double getSkillLevel(int skill_type){
        /**THIS IS THE FORMULA FOR GETTING LEVEL FROM XP
         *PROPERTIES:
         *  - CONTINUOUS
         *  - LEVELS START AT ZERO
         *  - SETTABLE AMOUNT OF XP FROM LEVEL ZERO TO LEVEL ONE
         *  - XP REQUIRED FOR NEXT LEVEL IS IS (MULTIPLIER) TIMES HIGHER
         *  For example, if it requires 27XP to level from 0 to 1, It now requires 81XP to
         *  reach level 2 from 1.
         */
        double SKILL_XP_MULTIPLIER_PER_LEVEL = 3;
        double SKILL_XP_TO_LEVEL_ONE = 27;

        double k = SKILL_XP_MULTIPLIER_PER_LEVEL;
        double A = SKILL_XP_TO_LEVEL_ONE;

        return Math.log((this.skill[skill_type]/A)*(k - 1) + 1) / Math.log(k);

    }



    public static final int AGNITIA = 0;
    public static final int INERTIA = 1;
    public static final int ETHERIA = 2;
    public static final int MATERIA = 3;

    public static final int ACCURACY = 0;
    public static final int DODGE = 1;
    public static final int INTELLIGENCE = 2;
    public static final int STRENGTH = 3;
    public static final int SPEED = 4;
    public static final int BLOCK = 5;
}
