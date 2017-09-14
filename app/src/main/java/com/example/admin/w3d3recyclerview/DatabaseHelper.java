package com.example.admin.w3d3recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    String json = "";
    ArrayList<Famous> famousData;

    public DatabaseHelper(Context context, String json) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Famous> getFamousData() {
        return famousData;
    }

    void loadData() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToNext()) {
            json = cursor.getString(1);
            Type listType = new TypeToken<ArrayList<Famous>>(){}.getType();
            famousData = new Gson().fromJson(json, listType);
        } else {
            famousData = new ArrayList<>();
            famousData.add(new Famous("CHARLIZE THERON", "Date of Birth: August 7, 1975 | Birth Place: Benoni, South Africa", R.drawable.charlize));
            famousData.add(new Famous("ANNA KENDRICK", "Date of Birth: August 9, 1985 | Birth Place: Portland, ME", R.drawable.annakendrick164008421));
            famousData.add(new Famous("CHANNING TATUM", "Date of Birth: April 26, 1980 | Birth Place: Cullman, AL", R.drawable.channing162613136));
            famousData.add(new Famous("CHRIS EVANS", "Date of Birth: June 13, 1981 | Birth Place: Sudbury, MA", R.drawable.chrisevans12));
            famousData.add(new Famous("GEORGE CLOONEY", "Date of Birth: May 6, 1961 | Birth Place: Lexington, KY", R.drawable.clooneyperfp));
            famousData.add(new Famous("GAL GADOT", "Date of Birth: April 30, 1985 | Birth Place: Rosh Ha'Ayin, Israel", R.drawable.gadot));
            famousData.add(new Famous("JOHNNY DEPP", "Date of Birth: June 9, 1963 | Birth Place: Owensboro, KY", R.drawable.johnnydeep));
            famousData.add(new Famous("Tom Cruise2", "Actor2", R.drawable.kristinwiig162157550));
            famousData.add(new Famous("JENNIFER LAWRENCE", "Date of Birth: August 15, 1990 | Birth Place: Louisville, KY", R.drawable.lawrencejennifer));
            famousData.add(new Famous("JARED LETO", "Date of Birth: December 26, 1971 | Birth Place: Bossier City, LA", R.drawable.leto));
            famousData.add(new Famous("ROBERT DOWNEY, JR.", "Date of Birth: April 4, 1965 | Birth Place: New York, NY\n", R.drawable.robert162574544));
            famousData.add(new Famous("SCARLETT JOHANSSON", "Date of Birth: November 22, 1984 | Birth Place: New York, NY", R.drawable.scarlett159652076));
            famousData.add(new Famous("EMMA STONE", "Date of Birth: November 6, 1988  | Birth Place:Scottsdale, Arizona, U.S.", R.drawable.stone159231705));
            famousData.add(new Famous("WILL SMITH", "Date of Birth: September 25, 1968 | Birth Place: Philadelphia, PA", R.drawable.willsmith169638915));
            json = new Gson().toJson(famousData);
            insertDB(json);
        }
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

    public long insertDB(String jsonObject) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Drop();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FAMOUS_NAME, "Json");
        contentValues.put(FAMOUS_DESCRIPTION, jsonObject);
        contentValues.put(FAMOUS_ID, 1000);
        long rowNumber = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);


        Log.d(TAG, "saveNewContact: " + rowNumber);

        return rowNumber;
    }


    void Drop() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, null, null);
    }
}




