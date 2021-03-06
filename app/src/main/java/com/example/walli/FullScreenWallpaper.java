package com.example.walli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class FullScreenWallpaper extends AppCompatActivity {

    String originalURL = "";
    PhotoView photoView;

    Button btn_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_wallpaper);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        originalURL = intent.getStringExtra("originalURL");

        photoView = findViewById(R.id.photoView);
        btn_apply = findViewById(R.id.btn_apply);

        Glide.with(this).load(originalURL).into(photoView);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager wallpaperManager;
                wallpaperManager = WallpaperManager.getInstance(FullScreenWallpaper.this);

                Bitmap bitmap = ((BitmapDrawable)photoView.getDrawable()).getBitmap();

                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(FullScreenWallpaper.this, "Wallpaper Set!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}