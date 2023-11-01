package com.example.sarifle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_NAME = "acountant_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "Name";
    public static final String COL3 = "Tell";
    public static final String COL4 = "Tell2";
    public static final String COL5 = "Accountnum";
    public static final String COL6 = "Datereg";
    public static final String COL7 = "Sarifle";


    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( ID TEXT, " +
                " Name TEXT, " +
                " Sarifle TEXT, " +
                " Tell TEXT, " +
                " Tell2 TEXT, " +
                " Accountnum TEXT, " +
                " Datereg TEXT)";
        db.execSQL(createTable);
    }

    public void insertInitialData(SQLiteDatabase db) {
        delete();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, "1");
        contentValues.put(COL2, "mohamed");
        contentValues.put(COL3, "252617515720");
        contentValues.put(COL4, "252634437999");
        contentValues.put(COL5, "340204");
        contentValues.put(COL6, "10/25/2023");
        contentValues.put(COL7, "mohamed sarifle");


        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL1, "2");
        contentValues2.put(COL2, "alia");
        contentValues2.put(COL3, "252617515720");
        contentValues2.put(COL4, "252634435541");
        contentValues2.put(COL5, "340204");
        contentValues2.put(COL6, "10/25/2023");
        contentValues2.put(COL7, "ali sarifle");

        db.insert(TABLE_NAME, null, contentValues);
        db.insert(TABLE_NAME, null, contentValues2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item1,String item2,String item3,String item4,String item5,String item6,String item7) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item1);
        contentValues.put(COL2, item2);
        contentValues.put(COL3, item3);
        contentValues.put(COL4, item4);
        contentValues.put(COL5, item5);
        contentValues.put(COL6, item6);
        contentValues.put(COL7, item7);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
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

    public Cursor readsearch(String searchKeyword){

        String query = "SELECT * FROM " + TABLE_NAME+" WHERE "+COL2+" LIKE ? OR "+COL3+" LIKE ? OR "+COL4+" LIKE ? OR "+COL5+" LIKE ? OR "+COL7+" LIKE ?";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] searchArgs = {"%" + searchKeyword + "%", "%" + searchKeyword + "%", "%" + searchKeyword + "%", "%" + searchKeyword + "%", "%" + searchKeyword + "%"};


        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, searchArgs);
        }
        return cursor;
    }

    public boolean updateData(String row_id,String item1,String item2,String item3,String item4,String item5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);
        contentValues.put(COL5, item4);
        contentValues.put(COL6, item5);

        long result = db.update(TABLE_NAME, contentValues, "ID=?", new String[]{row_id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "ID=?", new String[]{row_id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
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
