package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ViewRatings extends AppCompatActivity {

    public static String movieTitle;
    public static ListView listView;
    public static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings);

        //get the passed movie title from Ratings
        Intent intent = getIntent();
        movieTitle = intent.getExtras().getString("Movie");

        listView = findViewById(R.id.titleAndRatings);

        MovieRatingAPI movieRatingAPI = new MovieRatingAPI(ViewRatings.this);
        movieRatingAPI.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;       //get selected item index
                System.out.println(index);

                //start MovieImage Activity
                Intent intentImg = new Intent(ViewRatings.this, MovieImage.class);
                startActivity(intentImg);
            }
        });
    }
}