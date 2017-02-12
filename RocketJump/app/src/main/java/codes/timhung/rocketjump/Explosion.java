package codes.timhung.rocketjump;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Explosion extends Sprite {

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
        if(lifeTime >= 40) live = false;
    }
}
