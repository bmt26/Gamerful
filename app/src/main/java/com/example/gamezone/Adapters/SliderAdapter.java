package com.example.gamezone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;
import com.example.gamezone.fragments.DetailsFragment;
import com.example.gamezone.fragments.HomeFragment;
import com.example.gamezone.models.Games;

import java.util.List;
import java.util.Objects;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {

    private Context context;
    List<Games> games;

    public SliderAdapter(Context context, List<Games> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Games game = games.get(position);
        holder.bind(game);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        final FragmentManager fragmentManager;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        }

        public void bind(Games game) {
            Glide.with(context)
                    .load(game.getBackgroundImage())
                    .centerCrop()
                    .into(ivPoster);

            ivPoster.setOnClickListener(new View.OnClickListener() {
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
