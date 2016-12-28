package com.accypiter.warriorv0_4;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private Util() {}

    public static String toString( Object o ) {
        ArrayList<String> list = new ArrayList<String>();
        Util.toString( o, o.getClass(), list );
        return o.getClass().getName().concat( list.toString() );
    }

    private static void toString( Object o, Class<?> clazz, List<String> list ) {
        Field f[] = clazz.getDeclaredFields();
        AccessibleObject.setAccessible( f, true );
        for ( int i = 0; i < f.length; i++ ) {
            try {
                list.add("\n" + f[i].getName() + "=" + f[i].get(o) );
            }
            catch ( IllegalAccessException e ) { e.printStackTrace(); }
        }
        if ( clazz.getSuperclass().getSuperclass() != null ) {
            toString( o, clazz.getSuperclass(), list );
        }
    }

    public static String capitalize(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }

    public static String ordinal(int integer){
        switch (integer){
            case 1:
                return "first";
            case 2:
                return "second";
            case 3:
                return "third";
            case 4:
                return "fourth";
            case 5:
                return "fifth";
            default:
                return "ERROR - INDEX OUT OF BOUNDS FOR UTIL.ORDINAL";
        }
    }


}