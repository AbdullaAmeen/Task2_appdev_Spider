package com.example.appdev_task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;

public class Game extends AppCompatActivity {
    int gameDifficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Display display= getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Intent intent = getIntent();
        gameDifficulty = intent.getIntExtra("GameDifficulty", 0);
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        activity_game_layout layout = new activity_game_layout(this, size.y, size.x, gameDifficulty, v);
        setContentView(layout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}