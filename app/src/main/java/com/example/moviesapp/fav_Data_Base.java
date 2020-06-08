package com.example.moviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class fav_Data_Base extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "Locations.db";
    public static final String TABLE_NAME = "Locations_table";
    public static final String ID = "ID";
    public static final String COL_1 = "originalTitle";
    public static final String COL_2 = "avatar";
    public static final String COL_3 = "overview";
    public static final String COL_4 = "releaseDate";
    public static final String COL_5 = "vote_Average";



    public fav_Data_Base(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_NAME + " (originalTitle TEXT PRIMARY KEY," +
                "  avatar TEXT, overview TEXT, releaseDate TEXT, vote_Average TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String OriginalTitle, String Avatar, String Overview,String ReleaseDate,String Vote_Average){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_1,OriginalTitle);
        contentvalues.put(COL_2,Avatar);
        contentvalues.put(COL_3,Overview);
        contentvalues.put(COL_4,ReleaseDate);
        contentvalues.put(COL_5,Vote_Average);

        long result = db.insert(TABLE_NAME,null, contentvalues);
        if (result == (-1)) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +TABLE_NAME,null);
        return result;

    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME, "originalTitle = ?",new String[] {id});
    }


}