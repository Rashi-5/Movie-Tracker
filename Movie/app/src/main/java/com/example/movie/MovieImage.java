package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MovieImage extends AppCompatActivity {

    public static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_image);

        imageView = findViewById(R.id.movieImage);

        MovieImageAPI movieImageAPI = new MovieImageAPI(this);
        movieImageAPI.execute();
    }
}