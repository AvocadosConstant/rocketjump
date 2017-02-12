package codes.timhung.rocketjump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Game {

    private enum State {
        PAUSED, RUNNING
    }

    private Context context;
    private SurfaceHolder holder;
    private Resources resources;
    private State state = State.PAUSED;

    private Sprite testSprite;

    public Game(Context context, int screenWidth, int screenHeight, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.holder = holder;
        this.resources = resources;

        testSprite = new Sprite(screenWidth, screenHeight);
    }

    public void init() {
        testSprite.setX(testSprite.getScreenWidth()/2);
        testSprite.setY(testSprite.getScreenHeight()/2);
    }

    public void onTouchEvent(MotionEvent event) {
        if (state == State.RUNNING) {
            testSprite.setX(event.getX());
            testSprite.setY(event.getY());
        } else {
            state = State.RUNNING;
        }
    }
    /**
     * Game logic is checked here! Hitboxes, movement, etc.
     * @param elapsed Time since game started
     */
    public void update(Long elapsed) {
        if(state == State.RUNNING){
            // Check hitboxes etc
        }
    }

    /**
     * Decides whether to draw
     */
    public void draw() {
        Log.d("GAME_DRAW", "Locking canvas");
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            switch (state) {
                case PAUSED:
                    break;
                case RUNNING:
                    drawGame(canvas);
                    break;
            }
            Log.d("GAME_DRAW", "Unlocking canvas");
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Draws game resources
     * @param canvas Canvas to be drawn on
     */
    private void drawGame(Canvas canvas) {
        Log.d("GAME_DRAWGAME", "Trying to draw everything in the game!");
        testSprite.draw(canvas);
    }
}
