package com.finalyearproject.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by microsoft on 29-Feb-16.
 */
public class DatabaseAdapter {
    public static final String table = "MedicineData";
    public static final String COL_ID = "id";
    public static final String COL_MEDNAME = "medName";
    public static final String COL_TIME = "time";
    public static final String COL_FOOD = "food";

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DatabaseAdapter(Context context){
        this.context = context;
    }

    public DatabaseAdapter open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long insertData(String aMedName, String aTime, String aFood){
        ContentValues initialValues = createContentValues(aMedName,aTime, aFood);
        return database.insert(table,null,initialValues);
    }

    public ContentValues createContentValues(String medName,String time,String food){
        ContentValues values = new ContentValues();
        values.put(COL_MEDNAME,medName);
        values.put(COL_TIME,time);
        values.put(COL_FOOD,food);
        return values;
    }

    public Cursor getData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor res = db.rawQuery("select * from MedicineData",null);
        Log.d("Found", String.valueOf(res.getCount()));
        return res;
    }

    public Cursor getDataGivenTimeFood(String time, String food){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        Cursor res = db.rawQuery("select medName from MedicineData where time =\"" + time + "\" and food=\"" + food + "\"",null);
        Log.d("Found", String.valueOf(res.getCount()));
        return res;
    }
    public boolean deleteMedicineName(String medname) {
        return database.delete(table, COL_MEDNAME + "=\"" + medname + "\"", null) > 0;
    }
}
