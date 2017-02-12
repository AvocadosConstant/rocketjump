package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Explosion extends Sprite {

    private final int DEATH_AGE = 30;
    private final int FORCE_PERIOD = 10;
    private int lifeTime;
    boolean live;

    public Explosion(Bitmap image, Rect hitbox, Rect screen) {
        super(image, hitbox, screen);
        lifeTime = 0;
        live = true;
    }

    public void update(long elapsed) {
        lifeTime++;
        //Log.d("EXPLOSION", "lifetime: " + lifeTime);
        if(lifeTime >= DEATH_AGE) live = false;
    }

    public boolean isForceful() {
        return lifeTime < FORCE_PERIOD;
    }
}
