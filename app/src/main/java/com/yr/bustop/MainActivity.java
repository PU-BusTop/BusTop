package com.yr.bustop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button RealTime,Favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealTime = findViewById(R.id.RealTime);
        Favorite = findViewById(R.id.Favorite);

        RealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HomeRouteActivity.class);
                startActivity(intent);
            }
        });

        Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
//                Intent intent = new Intent(MainActivity.this,FavoriteRouteActivity.class);
//                startActivity(intent);
            }
        });
    }
}
