package com.example.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.inventoryapp.R;

public class HelperDatabase extends SQLiteOpenHelper {
    private  static String  DATABASE_NAME="students.db";
    private static int DATABASE_VERSION=1;
    public HelperDatabase(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String  SQL_CREATE_TABLE="CREATE TABLE "
            +Contract.Entry.TABLE_NAME+"("+
            Contract.Entry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
            Contract.Entry.COLUMN_NAME+" TEXT NOT NULL, "+
            Contract.Entry.COLUMN_CLASS+" INTEGER NOT NULL DEFAULT 10, "+
            Contract.Entry.COLUMN_ROLLNO+" INTEGER NOT NULL, "+
            Contract.Entry.COLUMN_IMAGE+" INTEGER NOT NULL DEFAULT "+ R.drawable.first+","+
            Contract.Entry.COLUMN_GENDER+" INTEGER NOT NULL DEFAULT 0);";
            db.execSQL(SQL_CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
