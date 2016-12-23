package com.accypiter.warriorv0_4;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Instances of JournalEntry are journal entries, an ArrayList of which are stored in SaveGame.current.
 */

public class JournalEntry implements Serializable {

    Date date;
    String message_short;
    String message_long;

}
