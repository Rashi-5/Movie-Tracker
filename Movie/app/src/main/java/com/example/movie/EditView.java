package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditView extends AppCompatActivity {

    public EditText title;
    public EditText director;
    public EditText actors;
    public EditText reviews;
    public EditText year;
    public TextView fav;
    public RatingBar ratings;
    public CheckBox checkBox;
    public int rating;
    MovieDatabase movieDatabase;
    String passedMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_view);

        //get the passed value from EditMovie intent
        Intent intent = getIntent();
        passedMovieTitle = intent.getExtras().getString("Movie");

        System.out.println(passedMovieTitle);

        title = findViewById(R.id.titleTextEdit);
        director = findViewById(R.id.directorTextEdit);
        actors = findViewById(R.id.actorTextEdit);
        reviews = findViewById(R.id.reviewsTextEdit);
        year = findViewById(R.id.textYearEdit);
        fav = findViewById(R.id.EditFav);
        ratings = findViewById(R.id.ratingBar);
        checkBox = findViewById(R.id.checkBox);

        movieDatabase = new MovieDatabase(this);

        //get selected row from the database
        Cursor cursor = movieDatabase.getSelectedData(passedMovieTitle);

        if (cursor.getCount() == 0) {
            Toast.makeText(EditView.this, "Database is Empty!", Toast.LENGTH_SHORT).show();

        } else {
            while (cursor.moveToNext()) {
                String getTitle = cursor.getString(1);
                String getYear = cursor.getString(2);
                String getDirector = cursor.getString(3);
                String getRatings = cursor.getString(4);
                String getReviews = cursor.getString(5);
                String getActors = cursor.getString(6);
                String getFavourite = cursor.getString(7);

                title.setText(getTitle);
                director.setText(getDirector);
                actors.setText(getActors);
                reviews.setText(getReviews);
                year.setText(getYear);

                //check if fav or not
                if (getFavourite.equals("0")){
                    fav.setText("Not Favourite");
                    checkBox.setChecked(false);
                }
                else if (getFavourite.equals("1")){
                    fav.setText("Favourite");
                    checkBox.setChecked(true);
                }
                rating = Integer.parseInt(getRatings);
                ratings.setRating(rating);      //set ratings on rating bar
            }
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(EditView.this, "Added As Favourite!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditView.this, "Removed From Favourite!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String updatedFavourite;

    public void updateTable(View view) {

        //update the table
        int rating = (int) ratings.getRating();

        String updatedRating = String.valueOf(rating);
        String updatedTitle = title.getText().toString();
        String updatedYear = year.getText().toString();
        String updatedDirector = director.getText().toString();
        String updatedReviews = reviews.getText().toString();
        String updatedActors = actors.getText().toString();

        if (checkBox.isChecked()){
            updatedFavourite = "1";
        }else {
            updatedFavourite = "0";
        }

        movieDatabase.updateData(updatedTitle, updatedYear, updatedDirector, updatedRating, updatedReviews, updatedActors, updatedFavourite, passedMovieTitle);
        Toast.makeText(EditView.this, "Successfully updated the database!", Toast.LENGTH_SHORT).show();

    }
}