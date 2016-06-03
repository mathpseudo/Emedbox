package com.finalyearproject.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by microsoft on 29-Feb-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MedicinesData";
    public static final int DB_VERSION = 1;

    public static final String createTable ="create table MedicineData(medName varchar not null, time varchar not null, food varchar not null);";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all data");
        database.execSQL("DROP TABLE IF EXISTS MedicineData");
        onCreate(database);
    }
}
