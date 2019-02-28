package com.omar.mymovies.view;

import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.omar.mymovies.R;
import com.omar.mymovies.adapter.MovieAdapter;
import com.omar.mymovies.model.Movie;
import com.omar.mymovies.model.MovieResponse;
import com.omar.mymovies.service.MovieDataService;
import com.omar.mymovies.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Most Popular Movie");

        getMovies();

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovies();
            }
        });
    }

    private void getMovies() {

        MovieDataService movieDataService = RetrofitInstance.getService();
        Call<MovieResponse> movieResponseCall = movieDataService.getPopularMovies(this.getString(R.string.api_key));

        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                if (movieResponse != null && movieResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) movieResponse.getMovies();
                    showOnRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void showOnRecyclerView() {
        recyclerView = findViewById(R.id.moviesRV);
        movieAdapter = new MovieAdapter(this, movies);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

    }
}
