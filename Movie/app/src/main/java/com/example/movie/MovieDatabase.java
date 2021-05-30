package com.example.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabase extends SQLiteOpenHelper {

    private static final String DB_Name = "MovieDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "MovieCollection";
    public static final String ID = "ID";
    public static final String TITLE = "Title";
    public static final String YEAR = "Year";
    public static final String DIRECTOR = "Director";
    public static final String RATINGS = "Ratings";
    public static final String REVIEWS = "Reviews";
    public static final String ACTORS = "Actors";
    public static final String FAVOURITE = "Favourite";

    public MovieDatabase(Context context) {
        super(context, DB_Name, null, DATABASE_VERSION);
    }

    //create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Title TEXT, " +
                "Year TEXT, " +
                "Director TEXT, " +
                "Ratings TEXT, " +
                "Reviews TEXT, " +
                "Actors TEXT, " +
                "Favourite TEXT )";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    //add values to the table
    public Boolean addMovieInfo(String Title, String Year, String Director, String Ratings, String Reviews, String Actors, String Favourite){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, Title);
        contentValues.put(YEAR, Year);
        contentValues.put(DIRECTOR, Director);
        contentValues.put(RATINGS, Ratings);
        contentValues.put(REVIEWS, Reviews);
        contentValues.put(ACTORS, Actors);
        contentValues.put(FAVOURITE, Favourite);

        long result = database.insertOrThrow(TABLE_NAME, null, contentValues);

        //not added to the table
        if (result == -1){
            System.out.println("Error Occurred!");
            return false;
        }else {
            return true;
        }
    }

    //update favourite column
    public void addFavourites(String title, String favCode){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FAVOURITE, favCode);

        database.update(TABLE_NAME, contentValues,TITLE + " =?" , new String[]{title});
    }

    //pass all table values
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    //pass selected data
    public Cursor getSelectedData(String title){
        SQLiteDatabase database = this.getWritableDatabase();
        String getData = "SELECT * " +
                "FROM " + TABLE_NAME +
                " WHERE " + TITLE + "= " +
                "'" + title + "'";
        Cursor cursor = database.rawQuery(getData, null);
        return cursor;
    }

    //update all data
    public void updateData(String Title, String Year, String Director, String Ratings, String Reviews, String Actors, String Favourite, String edit){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, Title);
        contentValues.put(YEAR, Year);
        contentValues.put(DIRECTOR, Director);
        contentValues.put(RATINGS, Ratings);
        contentValues.put(REVIEWS, Reviews);
        contentValues.put(ACTORS, Actors);
        contentValues.put(FAVOURITE, Favourite);

        database.update(TABLE_NAME, contentValues, TITLE + " =?" , new String[]{edit});
    }
}
