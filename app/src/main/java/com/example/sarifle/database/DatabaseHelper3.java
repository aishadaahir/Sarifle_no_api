package com.example.sarifle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper3 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dataprofile.db";
    public static final String TABLE_NAME = "profile_data";
    public static final String COL1 = "Name";
    public static final String COL2 = "Tell";
    public static final String COL3 = "lang";
    public static final String COL4 = "sarifle";
    public static final String COL5 = "image";



    public DatabaseHelper3 (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( Name TEXT, " +
                " Tell TEXT, " +
                " lang TEXT, " +
                " sarifle TEXT, " +
                " image TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public boolean addData(String item1,String item2,String item3,String item4,String item5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item1);
        contentValues.put(COL2, item2);
        contentValues.put(COL3, item3);
        contentValues.put(COL4, item4);
        contentValues.put(COL5, item5);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updatedata(String item1,String item2,String item3,String item4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item1);
        contentValues.put(COL2, item2);
        contentValues.put(COL3, item3);
        contentValues.put(COL5, item4);

        long result = db.update(TABLE_NAME, contentValues,null,null);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updateclo4(String item1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, item1);

        long result = db.update(TABLE_NAME, contentValues,null,null);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor read(){
        String query = "SELECT "+"Sarifle"+" FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor read2(){
        String query = "SELECT "+"lang"+" FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public boolean delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, null, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
