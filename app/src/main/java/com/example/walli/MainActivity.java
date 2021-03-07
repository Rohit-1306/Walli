package com.example.walli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    List<Model> wallpaperList;

    int pageNumber = 1;


    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;

    String url = "https://api.pexels.com/v1/curated/?page="+pageNumber+"&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        wallpaperList = new ArrayList<>();
        adapter = new Adapter(this,wallpaperList);

        recyclerView.setAdapter(adapter);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                 if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                     isScrolling = true;
                 }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems= gridLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling&&(currentItems+scrollOutItems == totalItems)){
                    isScrolling = false;
                    fetchWallpaper();
                }
            }
        });

        fetchWallpaper();
    }

    public void fetchWallpaper(){

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("photos");

                            int length = jsonArray.length();

                            for(int i = 0; i < length; i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                int id = jsonObject1.getInt("id");

                                JSONObject objectImages = jsonObject1.getJSONObject("src");

                                String originalURL = objectImages.getString("original");
                                String mediumURL = objectImages.getString("medium");

                                Model model = new Model(id,originalURL,mediumURL);
                                wallpaperList.add(model);

                            }

                            adapter.notifyDataSetChanged();
                            pageNumber++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> param = new HashMap<>();
                param.put("Authorization","563492ad6f91700001000001c1dbc553e2ca456188c3551ea1af7b07");

                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.navSearch){
            //creating dialog

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText editText = new EditText(this);

            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            alert.setMessage("Enter catagory e.g. Natue");
            alert.setTitle("Search Wallpaper!");

            alert.setView(editText);
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String query = editText.getText().toString().toLowerCase();

                    url = "https://api.pexels.com/v1/search?page="+pageNumber+"&query="+query+"&per_page=80";
                    wallpaperList.clear();
                    fetchWallpaper();


                }
            });

            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}