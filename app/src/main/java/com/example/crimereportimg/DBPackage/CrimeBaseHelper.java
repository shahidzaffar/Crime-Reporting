package com.example.crimereportimg.DBPackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.DatabaseMetaData;

public class CrimeBaseHelper extends SQLiteOpenHelper
{
    private static final String NAME="CrimesDB.db";
    private static final int VERSION =1;


    public CrimeBaseHelper(@Nullable Context context)
    {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query="CREATE TABLE " +DatabaseSchema.CrimeTables.NAME+"( "+
                "_id integer primary key autoincrement, "+
                DatabaseSchema.CrimeTables.cols.UUID +","+
                DatabaseSchema.CrimeTables.cols.TITLE+","+
                DatabaseSchema.CrimeTables.cols.DATE+","+
                DatabaseSchema.CrimeTables.cols.SOLVED+")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
