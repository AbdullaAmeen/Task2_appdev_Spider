package com.example.appdev_task2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Grid extends PaintTools{

    int size ,score, noOfBombs;
    RectF gridCords;
    float cellDim, gridDim, gapDim;
    Cell[][] cell;
    boolean gameOver;
    public Grid(int size, float S_Height, float S_Width, int gameDifficulty) {
        this.size = size;
        gameOver = false;
        gapDim = S_Width/48;
        cellDim = (S_Width - (size+1)*gapDim)/size;
        gridDim = S_Width;
        gridCords = new RectF(0,(S_Height - gridDim)/2, gridDim,(S_Height - gridDim)/2 + gridDim );
        cell = new Cell[size][size];
        noOfBombs = 5*(2 + gameDifficulty);
        init_cell();
        score =0;

    }

    void init_cell(){
        float x = gridCords.left + gapDim, y = gridCords.top+gapDim;
        for(int i=0; i < size; i++){
            for(int j=0; j <size; j++) {
                cell[i][j] = new Cell(x, y, cellDim, i, j);
                x += cellDim + gapDim;
            }
            y += cellDim + gapDim;
            x = gridCords.left + gapDim;
        }
        placeBombs();
    }

    void placeBombs(){
        int[][] array;
        array = new int[size][size];
        int i, j, count=0;
        while(count < noOfBombs){
            i = new Random().nextInt(size);
            j = new Random().nextInt(size);
            if(array[i][j] != -1){
                array[i][j] = -1;
                count++;

            }
            Log.v("yes", "yesbomb");
        }


        for(i=0; i < size; i++)
            for(j=0; j <size; j++) {
                if (array[i][j] != -1) {
                    for (int k = -1; k < 2; k++)
                        for (int l = -1; l < 2; l++) {
                            int p = i + k, q = j + l;
                            if (p > -1 && q > -1 && p < size && q < size) {
                                if (array[p][q] == -1) {
                                    array[i][j]++;
                                }
                            }
                        }
                }
                cell[i][j].value = array[i][j];
            }
    }

    public boolean drawCells(Canvas canvas, Paint paint, Bitmap bomb){

        if(canvas != null){

            for(int i=0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if(cell[i][j].value == -1 && gameOver) {
                        paint.setColor(Color.RED);
                        canvas.drawRect(cell[i][j].cords, paint);
                        canvas.drawBitmap(bomb,cell[i][j].cords.left, cell[i][j].cords.top, paint);
                    }
                    else if(cell[i][j].isClosed){
                        paint.setColor(Color.WHITE);
                        canvas.drawRect(cell[i][j].cords, paint);}
                    else
                        if (cell[i][j].value == -1) {
                            gameOver = true;
                            drawCells(canvas, paint, bomb);
                        }
                        else {
                            paint.setColor(Color.parseColor("#333333"));
                            canvas.drawRect(cell[i][j].cords, paint);
                            if (cell[i][j].value != 0) {
                                writeText_Center("" + cell[i][j].value, (int) cell[i][j].cords.centerX(), (int) cell[i][j].cords.centerY(), canvas, Color.WHITE, 50);
                            }
                            paint.setColor(Color.WHITE);
                        }
                    Log.v("drawcell", ""+ i +" "+ j +" "+ cell[i][j].cords +" "+ cellDim +" " + gapDim);
                }
            }

        }
        Log.v("drawcell", "outside");
        return gameOver;
    }

    public void onTouchGrid(float x , float y){
        float cellWidth = cellDim+gapDim;
        y -= gridCords.top;

        if( x%cellWidth > gapDim && y%cellWidth > gapDim)
            score=cell[(int)(y/cellWidth)][(int)(x/cellWidth)].onTouchCell(score);

    }
}
