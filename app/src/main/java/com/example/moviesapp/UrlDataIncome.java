package com.example.moviesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.moviesapp.secrets.SecretVariables;
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

public class UrlDataIncome extends AsyncTask<Void,Void,Void> {

    StringBuilder data = new StringBuilder();
    String line;
    Context context;
    int resource;
    ListView listView;
    ArrayList<Movie> movieArray = new ArrayList<>();

    public UrlDataIncome(Context c, int r, ListView list) {
        super();
        context = c;
        resource = r;
        listView = list;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.themoviedb.org/3/discover/movie?with_genres=28&primary_release_year=2017&language=el&api_key="+ SecretVariables.API_KEY);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            line = bufferedReader.readLine();

            while (line !=null){
                data.append(line);
                line = bufferedReader.readLine();
            }
            Log.d("KAPPA", "doInBackground: " + data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject;
        JSONArray json;

        try {

            jsonObject = new JSONObject(data.toString());
            json = jsonObject.getJSONArray("results");

            for(int i = 0; i<40; i++ ){

                JSONObject obj = json.getJSONObject(i);

                String original_title = obj.get("original_title").toString();
                String Avatar=obj.get("poster_path").toString();
                String overview = obj.get("overview").toString();
                String release_date = obj.get("release_date").toString();
                String vote_average = obj.get("vote_average").toString();


                Movie aMovie = new Movie(original_title,Avatar,overview,release_date,vote_average);
                movieArray.add(aMovie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute (Void aVoid){

        MoviesAdapter adapter = new MoviesAdapter(context, R.layout.movies_adapter,  movieArray);
        listView.setAdapter(adapter);

        super.onPostExecute(aVoid);
    }
}
