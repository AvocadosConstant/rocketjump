package codes.timhung.rocketjump;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Tim Hung on 2/12/2017.
 */

public class Rocket extends Sprite {

    public final double ROCKET_SPEED = 80;
    public float angle;
    public boolean exists;
    public Rocket(Bitmap image, Resources resources, Rect hitbox, Rect screen, double vx, double vy) {
        super(image, resources, hitbox, screen);
        Bitmap source = BitmapFactory.decodeResource(resources, R.drawable.rocket, options);
        if(vx > 0) {
            Log.d("ROCKET", "vx is positive");
            Matrix matrix = new Matrix();
            matrix.postScale(-1, -1, (float) this.getX() + this.getWidth() / 2, (float) this.getY() + this.getHeight() / 2);
            source = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        }
        this.image = source;

        this.vx = vx * ROCKET_SPEED;
        this.vy = vy * ROCKET_SPEED;
        this.angle = (float)  Math.toDegrees(Math.atan(vy / vx));
        exists = true;
    }

    public void draw(Canvas canvas, long elevation) {
        if(exists) super.draw(canvas, elevation, angle);
    }

    public void draw(Canvas canvas, long elevation, float angle) {
        super.draw(canvas, elevation, angle);
        /*
        canvas.save();
        canvas.rotate(angle, (float) (this.getX() + this.getWidth() / 2), (float) (this.getY() + this.getHeight() / 2));
        super.draw(canvas, elevation);
        canvas.restore();
        this.drawVecs(canvas, elevation, 15);
        */
    }
}
