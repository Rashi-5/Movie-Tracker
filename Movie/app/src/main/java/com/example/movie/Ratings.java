package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Ratings extends AppCompatActivity {

    MovieDatabase movieDatabase;
    String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        ListView listView = findViewById(R.id.singleChoice);
        movieDatabase = new MovieDatabase(this);

        //load all data from the database
        Cursor cursor = movieDatabase.getAllData();
        ArrayList<String> listOfMovies = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(Ratings.this, "Database is Empty!", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                listOfMovies.add(cursor.getString(1));
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, listOfMovies);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movieTitle = listOfMovies.get(position);
            }
        });
    }

    public void FindIn(View view) {
        //pass movie title to ViewRatings Activity
        Intent intent = new Intent(Ratings.this, ViewRatings.class);
        intent.putExtra("Movie" ,movieTitle);
        startActivity(intent);
    }
}