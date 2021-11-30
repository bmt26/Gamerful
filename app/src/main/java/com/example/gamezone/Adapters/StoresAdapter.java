package com.example.gamezone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamezone.R;
import com.example.gamezone.models.Screenshots;
import com.example.gamezone.models.Stores;

import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder> {

    private Context context;
    private List<Stores> stores;

    public StoresAdapter(Context context, List<Stores> stores) {
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stores store = stores.get(position);
        holder.bind(store);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button storeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeBtn = itemView.findViewById(R.id.storeBtn);
            Log.d("StoreAdapter", "Button: " + storeBtn);
        }

        public void bind(Stores store) {
            String store_name = store.getStoreName();
            String store_url = store.getLink();
            int store_icon;

            switch (store_name) {

                case "Steam":
                    store_icon = R.drawable.ic_steam_logo;
                    break;

                case "PlayStation Store":
                    store_icon = R.drawable.ic_playstation_logo;
                    break;

                case "Xbox Store":
                    store_icon = R.drawable.ic_xbox_logo;
                    break;

                case "App Store":
                    store_icon = R.drawable.ic_apple_logo;
                    break;

                case "GOG":
                    store_icon = R.drawable.ic_gog_logo;
                    break;

                case "Nintendo Store":
                    store_icon = R.drawable.ic_nintando_logo;
                    break;

                case "Xbox 360 Store":
                    store_icon = R.drawable.ic_xbox_logo;
                    break;

                case "Google Play":
                    store_icon = R.drawable.ic_playstore_logo;
                    break;

                case "itch.io":
                    store_icon = R.drawable.ic_itchio_logo;
                    break;

                case "Epic Games":
                    store_icon = R.drawable.ic_epic_games_logo;
                    break;

                default:
                    store_icon = 0;
            }
            
            storeBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, store_icon, 0);


            storeBtn.setText(store_name);

            storeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(store_url);

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
        }
    }

}
