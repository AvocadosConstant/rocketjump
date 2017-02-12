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
    public enum SpriteState {
        IDLE, JUMP, FLY, LAND
    }
    private Bitmap image;
    private Rect hitbox;
    private Rect screenBounds;
    private SpriteState spriteState;

    private int width;
    private int height;
    private float x;
    private float y;

    public Sprite(Bitmap image, Rect hitbox, Rect screenBounds) {
        this.image = image;
        this.hitbox = hitbox;
        this.screenBounds = screenBounds;
        spriteState = SpriteState.IDLE;

        this.width = hitbox.width();
        this.height = hitbox.height();
        this.x = hitbox.left;
        this.y = hitbox.top;
    }

    public void update(long elapsed) {
    }

    public void draw(Canvas canvas) {
        Log.d("SPRITE", "Drawing sprite at (" + hitbox.left + ", " + hitbox.top + ")");
        if(image == null) {
            // Handle null sprites
            Paint borderPaint = new Paint();
            borderPaint.setStrokeWidth(10);
            borderPaint.setColor(Color.RED);
            borderPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(hitbox, borderPaint);
        }
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public float getX() {return this.x;}

    public float getY() {return this.y;}

    public int getHeight() {return this.height;}

    public int getWidth() {return this.width;}

    public void setX(float x) {
        this.x = x;
        this.hitbox.offsetTo((int) this.x, (int) this.y);
    }

    public void setY(float y) {
        this.y = y;
        this.hitbox.offsetTo((int) this.x, (int) this.y);
    }
}
