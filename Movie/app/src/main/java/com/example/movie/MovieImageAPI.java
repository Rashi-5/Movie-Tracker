package com.example.movie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieImageAPI extends AsyncTask<Void, String, Bitmap> {

    final String MOVIE_LIST_URL = "https://imdb-api.com/en/API/SearchTitle/k_bmrhqbno/";
    String image;
    Bitmap bitmap;
    Context context;

    //constructor
    public MovieImageAPI(Context cont){
        this.context = cont;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {

        try {
            URL url = new URL(MOVIE_LIST_URL + ViewRatings.movieTitle);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = bufferedReader.readLine();
            JSONObject data = new JSONObject(line);

            //get data to an array
            JSONArray titlesRelated = data.getJSONArray("results");

            if (titlesRelated.length() == 0){
                Toast.makeText(context, "No Results Found!", Toast.LENGTH_SHORT).show();
            }

            //get the particular object
            JSONObject movieObject = titlesRelated.getJSONObject(ViewRatings.index);
            image = movieObject.getString("image");

            //create the image url
            URL imgUrl = new URL(image);
            HttpURLConnection openConnection = (HttpURLConnection) imgUrl.openConnection();
            InputStream imgInputStream = openConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(imgInputStream);

            //set to bitmap
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitImg) {
        //set image to the  image view
        MovieImage.imageView.setImageBitmap(bitImg);
    }

}
