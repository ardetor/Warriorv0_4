package com.accypiter.warriorv0_4;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

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

    public static int invertedColorScale(double number, double reference, int hex_alpha){
        int red, green;

        if (number / reference < 0){
            red = 0x0;
            green = 0xFF;

        } else if (number / reference > 1){
            green = 0x0;
            red = 0xFF;

        } else if (number / reference <= 0.5){
            green = 0xFF;
            red = (int) ((number / reference) * 2 * 0xFF);

        } else {
            red = 0xFF;
            green = (int) ((1 - (number / reference)) * 2 * 0xFF);
        }

        return (hex_alpha * 0x1000000) + (red * 0x10000) + (green * 0x100);
    }

    public static int invertedColorScale(double number, int hex_alpha){
        return invertedColorScale(number, 1, hex_alpha);
    }

    public static int colorScale(double number, double reference, int hex_alpha){
        int red, green;

        if (number / reference > 1){
            red = 0x0;
            green = 0xFF;

        } else if (number / reference < 0){
            green = 0x0;
            red = 0xFF;

        } else if (number / reference <= 0.5){
            red = 0xFF;
            green = (int) ((number / reference) * 2 * 0xFF);

        } else {
            green = 0xFF;
            red = (int) ((1 - (number / reference)) * 2 * 0xFF);
        }

        return (hex_alpha * 0x1000000) + (red * 0x10000) + (green * 0x100);
    }

    public static int colorScale(double number, int hex_alpha){
        return colorScale(number, 1, hex_alpha);
    }

    public static void setTextSizeSP(Context context, TextView textView, double size_in_sp){
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) (size_in_sp));
    }

    public static double getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getDamageTypeImage(int damage_type){
        switch (damage_type){
            case 0:
                return R.drawable.icon_sharp;
            case 1:
                return R.drawable.icon_blunt;
            case 2:
                return R.drawable.icon_blood;
            case 3:
                return R.drawable.icon_burn;
            default:
                return R.drawable.icon_dark;

        }
    }

}