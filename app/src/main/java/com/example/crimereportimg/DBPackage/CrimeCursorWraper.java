package com.example.crimereportimg.DBPackage;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.crimereportimg.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWraper extends CursorWrapper
{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWraper(Cursor cursor) {
        super(cursor);
    }


    public Crime getCrime()
    {
        String UUIDCrime=getString(getColumnIndex(DatabaseSchema.CrimeTables.cols.UUID));
        String title=getString(getColumnIndex(DatabaseSchema.CrimeTables.cols.TITLE));
        Long crimeDate=getLong(getColumnIndex(DatabaseSchema.CrimeTables.cols.DATE));
        boolean isSolved=getInt(getColumnIndex(DatabaseSchema.CrimeTables.cols.SOLVED))!=0;

        Crime crime=new Crime(UUID.fromString(UUIDCrime));
        crime.setTitleCrime(title);
        crime.setCrimDate(new Date(crimeDate));
        crime.setStatus(isSolved);
        return crime;

    }
}
