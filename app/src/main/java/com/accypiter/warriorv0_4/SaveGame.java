package com.accypiter.warriorv0_4;


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

public class SaveGame implements Serializable{
    public static SaveGame current;
    public static final String file_name = "savegame.sav";

    //General game mechanics
    Date date_created;
    Date date_latest;
    Date date_current;
    boolean in_fight;
    boolean magic_enabled;

    //Journal
    ArrayList <JournalEntry> journal;

    //Body object, representing all info about physical health
    Body body;


    //Health debugging, to be deleted later
    double health_max;
    double health_body;
    double health_current;



    //Constructor
    public SaveGame(){
        //General game mechanics
        this.date_created = new Date();
        this.date_current = new Date();
        this.date_latest = new Date();
        this.in_fight = false;
        this.magic_enabled = true;

        //Journal
        this.journal = new ArrayList<JournalEntry>();

        //Body creation
        this.body = new Body(Body.SPECIES_HUMAN);





        //Health debugging -- to be deleted
        this.health_max = 100.;
        this.health_body = 100.;
        this.health_current = 100.;

    }

    public static boolean save(Context context){
        //Returns true if successful, false if it fails.
        try {
            FileOutputStream fileOut = context.openFileOutput(SaveGame.file_name, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(SaveGame.current);
            out.close();
            fileOut.close();
        }catch(IOException i) {
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
        //Update date statistics
        updateDateCurrent();
        updateDateLatest();
        //Prune journal entries
        pruneJournal(context);

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

}
