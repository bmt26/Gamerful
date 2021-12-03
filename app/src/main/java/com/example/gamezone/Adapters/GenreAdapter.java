package com.example.gamezone.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.gamezone.BuildConfig;
import com.example.gamezone.R;
import com.example.gamezone.fragments.GamelistFragment;
import com.example.gamezone.fragments.SearchlistFragment;
import com.example.gamezone.models.Games;
import com.example.gamezone.models.Genres;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    Context context;

    public GenreAdapter(Context context, List<Genres> genres) {
        this.context = context;
        this.genres = genres;
    }

    List<Genres> genres;

    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_genre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.ViewHolder holder, int position) {
        Genres genre = genres.get(position);
        holder.bind(genre);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivGenreImage;
        TextView tvGenreName;

        FragmentManager fragmentManager;

        public static final String API_KEY = BuildConfig.RAWG_KEY;
        public static final String BASE_URL = "https://rawg.io/api/games?disable_user_platforms=true&page=1&page_size=20&filter=true&comments=true&key=" + API_KEY;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGenreImage = itemView.findViewById(R.id.ivGenreImage);
            tvGenreName = itemView.findViewById(R.id.tvGenreName);
        }

        public void bind(Genres genre) {

            Glide.with(context)
                    .load(genre.getBackgroundImage())
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(ivGenreImage);

            if(genre.getName().equals("Massively Multiplayer")) {
                tvGenreName.setText("Multiplayer");
            }
            else if(genre.getName().equals("Board Games")) {
                tvGenreName.setText("Board");
            }
            else {
                tvGenreName.setText(genre.getName());
            }

            String url = BASE_URL + "&genres=" + genre.getId();

            ivGenreImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    launchGamelistFragment(url, genre.getName());
                }
            });

        }

        private void launchGamelistFragment(String Url, String name) {
            fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString("Url", Url);
            bundle.putString("Name", name);

            Fragment fragment = new GamelistFragment();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }


    }

}
