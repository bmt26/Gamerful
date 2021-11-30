package com.example.gamezone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;
import com.example.gamezone.models.Reviews;
import com.parse.ParseFile;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private Context context;
    private List<Reviews> reviews;

    public ReviewsAdapter(Context context, List<Reviews> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reviews review = this.reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProfilePicture;
        private TextView tvUsername;
        private TextView tvCreatedAt;
        private TextView tvGame;
        private RatingBar rbRating;
        private TextView tvComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvGame = itemView.findViewById(R.id.tvGame);
            rbRating = itemView.findViewById(R.id.rbRating);
            tvComment = itemView.findViewById(R.id.tvComment);
        }

        public void bind(Reviews review) {
            ParseFile image = review.getImage();
            if (image!=null) {
                Glide.with(context)
                        .asBitmap()
                        .load(image)
                        .centerCrop()
                        .circleCrop()
                        .into(ivProfilePicture);
            }
            else {
                Glide.with(context)
                        .load(R.drawable.ic_default_figure)
                        .into(ivProfilePicture);
            }


            tvUsername.setText(review.getUser().getUsername());
            tvCreatedAt.setText(review.getCreatedAt().toString());
            tvGame.setText(review.getGame());
            rbRating.setRating(review.getStarRating());
            tvComment.setText(review.getComment());
        }
    }

}
