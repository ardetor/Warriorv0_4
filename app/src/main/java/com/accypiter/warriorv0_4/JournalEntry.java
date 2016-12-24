package com.accypiter.warriorv0_4;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Instances of JournalEntry are journal entries, an ArrayList of which are stored in SaveGame.current.
 */

public class JournalEntry implements Serializable {
    public static int TYPE_SKILL_LEVEL_UP = 0;


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

}
