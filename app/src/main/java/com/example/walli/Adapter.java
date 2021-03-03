package com.example.walli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Holder>{

    private Context context;
    private List<Model> wallpaperList;

    public Adapter(Context context, List<Model> wallpaperList) {
        this.context = context;
        this.wallpaperList = wallpaperList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Glide.with(context).load(wallpaperList.get(position).getMediumURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }
}
class Holder extends RecyclerView.ViewHolder{

    ImageView imageView;
    public Holder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageViewItem);

    }
}
