package com.omar.mymovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omar.mymovies.view.MovieActivity;
import com.omar.mymovies.R;
import com.omar.mymovies.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_item , viewGroup , false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.movieTitle.setText(movies.get(i).getOriginalTitle());
        movieViewHolder.movieRating.setText(Double.toString(movies.get(i).getVoteAverage()));

        String imagePath = "https://image.tmdb.org/t/p/w500" + movies.get(i).getPosterPath();
        Glide.with(context).load(imagePath).placeholder(R.drawable.loading).into(movieViewHolder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
    {
        TextView movieRating , movieTitle;
        ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.movieTitleTV);
            movieRating = itemView.findViewById(R.id.movieRatingTV);
            movieImage = itemView.findViewById(R.id.movieIV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Movie selectedMovie = movies.get(position);

                        Intent intent = new Intent(context , MovieActivity.class);
                        intent.putExtra("movie",selectedMovie);
                        context.startActivity(intent);
                    }
                }
            });
        }




    }
}
