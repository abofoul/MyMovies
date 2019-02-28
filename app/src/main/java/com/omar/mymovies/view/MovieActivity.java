package com.omar.mymovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omar.mymovies.R;
import com.omar.mymovies.model.Movie;

import java.util.Objects;

public class MovieActivity extends AppCompatActivity {

    Movie movie;
    private ImageView movieImage;
    String imagePath;
    private TextView movieTitle, movieSynopsis, movieRating, movieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        movieImage = findViewById(R.id.movieDetailsIV);
        movieTitle = findViewById(R.id.tvMovieTitle);
        movieSynopsis = findViewById(R.id.tvPlotsynopsis);
        movieRating = findViewById(R.id.tvMovieRating);
        movieReleaseDate = findViewById(R.id.tvReleaseDate);

        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {
            movie = intent.getParcelableExtra("movie");

            imagePath = movie.getPosterPath();
            Toast.makeText(getApplicationContext(), movie.getOriginalTitle(), Toast.LENGTH_LONG).show();

            String image = "https://image.tmdb.org/t/p/w500" + imagePath;

            Glide.with(this).load(image).placeholder(R.drawable.loading).into(movieImage);
            getSupportActionBar().setTitle(movie.getTitle());

            movieTitle.setText(movie.getTitle());
            movieSynopsis.setText(movie.getOverview());
            movieRating.setText(Double.toString(movie.getVoteAverage()));
            movieReleaseDate.setText(movie.getReleaseDate());


        }

    }

}
