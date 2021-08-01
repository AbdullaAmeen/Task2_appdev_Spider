package com.example.appdev_task2;

import android.graphics.Rect;

public class Button {
    Rect cords;

    public Button(int height, int length, int x, int y ) {
        cords = new Rect(x,y,x + length,y + height);
    }

}
