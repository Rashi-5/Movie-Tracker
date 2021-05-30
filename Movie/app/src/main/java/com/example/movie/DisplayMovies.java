package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayMovies extends AppCompatActivity {

    MovieDatabase movieDatabase;
    ArrayList<String> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        ListView listView = findViewById(R.id.movieList);
        movieDatabase = new MovieDatabase(this);

        //retrieve all the data from the database
        Cursor cursor = movieDatabase.getAllData();
        ArrayList<String> listOfMovies = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(DisplayMovies.this, "Database is Empty!", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                //get movie TITLE column
                listOfMovies.add(cursor.getString(1));
                //Sort Movie titles
                Collections.sort(listOfMovies);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);      //set choice mode to multiple
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, listOfMovies);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get selected movie title
                String movieTitle = listOfMovies.get(position);
                movies.add(movieTitle);
            }
        });
    }

    public void addToFavourite(View view) {
        String favCode = "1";       //set integer 1 as Boolean true
        //update the table
        for (int i = 0; i < movies.size(); i++) {
            String getMovie = movies.get(i);

            movieDatabase.addFavourites(getMovie, favCode);
        }
        Toast.makeText(DisplayMovies.this, "Selected Movies Added as Favourite", Toast.LENGTH_SHORT).show();
    }
}