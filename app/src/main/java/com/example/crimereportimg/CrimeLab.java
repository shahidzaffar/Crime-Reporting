package com.example.crimereportimg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.crimereportimg.DBPackage.CrimeBaseHelper;
import com.example.crimereportimg.DBPackage.CrimeCursorWraper;
import com.example.crimereportimg.DBPackage.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab
{
    public static CrimeLab sCrimeLab;
    //public List<Crime>  mCrimes;

    private SQLiteDatabase mCrimeDB;
    public CrimeLab(Context context)
    {
       // mCrimes=new ArrayList<Crime>();
        mCrimeDB=new CrimeBaseHelper(context.getApplicationContext()).getWritableDatabase();
//        for(int i=0;i<50;i++)
//        {
//            Crime c=new Crime();
//            c.setTitleCrime("Crime No " + i);
//            c.setStatus(i%2==0);
//
//            mCrimes.add(c);
//        }
    }

    public void addCrime(Crime c)
    {
        ContentValues cv=getContentValues(c);
        mCrimeDB.insert(DatabaseSchema.CrimeTables.NAME,null,cv);
        //mCrimes.add(c);
    }

    public void updateDB(Crime c)
    {
        String UUIDstring=c.getMid().toString();
        ContentValues cv=getContentValues(c);
        mCrimeDB.update(DatabaseSchema.CrimeTables.NAME,cv,
                DatabaseSchema.CrimeTables.cols.UUID+"= ?",new String[]{UUIDstring});
    }

    public CrimeCursorWraper queryCrime(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mCrimeDB.query(DatabaseSchema.CrimeTables.NAME, null, whereClause,whereArgs,null,null,null);
        return new CrimeCursorWraper(cursor);
    }

    public static CrimeLab getsCrimeLabOBJ(Context context)
    {
        if(sCrimeLab==null)
            sCrimeLab=new CrimeLab(context);
        return sCrimeLab;
    }

    public List<Crime> getmCrimes()
    {
        List<Crime> crimeList=new ArrayList<>();
        CrimeCursorWraper cursor=queryCrime(null,null);

        cursor.moveToFirst();

        try
        {
            while(! cursor.isAfterLast())
            {
                crimeList.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return crimeList;
    }

    public Crime getSingleCrime(UUID mid)
    {
        String whereClause= DatabaseSchema.CrimeTables.cols.UUID+"= ?";
        String[] whereArgs=new String[]{mid.toString()};
        CrimeCursorWraper cursor=queryCrime(whereClause,whereArgs);
        cursor.moveToFirst();
        try
        {
            if(cursor.getCount()==0)
                return null;

            else
                return cursor.getCrime();

        }
        finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Crime crime)
    {
        ContentValues cv=new ContentValues();
        cv.put(DatabaseSchema.CrimeTables.cols.UUID,crime.getMid().toString());
        cv.put(DatabaseSchema.CrimeTables.cols.TITLE,crime.getTitleCrime());
        cv.put(DatabaseSchema.CrimeTables.cols.DATE,crime.getCrimDate().getTime());
        cv.put(DatabaseSchema.CrimeTables.cols.SOLVED,crime.isStatus() ?1:0);
        return cv;

    }
}