package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {
    MovieDatabase movieDatabase;
    ListView listView;
    ArrayList<String> listOfMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        movieDatabase = new MovieDatabase(this);

        listView = findViewById(R.id.favList);

        //load all data
        Cursor cursor = movieDatabase.getAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(Favourites.this, "No favourites added!", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                String fav = cursor.getString(7);
                //if favourite add to the array list
                if (fav.equals("1")) {
                    listOfMovies.add(cursor.getString(1));
                }

                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, listOfMovies);
                listView.setAdapter(listAdapter);

                //set favourites checked
                for (int i=0; i < listOfMovies.size(); i++){
                    listView.setItemChecked(i,true);
                }
            }
        }
    }

    public void saveChanges(View view) {

        for (int i=0; i < listOfMovies.size(); i++){
            if (!(listView.isItemChecked(i))) {
                String nonFavMovie = listOfMovies.get(i);
                movieDatabase.addFavourites(nonFavMovie, "0");      //set unchecked values as not favourite
            }
        }
        Toast.makeText(Favourites.this, "Successfully Updated the favourites!", Toast.LENGTH_SHORT).show();
    }
}