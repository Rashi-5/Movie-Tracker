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
import java.util.Collections;

public class EditMovies extends AppCompatActivity {

    MovieDatabase movieDatabase;
    String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        ListView listView = findViewById(R.id.moviesList);
        movieDatabase = new MovieDatabase(this);

        //load all data from the database
        Cursor cursor = movieDatabase.getAllData();
        ArrayList<String> listOfMovies = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(EditMovies.this, "Database is Empty!", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                listOfMovies.add(cursor.getString(1));
                Collections.sort(listOfMovies);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfMovies);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the selected item and pass it to the EditView intent
                movieTitle = listOfMovies.get(position);
                Intent intent = new Intent(EditMovies.this, EditView.class);
                intent.putExtra("Movie" ,movieTitle);
                startActivity(intent);
            }
        });
    }
}