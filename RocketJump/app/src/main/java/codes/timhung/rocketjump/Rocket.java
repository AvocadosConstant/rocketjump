package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Tim Hung on 2/12/2017.
 */

public class Rocket extends Sprite {

    public final double ROCKET_SPEED = 80;
    public boolean exists;
    public Rocket(Bitmap image, Rect hitbox, Rect screen, double vx, double vy) {
        super(image, hitbox, screen);
        this.vx = vx * ROCKET_SPEED;
        this.vy = vy * ROCKET_SPEED;
        exists = true;
    }

    public void draw(Canvas canvas, long elevation) {
        if(exists) super.draw(canvas, elevation);
    }
}
