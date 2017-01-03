package com.ardetor.warriorv0_4;

import java.io.Serializable;

public class Skill implements Serializable {

    public static double[] initializeSkill(){
        return new double[] {0,0,0,0,0,0};
    }

    public static double[] initializeMana(){
        return new double[] {6,6,6,6};
    }




    public static double getManaLevel(int mana_type){
        //XP to next level is proportional to level. i.e. follows triangle number sequence.
        //This formula is the positive solution to the quadratic equation:
        // XP = k * 0.5 * (level)(level-1)

        double multiplier = 2;

        return 0.5 + Math.sqrt(0.25 + (2 * multiplier * SaveGame.current.mana[mana_type]));
    }

    public static int getIntManaLevel(int mana_type){
        return (int) getManaLevel(mana_type);
    }

    public static double getManaLevel(){
        //Returns the sum of all Mana levels
        //IS A DOUBLE - mana is continuous; only consumption is discrete
        double totalMana = 0;
        for (double each_mana : SaveGame.current.mana){
            totalMana += each_mana;
        }
        return totalMana;
    }

    public static void addManaXP(int mana_type, int mana_spent){
        SaveGame.current.mana[mana_type] += mana_spent;
    }

    public static double getSkillLevel(int skill_type){
        /**THIS IS THE FORMULA FOR GETTING LEVEL FROM XP
         *PROPERTIES:
         *  - CONTINUOUS
         *  - LEVELS START AT ZERO
         *  - SETTABLE AMOUNT OF XP FROM LEVEL ZERO TO LEVEL ONE
         *  - XP REQUIRED FOR NEXT LEVEL IS IS (MULTIPLIER) TIMES HIGHER
         *  For example, if it requires 27XP to level from 0 to 1, It now requires 81XP to
         *  reach level 2 from 1.
         */
        double k = 3; //SKILL_XP_MULTIPLIER_PER_LEVEL;
        double A = 27; //SKILL_XP_TO_LEVEL_ONE;

        return Math.log((SaveGame.current.skill[skill_type]/A)*(k - 1) + 1) / Math.log(k);

    }



    public static final int ANIMA = 0;
    public static final int INERTIA = 1;
    public static final int ETHERIA = 2;
    public static final int MATERIA = 3;

    public static final int INTELLIGENCE = 0;
    public static final int ACCURACY = 1;
    public static final int DODGE = 2;
    public static final int STRENGTH = 3;
    public static final int SPEED = 4;
    public static final int BLOCK = 5;
}
