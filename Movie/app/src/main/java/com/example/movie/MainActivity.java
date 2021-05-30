package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //call EditMovies Activity
    public void launchEditMovies(View view) {
        Log.d(LOG_TAG, "Edit Movies launched!");
        Intent intent = new Intent(this, EditMovies.class);
        startActivity(intent);
    }

    //call Search Activity
    public void launchSearch(View view) {
        Log.d(LOG_TAG, "Search launched!");
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    //call DisplayMovies Activity
    public void launchDisplayMovies(View view) {
        Log.d(LOG_TAG, "Display Movies launched!");
        Intent intent = new Intent(this, DisplayMovies.class);
        startActivity(intent);
    }

    //call Ratings Activity
    public void launchRatings(View view) {
        Log.d(LOG_TAG, "Ratings launched!");
        Intent intent = new Intent(this, Ratings.class);
        startActivity(intent);
    }

    //call Register Movies
    public void launchRegisterMovies(View view) {
        Log.d(LOG_TAG, "Register Movies launched!");
        Intent intent = new Intent(this, RegisterMovie.class);
        startActivity(intent);
    }

    //call Favourite Activity
    public void launchFavourites(View view) {
        Log.d(LOG_TAG, "Favourites launched!");
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }
}
