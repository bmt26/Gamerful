package com.example.gamezone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.gamezone.R;
import com.example.gamezone.models.Games;
import com.example.gamezone.models.Screenshots;

import java.util.List;

public class ScreenshotAdapter extends RecyclerView.Adapter<ScreenshotAdapter.ViewHolder> {

    Context context;
    private List<Screenshots> screenshots;

    public ScreenshotAdapter(Context context, List<Screenshots> screenshots) {
        this.context = context;
        this.screenshots = screenshots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_screenshot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Screenshots screenshot = screenshots.get(position);
        holder.bind(screenshot);
    }

    @Override
    public int getItemCount() {
        return screenshots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivScreenshot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivScreenshot = itemView.findViewById(R.id.ivScreenshot);
        }

        public void bind(Screenshots screenshot) {

            Glide.with(context)
                    .asBitmap()
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .load(screenshot.getScreenshot())
                    .into(ivScreenshot);

        }
    }

}
