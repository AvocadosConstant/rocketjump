package codes.timhung.rocketjump;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Tim Hung on 2/12/2017.
 */

public class Rocket extends Sprite {

    public final double ROCKET_SPEED = 80;
    public float angle;
    public boolean exists;
    public Rocket(Bitmap image, Resources resources, Rect hitbox, Rect screen, double vx, double vy) {
        super(image, resources, hitbox, screen);
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
