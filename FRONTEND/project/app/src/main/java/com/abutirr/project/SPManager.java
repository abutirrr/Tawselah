package com.abutirr.project;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {

    private static SharedPreferences SP ;
    private static final String SPName ="shared preference";

    public static final String USERID = "USERID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String EMAIL = "EMAIL";
    public static final String TYPEID ="TYPEID";
    public static final String DATEOFBIRTH = "DATEOFBIRTH";
    public static final String PHONENUMBER = "PHONENUMBER";
    public static final String GENDER = "GENDER";
    public static final String DRIVERID = "DRIVERID";


    private SPManager(){

    }

    public static void init (Context context){
        if (SP == null){
            SP = context
                    .getApplicationContext()
                    .getSharedPreferences(SPName,Context.MODE_PRIVATE);
        }
    }

    public static void save(String Key , String Value){
        SharedPreferences.Editor editor= SP.edit();
        editor.putString(Key,Value);
        editor.apply();
    }
    public static void save(String Key , Integer Value){
        SharedPreferences.Editor editor= SP.edit();
        editor.putInt(Key,Value);
        editor.apply();
    }

    public static String read (String Key , String defValue){
        return SP.getString(Key,defValue);
    }

    public static int read (String Key , Integer defValue){
        return SP.getInt(Key,defValue);
    }

    public static boolean isLogged(){

       return SP.getInt(USERID,-1) != -1 ;
    }

    public static void signOut(){
        SharedPreferences.Editor editor = SP.edit();
        editor.clear();
        editor.apply();

    }


}
