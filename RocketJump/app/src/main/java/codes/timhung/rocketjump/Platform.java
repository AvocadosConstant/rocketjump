package codes.timhung.rocketjump;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import codes.timhung.rocketjump.Sprite;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Platform extends Sprite {

    private final int MAX_PLAT_SPEED = 20;
    public Platform(Bitmap image, Resources resources, Rect hitbox, Rect screen) {
        super(image, resources, hitbox, screen);
        Random rng = new Random();
        this.vx = rng.nextInt(MAX_PLAT_SPEED * 2) - MAX_PLAT_SPEED;
    }

    public Platform(Bitmap image, Resources resources, Rect screen, int left, int top, int width, int height) {
        super(image, resources, new Rect( left, top, left + width, top + height), screen);
        this.image = BitmapFactory.decodeResource(resources, R.drawable.platform, options);
        Random rng = new Random();
        this.vx = rng.nextInt(MAX_PLAT_SPEED * 2) - MAX_PLAT_SPEED;
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
