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
import com.example.gamezone.fragments.GamelistFragment;
import com.example.gamezone.models.Games;

import java.util.List;

public class GamelistAdapter extends RecyclerView.Adapter<GamelistAdapter.ViewHolder> {

    Context context;
    private List<Games> games;

    public GamelistAdapter(Context context, List<Games> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public GamelistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_game_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamelistAdapter.ViewHolder holder, int position) {
        Games game = games.get(position);
        holder.bind(game);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivGameImage;
        TextView tvGameTitle;
        TextView tvGameGenres;
        TextView tvGameRatings;
        ConstraintLayout list_item;
        FragmentManager fragmentManager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGameImage = itemView.findViewById(R.id.ivGameImage);
            tvGameTitle = itemView.findViewById(R.id.tvGameTitle);
            tvGameGenres = itemView.findViewById(R.id.tvGameGenres);
            tvGameRatings = itemView.findViewById(R.id.tvGameRatings);
            list_item = itemView.findViewById(R.id.list_item);

            fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        }

        public void bind(Games game) {
            Glide.with(context)
                    .load(game.getBackgroundImage())
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(ivGameImage);

            tvGameTitle.setText(game.getName());
            tvGameGenres.setText(TextUtils.join(" · ", game.getGenres()));
            tvGameRatings.setText(String.format("%.1f", game.getRating()));

            list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Id", game.getId());

                    Fragment fragment = new DetailsFragment();
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}
