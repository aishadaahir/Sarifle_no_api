package com.example.sarifle.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "datalist.db";
    public static final String TABLE_NAME = "acountant_data_list";
    public static final String COL1 = "ID";
    public static final String COL7 = "position";
    public static final String COL2 = "Name";
    public static final String COL3 = "Tell";
    public static final String COL4 = "Tell2";
    public static final String COL5 = "Accountnum";
    public static final String COL6 = "Sarifle";


    public DatabaseHelper2 (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( position INTEGER  DEFAULT 0, " +
                " ID TEXT, " +
                " Name TEXT, " +
                " Sarifle TEXT, " +
                " Tell TEXT, " +
                " Tell2 TEXT, " +
                " Accountnum TEXT)";

        db.execSQL(createTable);
    }

    public void insertInitialData(SQLiteDatabase db) {
//        delete();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, "1");
        contentValues.put(COL2, "mohamed");
        contentValues.put(COL3, "252617515720");
        contentValues.put(COL4, "252634437999");
        contentValues.put(COL5, "340204");
        contentValues.put(COL6, "mohamed sarifle");
        String rowIdQuery = "SELECT MAX(rowid) FROM " + TABLE_NAME;
        Cursor rowIdCursor = db.rawQuery(rowIdQuery, null);
        long rowId = -1;
        if (rowIdCursor.moveToFirst()) {
            rowId = rowIdCursor.getLong(0);
        }
        rowIdCursor.close();

        contentValues.put("position", (rowId+1));
        db.insert(TABLE_NAME, null, contentValues);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL1, "2");
        contentValues2.put(COL2, "alia");
        contentValues2.put(COL3, "252617515720");
        contentValues2.put(COL4, "252634435541");
        contentValues2.put(COL5, "340204");
        contentValues2.put(COL6, "ali sarifle");
        String rowIdQuery2 = "SELECT MAX(rowid) FROM " + TABLE_NAME;
        Cursor rowIdCursor2 = db.rawQuery(rowIdQuery2, null);
        long rowId2 = -1;
        if (rowIdCursor2.moveToFirst()) {
            rowId2 = rowIdCursor2.getLong(0);
        }
        rowIdCursor2.close();

        contentValues2.put("position", (rowId2+1));


        db.insert(TABLE_NAME, null, contentValues2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    @SuppressLint("Range")
    public void moveItemToFirstPosition(long itemId) {

        if(itemId==0){
            return;
        }
        else{
            itemId=itemId+1;

            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.query(TABLE_NAME, null, "rowid = ?", new String[]{String.valueOf(itemId)}, null, null, null);
            int position = (int) itemId;

            if (cursor.moveToFirst()) {
                String pq1 = "SELECT rowid FROM " + TABLE_NAME + " WHERE position = ?";
                Cursor pc1 = db.rawQuery(pq1, new String[]{String.valueOf(position)});

                String pq2 = "SELECT rowid FROM " + TABLE_NAME + " WHERE position = ?";
                Cursor pc2 = db.rawQuery(pq2, new String[]{String.valueOf(1)});
                long rowId = -1;
                long rowId2 = -1;

                if (pc1.moveToFirst()&&pc2.moveToFirst()) {
                    rowId = pc1.getLong(0);
                    rowId2 = pc2.getLong(0);
                }

                pc1.close();
                pc2.close();
                ContentValues values = new ContentValues();
                values.put("position", 1);
                db.update(TABLE_NAME, values, "rowid = ?", new String[]{String.valueOf(rowId)});

                ContentValues values2 = new ContentValues();
                values2.put("position", position);
                db.update(TABLE_NAME, values2, "rowid = ?", new String[]{String.valueOf(rowId2)});

            }

            cursor.close();
            db.close();
        }

    }

    public void addData2(String item1,String item2,String item3,String item4,String item5,String item6) {
        SQLiteDatabase db = this.getWritableDatabase();
        String columnName = COL1;
        String columnValue = item1;

        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + columnName + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{columnValue});

        if (cursor.moveToFirst()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL1, item1);
            contentValues.put(COL2, item2);
            contentValues.put(COL3, item3);
            contentValues.put(COL4, item4);
            contentValues.put(COL5, item5);
            contentValues.put(COL6, item6);

            long result = db.update(TABLE_NAME, contentValues, "ID=?", new String[]{item1});

            Log.e("erre","Row already exists in the database.");
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL1, item1);
            contentValues.put(COL2, item2);
            contentValues.put(COL3, item3);
            contentValues.put(COL4, item4);
            contentValues.put(COL5, item5);
            contentValues.put(COL6, item6);

            String rowIdQuery = "SELECT MAX(rowid) FROM " + TABLE_NAME;
            Cursor rowIdCursor = db.rawQuery(rowIdQuery, null);
            long rowId = -1;
            if (rowIdCursor.moveToFirst()) {
                rowId = rowIdCursor.getLong(0);
            }
            rowIdCursor.close();

            contentValues.put("position", (rowId+1));
            long result = db.insert(TABLE_NAME, null, contentValues);
        }


        cursor.close();


    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }



    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY position ASC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readsarifle(String data){
        String query = "SELECT Sarifle,Accountnum,Tell,Tell2 FROM " + TABLE_NAME+" WHERE "+COL6+" == '"+data+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor2 = null;
        if(db != null){
            cursor2 = db.rawQuery(query, null);
        }

        return cursor2;
    }

    public boolean updateData(String row_id,String item1,String item2,String item3,String item4,String item5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item1);
        contentValues.put(COL2, item2);
        contentValues.put(COL3, item3);
        contentValues.put(COL4, item4);
        contentValues.put(COL5, item5);

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
