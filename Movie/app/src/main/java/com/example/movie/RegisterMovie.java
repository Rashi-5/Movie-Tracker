package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterMovie extends AppCompatActivity {

    MovieDatabase movieDatabase;
    public Button save;
    public EditText title;
    public EditText year;
    public EditText director;
    public EditText ratings;
    public EditText reviews;
    public EditText actors;
    Boolean found = false;
    Boolean yearDone = false;
    Boolean rateDone = false;
    Boolean filledYear = false;
    Boolean filledRate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        save = findViewById(R.id.Update);
        title = findViewById(R.id.titleTextEdit);
        year = findViewById(R.id.textYearEdit);
        director = findViewById(R.id.directorTextEdit);
        ratings = findViewById(R.id.ratingTextEdit);
        reviews = findViewById(R.id.reviewsTextEdit);
        actors = findViewById(R.id.actorTextEdit);
        movieDatabase = new MovieDatabase(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTitle = title.getText().toString().toUpperCase();
                String getYear = year.getText().toString().toUpperCase();
                String getDirector = director.getText().toString().toUpperCase();
                String getRatings = ratings.getText().toString().toUpperCase();
                String getReviews = reviews.getText().toString().toUpperCase();
                String getActors = actors.getText().toString().toUpperCase();
                String getFavourite = "0";

                //load all data from the database
                Cursor cursor = movieDatabase.getAllData();
                ArrayList<String> listOfMovies = new ArrayList<>();

                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        listOfMovies.add(cursor.getString(1));
                    }
                }

                //check if user already added the movie title to avoid duplicates
                for (int i = 0; i < listOfMovies.size(); i++) {
                    if (listOfMovies.get(i).equals(getTitle)) {
                        found = true;
                        Toast.makeText(RegisterMovie.this, title + " Already Exists!", Toast.LENGTH_SHORT).show();
                    }
                }

                //get valid year
                if (getYear.equals("")) {
                    Toast.makeText(RegisterMovie.this, "Please Enter the Year!", Toast.LENGTH_SHORT).show();
                } else if (getYear.length() != 4) {
                    Toast.makeText(RegisterMovie.this, "Please Enter Valid Year!", Toast.LENGTH_SHORT).show();
                } else {
                    filledYear = true;
                }

                //get valid ratings
                if (getRatings.equals("")) {
                    Toast.makeText(RegisterMovie.this, "Please Enter Ratings!", Toast.LENGTH_SHORT).show();
                } else {
                    filledRate = true;
                }

                //validate ratings and year
                if (filledRate && filledYear) {
                    String year = getYear.replaceAll("\\s", "");
                    int rating = Integer.parseInt(getRatings);
                    int movieYear = Integer.parseInt(year);

                    if (movieYear < 1895) {
                        Toast.makeText(RegisterMovie.this, "Please enter a year over '1895'", Toast.LENGTH_SHORT).show();
                    } else {
                        yearDone = true;
                    }

                    if (rating > 11) {
                        Toast.makeText(RegisterMovie.this, "Rate between 1 - 10!", Toast.LENGTH_SHORT).show();
                    } else {
                        rateDone = true;
                    }
                }

                //check if title id empty
                if (getTitle.equals("")) {
                    Toast.makeText(RegisterMovie.this, "Title Cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (yearDone && rateDone) {
                        if (!found) {
                            System.out.println(listOfMovies);
                            AddMovies(getTitle, getYear, getDirector, getRatings, getReviews, getActors, getFavourite);
                        }
                    }
                }
            }
        });
    }

    private void AddMovies(String getTitle, String getYear, String getDirector, String getRatings, String getReviews, String getActors, String favourite) {

        //check if data has successfully added
        boolean insertData = movieDatabase.addMovieInfo(getTitle, getYear, getDirector, getRatings, getReviews, getActors, favourite);

        if (insertData){
            Toast.makeText(RegisterMovie.this, "Successfully added to the database!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(RegisterMovie.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}