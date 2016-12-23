package com.accypiter.warriorv0_4;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The general class for bodies and health.
 */

public class Body implements Serializable {

    Species species;

    public static class Species {
        public static final int HUMAN = 0;
        public static final int WOLF = 1;
    }

    public static class Part {

    }


}
