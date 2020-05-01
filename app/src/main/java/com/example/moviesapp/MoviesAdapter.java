package com.example.moviesapp;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private int mResource;
    private ImageView favouriteImage;


    public MoviesAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        //GIATI GRAFEIS JAGA
        final Movie movie = getItem(position);

        String originalTitle = movie.getOriginalTitle();
        String avatar = movie.getAvatar();
        String sumary = movie.getSumary();
        final String movieTitle = movie.getTitle();
        final int pos = position;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        TextView sumaryTXT = convertView.findViewById(R.id.subscribeTXT);
        TextView TitleTXT = convertView.findViewById(R.id.titleTXT);
        ImageView avatarIMG = convertView.findViewById(R.id.avatarImage);

        final ImageView imageViewFavourite = convertView.findViewById(R.id.favouriteIMG);
        if (movie.getFavourite()) {
            imageViewFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            imageViewFavourite.setImageResource(R.drawable.ic_no_favourite);
        }
        imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageView(imageViewFavourite, movie);
            }
        });


        sumaryTXT.setMovementMethod(new ScrollingMovementMethod());
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
        sumaryTXT.setOnTouchListener(listener);

        sumaryTXT.setText("   " + sumary);
        TitleTXT.setText(movieTitle);
        String myUrl = "https://image.tmdb.org/t/p/w200" + avatar;
        Picasso.get().load(myUrl).into(avatarIMG);

        return convertView;
    }

    private void changeImageView(ImageView imageViewFavourite, Movie movie) {
        if (movie.getFavourite()) {
            movie.setFavourite(false);
            imageViewFavourite.setImageResource(R.drawable.ic_no_favourite);
            return;
        }
        movie.setFavourite(true);
        imageViewFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

}
