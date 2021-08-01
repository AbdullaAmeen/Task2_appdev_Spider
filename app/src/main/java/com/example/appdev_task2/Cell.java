package com.example.appdev_task2;

import android.graphics.RectF;
import android.util.Log;

public class Cell {

    RectF cords;
    int i,j;
    int value;
    boolean isClosed;

    public Cell(float x, float y, float cellDim, int i, int j) {

        cords = new RectF(x,y,x + cellDim,y + cellDim);

        this.i = i;
        this.j = j;
        this.value = 0;
        isClosed = true;
    }

    public int onTouchCell(int score){
        if(isClosed) {
            score += 1;
            isClosed = false;
            Log.v("cell touch", "cell touched");


        }
        return score;
    }

}

