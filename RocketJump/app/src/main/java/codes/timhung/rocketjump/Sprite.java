package codes.timhung.rocketjump;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    public Bitmap image;
    public Resources resources;
    private Rect hitbox;
    public Rect screen;
    private SpriteState spriteState;
    BitmapFactory.Options options = new BitmapFactory.Options();

    private int width;
    private int height;
    private double x;
    private double y;

    public double vx;
    public double vy;
    public double ax;
    public double ay;

    public final double FRIC = 3;
    public final double GRAV = 1;
    public boolean affectedByGrav = false;

    public Sprite(Bitmap image, Resources resources, Rect hitbox, Rect screen) {
        this.image = image;
        this.resources = resources;
        this.hitbox = hitbox;
        this.screen = screen;
        spriteState = SpriteState.IDLE;
        options.inScaled = false;

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

    public void draw(Canvas canvas, long elevation) {
        //Log.d("SPRITE", "Drawing sprite at (" + hitbox.left + ", " + hitbox.top + ")");
        if(image != null) {
            // Draw image
            canvas.drawBitmap(image, null, this.getHitbox(), null);
        } else drawHitbox(canvas, elevation, Color.MAGENTA);
        //drawVecs(canvas, elevation, 15);
    }

    public void draw(Canvas canvas, long elevation, float angle) {
        canvas.save();
        canvas.rotate(angle, (float) (this.getX() + this.getWidth() / 2), (float) (this.getY() + this.getHeight() / 2));
        if(image != null) {
            // Draw image
            canvas.drawBitmap(image, null, this.getHitbox(), null);
        } else drawHitbox(canvas, elevation, Color.MAGENTA);
        canvas.restore();
        //this.drawVecs(canvas, elevation, 15);
    }

    public void drawHitbox(Canvas canvas, long elevation, int color) {
        Paint borderPaint = new Paint();
        borderPaint.setStrokeWidth(10);
        borderPaint.setColor(color);
        borderPaint.setStyle(Paint.Style.STROKE);
        this.setY(this.getY());// + elevation);
        canvas.drawRect(hitbox, borderPaint);
    }

    public void drawVecs(Canvas canvas, long elevation, int scalar) {
        Paint paint = new Paint();
        // Draw velocities
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(this.getHitbox().centerX(), this.getHitbox().centerY());
        path.lineTo(this.getHitbox().centerX() + (int) vx * scalar,
                this.getHitbox().centerY() + (int) vy * scalar);
        path.close();
        canvas.drawPath(path, paint);
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public int getHeight() {return this.height;}

    public int getWidth() {return this.width;}

    public double getX() {return this.x;}

    public double getY() {return this.y;}

    public double getRight() {return this.x + this.getWidth();}

    public double getBottom() {return this.y + this.getHeight();}

    public void setX(double x) {
        this.x = x;
        //this.hitbox.offsetTo((int) this.x, (int) this.y);
        this.getHitbox().set(
                (int) x,
                (int) y,
                (int) x + this.getWidth(),
                (int) y + this.getHeight()
        );
    }

    public void setY(double y) {
        this.y = y;
        //this.hitbox.offsetTo((int) this.x, (int) this.y);
        this.getHitbox().set(
                (int) x,
                (int) y,
                (int) x + this.getWidth(),
                (int) y + this.getHeight()
        );
    }
}
