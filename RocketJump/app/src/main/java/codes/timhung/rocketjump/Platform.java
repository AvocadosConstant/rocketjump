package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Rect;

import codes.timhung.rocketjump.Sprite;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Platform extends Sprite {

    public Platform(Bitmap image, Rect hitbox, Rect screen) {
        super(image, hitbox, screen);
        this.vx = 20;
    }

    public Platform(Bitmap image, Rect screen, int left, int top, int width, int height) {
        super(
                image,
                new Rect(
                        left,
                        top,
                        left + width,
                        top + height),
                screen);
        this.vx = 20;
    }

    public void update(long elapsed) {
        // Platform has hit a side so toggle it's direction
        if(this.getHitbox().left <= screen.left
                || this.getHitbox().right >= screen.right) {
           this.vx *= -1;
        }
        super.update(elapsed);
    }
}
