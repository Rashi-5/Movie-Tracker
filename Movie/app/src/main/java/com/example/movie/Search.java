package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    MovieDatabase movieDatabase;
    ListView listView;
    ArrayList<String> listOfMovieTitles = new ArrayList<>();
    ArrayList<String> listOfMovieDirectors = new ArrayList<>();
    ArrayList<String> listOfMovieActors = new ArrayList<>();
    ArrayList<String> searchResult = new ArrayList<>();
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        movieDatabase = new MovieDatabase(this);
        listView = findViewById(R.id.searchList);
        search = findViewById(R.id.searchBar);

        //load all data
        Cursor cursor = movieDatabase.getAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(Search.this, "Database is Empty!", Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()) {
            String title = cursor.getString(1).toUpperCase();       //get Title
            String actors = cursor.getString(6).toUpperCase();      //get actors
            String director = cursor.getString(3).toUpperCase();    //get directors

            listOfMovieTitles.add(title);
            listOfMovieActors.add(actors);
            listOfMovieDirectors.add(director);
        }
    }

    public void lookUp(View view) {

        //avoid duplicates
        searchResult.clear();

        String getName = search.getText().toString().toUpperCase();     //get the input

        //check whether contains the input
        for (int i = 0; i < listOfMovieDirectors.size(); i++) {
            if (listOfMovieDirectors.get(i).contains(getName)){
                searchResult.add(listOfMovieDirectors.get(i));
            }
        }

        for (int i = 0; i < listOfMovieActors.size(); i++) {
            if (listOfMovieActors.get(i).contains(getName)){
                searchResult.add(listOfMovieActors.get(i));
            }
        }

        for (int i = 0; i < listOfMovieTitles.size(); i++) {
            if (listOfMovieTitles.get(i).contains(getName)){
                searchResult.add(listOfMovieTitles.get(i));
            }
        }

        if (searchResult.isEmpty()){
            Toast.makeText(Search.this, "Nothing Related to " + getName, Toast.LENGTH_SHORT).show();
        }
        else {
            //if contains add to list view
            ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResult);
            listView.setAdapter(listAdapter);
        }
    }
}