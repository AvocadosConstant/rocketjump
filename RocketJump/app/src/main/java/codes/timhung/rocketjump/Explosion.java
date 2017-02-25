package codes.timhung.rocketjump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Explosion extends Sprite {

    private final int DEATH_AGE = 30;
    private final int FORCE_PERIOD = 10;
    private float angle;
    private int lifeTime;
    boolean live;
    Bitmap[] bitmaps = new Bitmap[8];

    public Explosion(Bitmap image, Resources resources, Rect hitbox, Rect screen) {
        super(image, resources, hitbox, screen);
        angle = (float)(Math.random() * 360);
        lifeTime = 0;
        live = true;
        bitmaps[0] = BitmapFactory.decodeResource(resources, R.drawable.explosion1, options);
        bitmaps[1] = BitmapFactory.decodeResource(resources, R.drawable.explosion2, options);
        bitmaps[2] = BitmapFactory.decodeResource(resources, R.drawable.explosion3, options);
        bitmaps[3] = BitmapFactory.decodeResource(resources, R.drawable.explosion4, options);
        bitmaps[4] = BitmapFactory.decodeResource(resources, R.drawable.explosion5, options);
        bitmaps[5] = BitmapFactory.decodeResource(resources, R.drawable.explosion6, options);
        bitmaps[6] = BitmapFactory.decodeResource(resources, R.drawable.explosion7, options);
        bitmaps[7] = BitmapFactory.decodeResource(resources, R.drawable.explosion8, options);
        this.image = bitmaps[0];
        Log.d("EXPLOSION_CTOR", "Is image null? " + (this.image == null));
    }

    public void update(long elapsed) {
        Log.d("EXPLOSION_UPDATE", "Lifetime: " + lifeTime);
        lifeTime++;
        //Log.d("EXPLOSION", "lifetime: " + lifeTime);
        if(lifeTime >= DEATH_AGE) live = false;
    }

    public void draw(Canvas canvas, long elapsed) {
        Log.d("EXPLOSION_DRAW", "Lifetime: " + lifeTime);
        //super.draw(canvas, elapsed);
        if(lifeTime < bitmaps.length * 2) {
            this.image = bitmaps[lifeTime / 2];
            super.draw(canvas, elapsed, angle);
        } else Log.d("EXPLOSION_DRAW", "Image is null!");
    }

    public boolean isForceful() {
        return lifeTime < FORCE_PERIOD;
    }
}
