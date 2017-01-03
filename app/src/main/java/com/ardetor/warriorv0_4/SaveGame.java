package com.ardetor.warriorv0_4;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SaveGame implements Serializable{
    public static SaveGame current;
    public static final String file_name = "savegame.sav";

    //General game mechanics
    Date date_created;
    Date date_latest;
    Date date_current;

    boolean magic_enabled;
    boolean in_fight;

    Random random;

    //Journal
    ArrayList <JournalEntry> journal;

    //Body object, representing all info about physical health
    Body body;

    //Skill
    //This and mana are the raw XP levels.
    //Conversion to effectiveness/level is continuous, not discrete, and is done at the function level.
    double[] skill; //Carries 6 skills, representing, in order, Intelligence, Accuracy, Dodge,
                    // Strength, Speed, Block.
                    //Access through Skill.ACCURACY, etc.

    //Mana
    double[] mana;  //Carries 4 doubles, representing agnitia, inertia, etheria, materia
                    //Access through Skill.ANIMA, Skill.INERTIA, Skill.ETHERIA, Skill.MATERIA

    double mana_max;
    double mana_current;



    //Constructor
    public SaveGame(){
        //General game mechanics
        this.date_created = new Date();
        this.date_current = new Date();
        this.date_latest = new Date();
        this.in_fight = false;
        this.magic_enabled = true;
        this.random = new Random();

        //Journal
        this.journal = new ArrayList<JournalEntry>();

        //Body creation
        this.body = new Body(new Body.Human());

        //Initialize skills and mana
        this.skill = Skill.initializeSkill();
        this.mana = Skill.initializeMana();

        this.mana_max = 12;
        this.mana_current = this.mana_max;
    }







    public static boolean save(Context context){
        //Returns true if successful, false if it fails.
        try {
            FileOutputStream fileOut = context.openFileOutput(SaveGame.file_name, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(SaveGame.current);
            out.close();
            fileOut.close();
        }
        catch(IOException i) {
            i.printStackTrace();
        }
        return true;
    }

    public static boolean load(Context context){
        //Returns true if successful, false if it fails.
        try {
            FileInputStream fileIn = context.openFileInput(SaveGame.file_name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveGame.current = (SaveGame) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            return false;
        } catch (ClassNotFoundException|IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void startNewGame(Context context){
        SaveGame newSaveGame = new SaveGame();
        SaveGame.current = newSaveGame;
        SaveGame.save(context);
    }

    public static SaveGame update(Context context){
        /*
         * Updates SaveGame.current with the current time and writes to file.
         * Also returns SaveGame.current, so as simultaneously give Activities a local reference to it
         */

        //Get time since previous update, for use in healing and bleed damage
        //In milliseconds (a little overkill but whatever man)
        long time_elapsed = new Date().getTime() - SaveGame.current.date_current.getTime();
        //Update date statistics - done immediately to shortchange the minimum possible
        updateDateCurrent();
        updateDateLatest();

        //Prune journal entries
        pruneJournal(context);

        recoverHealth(time_elapsed);
        //TODO: All other time-based effects to be updated should go here. E.g. health, mana...


        save(context);
        return SaveGame.current;
    }

    public static void updateDateCurrent(){
        SaveGame.current.date_current = new Date();
    }

    public static void updateDateLatest(){
        if (SaveGame.current.date_current.after(SaveGame.current.date_latest)){
            SaveGame.current.date_latest = SaveGame.current.date_current;
        }
    }

    public static void addJournalEntry(Context context, JournalEntry journalEntry){
        SaveGame.current.journal.add(0, journalEntry);
        pruneJournal(context);
    }

    public static void pruneJournal(Context context){
        //If entries in journal are more than preferences, remove oldest until within limit.
        //Get preferred limit from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int preferredJournalCapacity = Integer.parseInt(sharedPreferences.getString("preference_journal_capacity", "100"));

        while (SaveGame.current.journal.size() > preferredJournalCapacity){
            SaveGame.current.journal.remove(SaveGame.current.journal.size() - 1);
        }
    }

    public static void recoverHealth(long time_elapsed){
        //DEFINE TIME PERIOD HERE
        //1 HOUR
        long time_reference = 1000 * 10;
        //long time_reference = 1000 * 60 * 60; //Production value is 1 hour, debugging value is 10 seconds.
        double time_normalized = time_elapsed / time_reference;

        //If no modifier is affecting healing rate,
        if (SaveGame.current.body.health_regen_variable <= 0 || SaveGame.current.body.health_regen_variable_duration <= 0){
            //First ensure regen variable and regen variable duration are zero,
            SaveGame.current.body.health_regen_variable = 0;
            SaveGame.current.body.health_regen_variable_duration = 0;

            //Calculate the amount supposed to heal
            double heal_amount = SaveGame.current.body.health_regen_base * time_normalized;

            //Heal by that amount
            SaveGame.current.body.recoverBody(heal_amount);
        }
        //Else if there is a modifier,
        //If time elapsed is within range
        else if (SaveGame.current.body.health_regen_variable_duration > time_elapsed){
            //Subtract spent time from duration
            SaveGame.current.body.health_regen_variable_duration -= time_elapsed;

            //Get regen rate modified by variable rate
            double modified_healing_rate = SaveGame.current.body.health_regen_base * (1 + SaveGame.current.body.health_regen_variable);

            //Heal for amount
            SaveGame.current.body.recoverBody(modified_healing_rate * time_normalized);
        }
        //Else, healing will expire within time_elapsed
        else {
            //First handle enhanced heal, separately from normal heal. Not stacked.
            double bonus_time_normalized = SaveGame.current.body.health_regen_variable_duration / time_reference;
            double bonus_healing_rate = SaveGame.current.body.health_regen_base * (SaveGame.current.body.health_regen_variable);

            //Execute bonus heal
            SaveGame.current.body.recoverBody(bonus_healing_rate * bonus_time_normalized);

            //Reset healing bonus and healing rate duration to zero
            SaveGame.current.body.health_regen_variable = 0;
            SaveGame.current.body.health_regen_variable_duration = 0;

            //Proceed to heal normally with full duration
            //Calculate the amount supposed to heal
            double heal_amount = SaveGame.current.body.health_regen_base * time_normalized;

            //Heal by that amount
            SaveGame.current.body.recoverBody(heal_amount);
        }

    }

}
