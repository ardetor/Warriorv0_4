package com.accypiter.warriorv0_4;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

    private ClassUtils() {}

    public static String toString( Object o ) {
        ArrayList<String> list = new ArrayList<String>();
        ClassUtils.toString( o, o.getClass(), list );
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
}