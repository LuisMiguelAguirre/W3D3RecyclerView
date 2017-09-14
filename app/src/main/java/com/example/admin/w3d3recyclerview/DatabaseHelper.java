package com.example.admin.w3d3recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.name;

/**
 * Created by Luis Aguirre on 9/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Famous";
    public static final String FAMOUS_NAME = "name";
    public static final String FAMOUS_ID = "id";
    public static final String FAMOUS_DESCRIPTION = "description";
    public static final String TAG = "DatabaseHelperTag";
    private static final String DATABASE_NAME = "FamousDatabase";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                FAMOUS_NAME + " TEXT," +
                FAMOUS_DESCRIPTION + " TEXT," +
                FAMOUS_ID + " INT PRIMARY KEY" +
                ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long testRecord() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FAMOUS_NAME, "Luis");
        contentValues.put(FAMOUS_DESCRIPTION, "Developer");
        contentValues.put(FAMOUS_ID, 0);
        long rowNumber = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        Log.d(TAG, "saveNewContact: " + rowNumber);

        return rowNumber;
    }

}
