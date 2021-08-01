package com.example.appdev_task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    Button bt_goToGameEASY, bt_goToGameMEDIUM, bt_goToGameHARD;
    TextView tv_easy, tv_medium, tv_hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bt_goToGameEASY = findViewById(R.id.bt_goToGameEASY);
        bt_goToGameMEDIUM = findViewById(R.id.bt_goToGameMEDIUM);
        bt_goToGameHARD = findViewById(R.id.bt_goToGameHARD);
        tv_easy = findViewById(R.id.tv_easy);
        tv_medium = findViewById(R.id.tv_medium);
        tv_hard = findViewById(R.id.tv_hard);
        bt_goToGameEASY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_goToGame = new Intent(Menu.this, Game.class);
                it_goToGame.putExtra("GameDifficulty", 0);
                startActivity(it_goToGame);
            }
        });

        bt_goToGameMEDIUM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_goToGame = new Intent(Menu.this, Game.class);
                it_goToGame.putExtra("GameDifficulty", 1);
                startActivity(it_goToGame);
            }
        });

        bt_goToGameHARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_goToGame = new Intent(Menu.this, Game.class);
                it_goToGame.putExtra("GameDifficulty", 2);
                startActivity(it_goToGame);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_easy.setText("EASY: "+getDataInt(0));
        tv_medium.setText("MEDIUM: "+getDataInt(1));
        tv_hard.setText("HARD: "+getDataInt(2));


    }
    public int getDataInt( int GameDifficulty) {
        SharedPreferences sharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(""+GameDifficulty, 0);
    }
}