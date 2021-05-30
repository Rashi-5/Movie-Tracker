package com.example.movie;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieRatingAPI extends AsyncTask<Void, String, ArrayList<String>> {

    final String MOVIE_LIST_URL = "https://imdb-api.com/en/API/SearchTitle/k_bmrhqbno/";
    final String MOVIE_RATINGS_URL = "https://imdb-api.com/en/API/UserRatings/k_bmrhqbno/";

    public static ArrayList<String> titleArray = new ArrayList<>();
    public static ArrayList<String> ratingArray = new ArrayList<>();
    public static ArrayList<String> movieArray = new ArrayList<>();
    Context context;

    public MovieRatingAPI (Context context) {
        this.context = context;
    }
    
    @Override
    protected ArrayList<String> doInBackground(Void... voids) {

        try {
            URL url = new URL(MOVIE_LIST_URL + ViewRatings.movieTitle);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = bufferedReader.readLine();

            JSONObject data = new JSONObject(line);

            //create an array to get the data
            JSONArray titlesRelated = data.getJSONArray("results");

            if (titlesRelated.length() == 0){
                Toast.makeText(context, "No Results Found!", Toast.LENGTH_SHORT).show();
            }

            //clear arrays to avoid duplicate values
            titleArray.clear();
            ratingArray.clear();
            movieArray.clear();

            for (int i = 0; i < titlesRelated.length(); i++){
                JSONObject movieObject = titlesRelated.getJSONObject(i);

                String movieTitle = movieObject.getString("title");         //get the title
                String id = movieObject.getString("id");                    //get the id

                titleArray.add(movieTitle);

                URL idUrl = new URL(MOVIE_RATINGS_URL + id);            //create url for extract ratings

                HttpURLConnection idConnection = (HttpURLConnection) idUrl.openConnection();

                InputStream idInputStream = idConnection.getInputStream();
                BufferedReader idBufferedReader = new BufferedReader(new InputStreamReader(idInputStream));

                String idLine = idBufferedReader.readLine();

                JSONObject idData = new JSONObject(idLine);

                String totalRating = idData.getString("totalRating");           //get ratings

                ratingArray.add(totalRating);
            }

            for (int i = 0; i < titleArray.size(); i++){
                String title = titleArray.get(i);
                String rate = ratingArray.get(i);

                if (rate.equals("") || rate.equals("null")){
                    rate = "Not Rated";
                }

                String detail = title + "\nRatings: " + rate;           //concatenate title and ratings as one
                movieArray.add(detail);
            }

            if (movieArray.isEmpty()){
                Toast.makeText(context, "Nothing to Show!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieArray;
    }

    @Override
    protected void onPostExecute(ArrayList<String> arrayOfMovies) {
        //add to list view
        System.out.println(arrayOfMovies);
        ListAdapter listAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayOfMovies);
        ViewRatings.listView.setAdapter(listAdapter);

    }
}
