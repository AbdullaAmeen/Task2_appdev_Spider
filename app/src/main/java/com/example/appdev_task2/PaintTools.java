package com.example.appdev_task2;

import android.graphics.Canvas;

public class PaintTools {
    public void writeText_Center(String text, int x, int y, Canvas canvas, int color, int fontSize) {
        android.graphics.Paint paintText = new android.graphics.Paint();
        paintText.setColor(color);
        paintText.setStyle(android.graphics.Paint.Style.FILL);
        paintText.setAntiAlias(true);
        paintText.setTextSize(fontSize);
        paintText.setTextAlign(android.graphics.Paint.Align.CENTER);
        int yPos = (int) (y - (paintText.descent() + paintText.ascent()) / 2);
        canvas.save();  //
        canvas.drawText(text, x, yPos, paintText);
        canvas.restore();
    }

}
