package com.shiv.inventorytracker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

  public final static String DATABASE_NAME = "myInventory.db";
  public final static String TABLE_NAME = "myInventory_table";
  public final static String COL_1 = "ID";
  public final static String COL_2 = "NAME";
  public final static String COL_3 = "PRICE";
  public final static String COL_4 = "QUANTITY";

  public DatabaseHelper(@Nullable Context context) {
    super(context, DATABASE_NAME, null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT, " +
            "PRICE INTEGER, " +
            "QUANTITY INTEGER)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }

  public boolean insertData(String id, String name, String price, String quantity) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    contentValues.put(COL_1, id);
    contentValues.put(COL_2, name);
    contentValues.put(COL_3, price);
    contentValues.put(COL_4, quantity);
    long result = db.insert(TABLE_NAME, null, contentValues);

    return result != -1;
  }

  public boolean updateData(String id, String name, String price, String quantity) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    contentValues.put(COL_1, id);
    contentValues.put(COL_2, name);
    contentValues.put(COL_3, price);
    contentValues.put(COL_4, quantity);

    db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
    return true;

  }

  public boolean updateData(String id, String newStockQuantity) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    contentValues.put(COL_1, id);
    contentValues.put(COL_4, newStockQuantity);

    db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
    return true;

  }

  public Cursor getData(String id) {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID='" + id + "'", null);
  }


  public Integer deleteData(String id) {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.delete(TABLE_NAME, "ID=?", new String[]{id});
  }

  public int deleteAll() {
    int rows;
    SQLiteDatabase db = this.getWritableDatabase();
    rows = db.delete(TABLE_NAME, "1", null);
    db.execSQL("DELETE FROM " + TABLE_NAME);
    db.close();
    return rows;
  }

  public Cursor getAllData() {
    SQLiteDatabase db = this.getWritableDatabase();

    return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
  }


}
