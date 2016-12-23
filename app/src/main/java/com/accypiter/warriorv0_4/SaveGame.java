package com.accypiter.warriorv0_4;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class SaveGame implements Serializable{
    static SaveGame current;
    static final String file_name = "savegame.sav";

    //General game mechanics
    boolean in_fight;

    //Health
    double health_max;
    double health_body;
    double health_current;



    //Constructor
    SaveGame(){
        //General game mechanics
        this.in_fight = false;


        //Health
        this.health_max = 100.;
        this.health_body = 100.;
        this.health_current = 100.;

    }

    static boolean save(Context context){
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

    static boolean load(Context context){
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

    static void startNewGame(Context context){
        SaveGame newSaveGame = new SaveGame();
        SaveGame.current = newSaveGame;
        try {
            FileOutputStream fileOut = context.openFileOutput(SaveGame.file_name, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(newSaveGame);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();

        }
    }

}
