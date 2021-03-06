package codes.timhung.rocketjump;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Player extends Sprite {

    public Player(Bitmap image, Resources resources, Rect hitbox, Rect screen) {
        super(image, resources, hitbox, screen);
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
        if(this.getHitbox().left <= screen.left) {
            this.setX(screen.left + 1);
            // Reflect player
            this.vx *= -.8;
        } else if(this.getHitbox().right >= screen.right) {
            this.setX(screen.right - this.getWidth() - 1);
            // Reflect player
            this.vx *= -.8;
        }
        Log.d("PLAYER", "y: " + (this.getY() - getWidth()));
        //Log.d("PLAYER", "vx: " + vx + " | ax: " + ax);
        //Log.d("PLAYER", "vy: " + vy + " | ay: " + ay);
        //Log.d("PLAYER", "height: " + this.getHeight() + " | width: " + this.getWidth());
        //Log.d("PLAYER", "HBheight: " + this.getHitbox().height() + " | HBwidth: " + this.getHitbox().width());

        super.update(elapsed);

        this.ax = this.ay = 0;
    }

    public void applyForce(double fax, double fay) {
        this.ax = fax;
        this.ay = fay;
    }

    public void applyFrictionX() {
        if(this.vx > 1) {
            this.ax = (this.vx > FRIC) ? -FRIC : this.vx;
        } else if(this.vx < -1) {
            this.ax = (this.vx < -FRIC) ? FRIC : this.vx;
        } else {
            this.ax = 0;
            this.vx = 0;
        }
    }
}
