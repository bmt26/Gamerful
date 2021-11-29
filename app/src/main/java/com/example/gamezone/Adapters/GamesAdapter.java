package com.example.gamezone.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.gamezone.R;
import com.example.gamezone.fragments.DetailsFragment;
import com.example.gamezone.models.Games;

import java.util.List;
import java.util.Objects;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    Context context;
    List<Games> games;

    public GamesAdapter(Context context, List<Games> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_games, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.ViewHolder holder, int position) {
        Games game = games.get(position);
        holder.bind(game);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGamePoster;
        TextView tvTitle;
        TextView tvGenres;
        TextView tvRating;
        ConstraintLayout game_list_item;
        final FragmentManager fragmentManager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGamePoster = itemView.findViewById(R.id.ivGamePoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGenres = itemView.findViewById(R.id.tvGenres);
            tvRating = itemView.findViewById(R.id.tvRating);
            game_list_item = itemView.findViewById(R.id.game_list_item);

            fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        }

        public void bind(Games game) {
            Glide.with(context)
                    .load(game.getBackgroundImage())
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(ivGamePoster);

            tvTitle.setText(game.getName());
            tvGenres.setText(TextUtils.join(" · ", game.getGenres()));
            tvRating.setText(String.format("%.1f", game.getRating()));

            game_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Id", game.getId());

                    Fragment fragment = new DetailsFragment();
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .hide(Objects.requireNonNull(fragmentManager.findFragmentByTag("homeFragment")))
                            .add(R.id.flContainer, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }
}
