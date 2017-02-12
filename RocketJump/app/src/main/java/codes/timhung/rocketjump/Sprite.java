package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
    public Rect screen;
    private SpriteState spriteState;

    private int width;
    private int height;
    private double x;
    private double y;

    public double vx;
    public double vy;
    public double ax;
    public double ay;

    public final double FRIC = 1;
    public final double GRAV = 2;
    public boolean affectedByGrav = false;

    public Sprite(Bitmap image, Rect hitbox, Rect screen) {
        this.image = image;
        this.hitbox = hitbox;
        this.screen = screen;
        spriteState = SpriteState.IDLE;

        this.width = hitbox.width();
        this.height = hitbox.height();
        this.x = hitbox.left;
        this.y = hitbox.top;

        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
    }

    public void update(long elapsed) {
        // Update velocities
        vx += ax;
        vy += ay;
        if(this.affectedByGrav) vy += GRAV;

        // Update positions
        setX(this.getX() + vx);
        setY(this.getY() + vy);
    }

    public void draw(Canvas canvas) {
        //Log.d("SPRITE", "Drawing sprite at (" + hitbox.left + ", " + hitbox.top + ")");
        if(image != null) {
            // Draw image
        }
        drawHitbox(canvas, Color.MAGENTA);
    }

    public void drawHitbox(Canvas canvas, int color) {
        Paint borderPaint = new Paint();
        borderPaint.setStrokeWidth(10);
        borderPaint.setColor(color);
        borderPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(hitbox, borderPaint);
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public double getX() {return this.x;}

    public double getY() {return this.y;}

    public int getHeight() {return this.height;}

    public int getWidth() {return this.width;}

    public void setX(double x) {
        this.x = x;
        this.hitbox.offsetTo((int) this.x, (int) this.y);
    }

    public void setY(double y) {
        this.y = y;
        this.hitbox.offsetTo((int) this.x, (int) this.y);
    }
}
