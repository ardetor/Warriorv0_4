package com.accypiter.warriorv0_4;


import android.content.Context;

import java.io.FileOutputStream;

public class SaveGame {
    public static SaveGame current;
    public static final String file_name = "savegame.sav";
    int testStat;



    //Constructor
    SaveGame(){
        this.testStat = 1;
    }

    public void save(Context context){

    }
}
