package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Player extends Sprite {

    public Player(Bitmap image, Rect hitbox, Rect screen) {
        super(image, hitbox, screen);
        this.affectedByGrav = true;
    }

    public void update(long elapsed) {
        // Check for player hitting bottom
        if(this.getHitbox().bottom >= screen.bottom) {
            this.setY(screen.bottom - this.getHeight());
            // Don't let player fall through
            this.vy = 0;
            // Apply friction in the x axis
            this.applyFrictionX();
        }
        // Check for player hitting side walls
        if(this.getHitbox().left <= screen.left
                || this.getHitbox().right >= screen.right) {
            // Reflect player
            this.vx *= -.8;
        }
        //Log.d("PLAYER", "vx: " + vx + " | ax: " + ax);
        //Log.d("PLAYER", "vy: " + vy + " | ay: " + ay);

        super.update(elapsed);

        this.ax = this.ay = 0;
    }

    public void applyForce(double fax, double fay) {
        this.ax = fax;
        this.ay = fay;
    }

    public void applyFrictionX() {
        if(this.vx > 0) {
            this.ax = -FRIC;
        } else if(this.vx < 0) {
            this.ax = FRIC;
        } else {
            this.ax = 0;
            this.vx = 0;
        }
    }
}
