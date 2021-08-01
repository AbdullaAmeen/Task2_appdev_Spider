package com.example.appdev_task2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;


public class activity_game_layout extends View {

    Grid grid;
    Paint paint = new Paint();
    float S_Height, S_Width;
    int gameDifficulty;
    PaintTools paintTools;

    //buttons
    Button bt_restart, bt_menu;

    Vibrator v;

    Bitmap bm_bomb;

    public activity_game_layout(Context context, float S_Height, float S_Width, int gameDifficulty, Vibrator v) {
        super(context);
        grid = new Grid(8, S_Height, S_Width, gameDifficulty);
        this.gameDifficulty = gameDifficulty;
        this.S_Height = S_Height;
        this.S_Width = S_Width;
        this.v = v;

        bt_restart = new Button(100, 300, (int) (S_Width - 300) / 2, (int) (grid.gridCords.bottom + 50));
        bt_menu = new Button(75, 150, (int)(S_Width - 150) / 2, bt_restart.cords.bottom+50);

        Resources res = context.getResources();

        bm_bomb = BitmapFactory.decodeResource(res,
                R.drawable.bomb);

        bm_bomb = Bitmap.createScaledBitmap(bm_bomb, (int)grid.cellDim, (int)grid.cellDim  , true);
        paintTools = new PaintTools();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();

        if(grid.gameState == grid.GAME_OVER){
            if (bt_restart.cords.contains((int) x, (int) y))
                restart();

            if (bt_menu.cords.contains((int) x, (int) y))
                exit();

        }
        else
            if (grid.gridCords.contains(x, y)) {
                grid.onTouchGrid(x, y);
                invalidate();
            }
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            //background
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.parseColor("#787878"));
            canvas.drawRect(grid.gridCords,paint);

            paint.setColor(Color.WHITE);
            grid.drawCells(canvas, paint,bm_bomb);
            if(grid.gameState != Grid.GAME_PLAYING)
                gameOverMenu(canvas);

            paintTools.writeText_Center("SCORE: "+ grid.score , getWidth()* 1/4 , getHeight()/16, canvas, Color.WHITE, 64 );
            canvas.drawBitmap(bm_bomb,getWidth()* 3/4 - grid.cellDim/2 , getHeight()/16 - grid.cellDim/2, paint);
            paintTools.writeText_Center(": "+grid.noOfBombs,(int)(getWidth()* 3/4 + grid.cellDim), (int)(getHeight()/16), canvas,Color.WHITE, 64 );


        }
    }




    public void gameOverMenu(Canvas canvas) {
        if(grid.gameState == Grid.GAME_WIN){
            paintTools.writeText_Center("YOU WIN!" , getWidth()/2 , (int)(grid.gridCords.top - 100), canvas, Color.WHITE, 64 );
        }
        v.vibrate(500);
        Paint paint = new Paint();
        //End Screen
        canvas.save();
      //  canvas.drawColor(Color.BLACK);
        paint.setColor(Color.WHITE);
        canvas.drawRect(bt_restart.cords.left, bt_restart.cords.top, bt_restart.cords.right, bt_restart.cords.bottom, paint);
        paintTools.writeText_Center("RESTART", bt_restart.cords.centerX(), bt_restart.cords.centerY(), canvas, Color.BLACK, 30);

        canvas.drawRect(bt_menu.cords.left, bt_menu.cords.top, bt_menu.cords.right, bt_menu.cords.bottom, paint);
        paintTools.writeText_Center("MENU", bt_menu.cords.centerX(), bt_menu.cords.centerY(), canvas, Color.BLACK, 30);

        if(grid.score > getDataInt()){
            saveDataInt(grid.score);
        }

        canvas.restore();

    }

    public void restart(){
        grid = new Grid(8, S_Height, S_Width, gameDifficulty);
        invalidate();

    }

    public void exit(){
        ((Activity) getContext()).finish();
    }

    public int getDataInt() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(""+gameDifficulty, 0);
    }

    public void saveDataInt(int data) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(""+gameDifficulty, data);
        editor.commit();
    }


}