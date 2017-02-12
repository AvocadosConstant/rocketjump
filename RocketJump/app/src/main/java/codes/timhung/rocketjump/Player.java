package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Player extends Sprite {
    float ay = 2f;
    float ax = 0;
    float vx = 0;
    float vy = -50f;

    public Player(Bitmap image, Rect hitbox, Rect screenBounds) {
        super(image, hitbox, screenBounds);
    }

    public void update(long elapsed) {
        Log.d("PLAYER", "vy: " + vy);
        // d = vi*t + .5*a*t^2

        // Update velocities
        vx += ax;
        vy += ay;

        // Update positions
        setX(this.getX() + vx);
        setY(this.getY() + vy);
    }
}
