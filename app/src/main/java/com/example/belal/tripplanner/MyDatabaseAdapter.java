package com.example.belal.tripplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Belal on 15/02/2017.
 */

public class MyDatabaseAdapter {


    static final String KEY_ROWID = "_id";
    static final String TAG = "********";
    static final String DATABASE_NAME = "Trip6";
    static final String DATABASE_TABLE = "TripInfo";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
            + "name_of_trip varchar not null, start_point varchar not null,end_point varchar not null,date text not null,time text not null,notes varchar,trip_type varchar,status varchar);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;


    public MyDatabaseAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    //================= My Inner Class   Helper ===============================================//
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + "to"
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE+"");
            onCreate(db);
        }

    }    // ========================  End of Helper Class ========================================//




    //---opens the database---
    public MyDatabaseAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }



    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }



    //---insert a Trip into the database---  //
    public long insertContact(String name_trip, String start_point,String end_point,String date_trip,String time_trip,String notes,String trip_type,String status)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name_of_trip", name_trip);
        initialValues.put("start_point", start_point);
        initialValues.put("end_point", end_point);
        initialValues.put("date", date_trip);
        initialValues.put("time", time_trip);
        initialValues.put("notes", notes);
        initialValues.put("trip_type", trip_type);
        initialValues.put("status", status);


        return db.insert(DATABASE_TABLE, null, initialValues);
    }



    //---deletes a particular Trip---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }



    //---retrieves all the UpComing Trips---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, "name_of_trip"}, "status " + " =" + "'not_done'", null, null, null, null, null);
    }

    public Cursor getAllContacts_Past_Trips()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, "name_of_trip"}, "status " + " =" + "'done'", null, null, null, null, null);
    }
    public Cursor getLastId()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID}, null, null, null, null, null);
    }



    //---retrieves a particular Trip---//
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, "name_of_trip", "start_point","end_point","date","time","notes","trip_type","status"}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }




    public Cursor getStartAndEndPoint(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, "start_point","end_point"}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


// get Id
public Cursor getId(long id) throws SQLException
{
    Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID}, KEY_ROWID +"=" + id, null, null, null, null, null);
    if (mCursor != null) {
        mCursor.moveToFirst();
    }
    return mCursor;
}




    //--------------------- updates a Trip -------------------//
    public boolean updateContact(long rowId, String name_trip, String start_point,String end_point,String date_trip,String time_trip,String notes,String trip_type,String status )
    {
        ContentValues args = new ContentValues();
        args.put("name_of_trip", name_trip);
        args.put("start_point", start_point);
        args.put("end_point", end_point);
        args.put("date", date_trip);
        args.put("time", time_trip);
        args.put("notes", notes);
        args.put("trip_type", trip_type);
        args.put("status", status);

        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean updateStatusContact(long rowId,String status )
    {
        ContentValues args = new ContentValues();

        args.put("status", status);

        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }




}




