package com.accypiter.warriorv0_4;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Instances of JournalEntry are journal entries, an ArrayList of which are stored in SaveGame.current.
 */

public class JournalEntry implements Serializable {


    Date date;
    int type;
    String message_title;
    String message_short;
    String message_long;

    JournalEntry(Date date, int type, String message_title){
        this.date = date;
        this.type = type;
        this.message_title = message_title;
        this.message_short = null;
        this.message_long = null;
    }

    JournalEntry(Date date, int type, String message_title, String message_short){
        this.date = date;
        this.type = type;
        this.message_title = message_title;
        this.message_short = message_short;
        this.message_long = null;
    }

    JournalEntry(Date date, int type, String message_title, String message_short, String message_long){
        this.date = date;
        this.type = type;
        this.message_title = message_title;
        this.message_short = message_short;
        this.message_long = message_long;
    }



    //JOURNAL ENTRY TYPES DEFINED HERE
    public static final int TYPE_NORMAL = 123874;
    public static final int TYPE_SKILL_LEVEL_UP = 435802;
    public static final int TYPE_FIGHT_WIN = 458923;
    public static final int TYPE_FIGHT_LOSE = 197209;



    int getColor(){
        int entryType = this.type;
        switch(entryType){
            case TYPE_NORMAL:
                return 0xFFDDDDDD;
            case TYPE_SKILL_LEVEL_UP:
                return 0xFFFFF9C4;
            case TYPE_FIGHT_WIN:
                return 0xFFB9F6CA;
            case TYPE_FIGHT_LOSE:
                return 0xFFF8BBD0;
            default:
                return 0xFF990000;
        }
    }
}
