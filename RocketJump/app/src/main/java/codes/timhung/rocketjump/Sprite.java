package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Tim Hung on 2/11/2017.
 */

/**
 * Parent class of any sprite
 */
public class Sprite {
    private float x;
    private float y;

    private int screenWidth;
    private int screenHeight;

    private Bitmap image;

    private Rect bounds;

    public Sprite(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Log.d("SPRITE", "Drawing sprite at (" + x + ", " + y + ")");
        canvas.drawRect(x, y, x + 80, y + 160, paint);
    }

    public int getScreenWidth() {return screenWidth;}

    public int getScreenHeight() {return screenHeight;}

    public void setX(float x) {this.x = x;}

    public void setY(float y) {this.y = y;}
}
