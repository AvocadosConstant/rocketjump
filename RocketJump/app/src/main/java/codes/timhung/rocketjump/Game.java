package codes.timhung.rocketjump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Game {

    private enum GameState {
        PAUSED, RUNNING
    }

    private Context context;
    private SurfaceHolder holder;
    private Rect screenBounds;
    private Resources resources;
    private GameState state = GameState.PAUSED;

    private Sprite testSprite;
    private Player player;

    public Game(Context context, Rect screenBounds, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.screenBounds = screenBounds;
        this.holder = holder;
        this.resources = resources;

        testSprite = new Sprite(
                null,
                new Rect(
                        screenBounds.width()/2,
                        screenBounds.height()/2,
                        screenBounds.width()/2 + 80,
                        screenBounds.height()/2 + 160),
                screenBounds);

        player = new Player(
                null,
                new Rect(
                        screenBounds.width()/2,
                        screenBounds.height()/2,
                        screenBounds.width()/2 + 160,
                        screenBounds.height()/2 + 320),
                screenBounds);
    }

    public void init() {
    }

    public void onTouchEvent(MotionEvent event) {
        if (state == GameState.RUNNING) {
            testSprite.setX(event.getX());
            testSprite.setY(event.getY());
        } else {
            state = GameState.RUNNING;
        }
    }
    /**
     * Game logic is checked here! Hitboxes, movement, etc.
     * @param elapsed Time since game started
     */
    public void update(Long elapsed) {
        if(state == GameState.RUNNING){
            // Check hitboxes etc

            // Check for player hitting bottom
            if(player.getHitbox().bottom >= screenBounds.bottom) {
                player.setY(screenBounds.bottom - player.getHeight());
                player.vy = 0;
            }
            // Update positions and hitboxes
            testSprite.update(elapsed);
            player.update(elapsed);
        }
    }

    /**
     * Decides whether to draw
     */
    public void draw() {
        //Log.d("GAME_DRAW", "Locking canvas");
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
            //Log.d("GAME_DRAW", "Unlocking canvas");
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Draws game resources
     * @param canvas Canvas to be drawn on
     */
    private void drawGame(Canvas canvas) {
        //Log.d("GAME_DRAWGAME", "Trying to draw everything in the game!");
        Paint borderPaint = new Paint();
        borderPaint.setStrokeWidth(24);
        borderPaint.setColor(Color.GREEN);
        borderPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(screenBounds, borderPaint);

        testSprite.draw(canvas);
        player.draw(canvas);

    }
}
