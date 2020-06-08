package com.example.moviesapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoviesAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private int mResource;
    private fav_Data_Base favmoviesDb;


    public MoviesAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        favmoviesDb = new fav_Data_Base(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie movie = getItem(position);
        final String originalTitle = movie.getOriginalTitle();
        final String avatar = movie.getAvatar();
        final String overView = movie.getOverView();
        final String voteAverage = movie.getVoteAverage();
        final String releaseDate = parseDate(movie.getReleaseDate());

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView overViewTXT = convertView.findViewById(R.id.subscribeTXT);
        TextView TitleTXT = convertView.findViewById(R.id.titleTXT);
        ImageView avatarIMG = convertView.findViewById(R.id.avatarImage);
        ImageView infoIMG = convertView.findViewById(R.id.infoIMG);
        final ImageView imageViewFavourite = convertView.findViewById(R.id.favouriteIMG);

        //SET ON CLICK ACTION IN THE BUTTON THAT THERE ARE MORE INFORMATION
        infoIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog infodialog = new AlertDialog.Builder(mContext).create();
                infodialog.setMessage("Release date: "+releaseDate +"\nVote average: "+voteAverage+"/10");
                infodialog.setTitle("More");
                infodialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent shareIntent = new Intent((Intent.ACTION_SEND));
                        shareIntent.setType("text/plain");
                        String body = originalTitle;
                        String sub = "Movie Title";
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                        shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                        mContext.startActivity(Intent.createChooser(shareIntent,"Share using"));
                    }
                });
                infodialog.show();
            }
        });
        //END OF THE BUTTON WITH INFORMATION

        imageViewFavourite.setImageResource(R.drawable.ic_no_favourite);//SET IMAGE TON NOT FAVORITE
        setMovieFavouriteImage(movie,imageViewFavourite);//CHECK THE MOVIE IF ITS ADDED TO FAVOURITES DATA BASE AND SET THE IMAGE TO FAVOURITE IF ITS ADDED

        //SET ON CLICK LISTENER TO DELETE OR ADD THE MOVIE TO FAVOURITES
        imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movie.getFavourite()){
                    favmoviesDb.deleteData(movie.getOriginalTitle());
                    movie.setFavourite(false);
                    imageViewFavourite.setImageResource(R.drawable.ic_no_favourite);
                    Toast.makeText(mContext,"successfully deleted",Toast.LENGTH_SHORT).show();
                } else{
                    favmoviesDb.insertData(originalTitle,avatar,overView,releaseDate,voteAverage);
                    movie.setFavourite(true);
                    imageViewFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Toast.makeText(mContext,"successfully added",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //END OF THE MOVIES FAVOURITE SWITCH

        overViewTXT.setText("   " + overView); //SET THE MOVIES DESCRIPTION
        overViewTXT.setMovementMethod(new ScrollingMovementMethod()); //IF ITS LARGER THAN THE BOX MAKES THE BOX SCROLLABLE
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean isLarger;
                isLarger = ((TextView) v).getLineCount() * ((TextView) v).getLineHeight() > v.getHeight();
                if (event.getAction() == MotionEvent.ACTION_MOVE && isLarger) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        };
        overViewTXT.setOnTouchListener(listener);

        TitleTXT.setText(originalTitle); //SET THE TITLE OF THE MOVIE
        String myUrl = "https://image.tmdb.org/t/p/w200" + avatar; //SET THE URL OF THE IMAGE OF THE MOVIE
        Picasso.get().load(myUrl).into(avatarIMG);//LOAD THE IMAGE WITH PICASSO ON THE IMAGE VIEW
        return convertView;
    }

    private void setMovieFavouriteImage(Movie movie,ImageView imageViewFavourite) {
        Cursor cursor = favmoviesDb.getAllData();
        if (cursor.getColumnCount() == 0) {
            Toast.makeText(mContext,"ERROR 404 PROGRAMMER NOT FOUND",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if (movie.getOriginalTitle().equalsIgnoreCase(cursor.getString(0)))
                {
                    imageViewFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    movie.setFavourite(true);
                    break;
                }
                else {
                    imageViewFavourite.setImageResource(R.drawable.ic_no_favourite);
                }
            }
        }
    }

    public String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
