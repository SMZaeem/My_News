package com.example.mynews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.Model.NewsData;
import com.example.mynews.adapter.NewsAdapter;
import com.example.mynews.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

ActivityMainBinding binding;
List<NewsData> newsDataList;
NewsAdapter newsAdapter;
ImageView ImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView=findViewById(R.id.imgNews);
newsDataList = new ArrayList<>();
newsAdapter = new NewsAdapter(newsDataList);
binding.recyclerNews.setAdapter(newsAdapter);

//        binding.textview.setText();
        getData();

    }

    private void getData(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=0205de62845c4665b2ec25abd5fc1eb9";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if(status.equals("ok")){
                        JSONArray jsonArray = response.getJSONArray("articles");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject news = jsonArray.getJSONObject(i);
                            String title = news.getString("title");
                            String content = news.getString("description");
                            String url = news.getString("url");
                            String imgUrl = news.getString("urlToImage");
                        Log.d("Hello","Title :" + title);
                        NewsData nd = new NewsData(title,content,imgUrl,url);
                        newsDataList.add(nd);
                        }
                        newsAdapter.notifyDataSetChanged();
                    }
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

                Map<String, String> header = new HashMap<>();
                header.put("User-Agent", "PostmanRuntime/7.29.0");
                return header;
            }

        };
queue.add(jsonObjectRequest);

    }
}