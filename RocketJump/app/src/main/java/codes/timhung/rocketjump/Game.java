package codes.timhung.rocketjump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private Rect screen;
    private Resources resources;
    private GameState state = GameState.PAUSED;

    private Sprite testSprite;
    private Player player;
    private Platform plat1;

    public Game(Context context, Rect screen, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.screen = screen;
        this.holder = holder;
        this.resources = resources;

        testSprite = new Sprite(
                null,
                new Rect(
                        screen.width()/2,
                        screen.height()/2,
                        screen.width()/2 + 80,
                        screen.height()/2 + 80),
                screen);

        player = new Player(
                null,
                new Rect(
                        screen.width()/2,
                        screen.height()/2,
                        screen.width()/2 + 160,
                        screen.height()/2 + 320),
                screen);
        plat1 = new Platform(
                null,
                screen,
                screen.centerX(),
                screen.centerY() + 200,
                400,
                40
                );
    }

    public void init() {
    }

    public void onTouchEvent(MotionEvent event) {
        if (state == GameState.RUNNING) {
            testSprite.setX(event.getX());
            testSprite.setY(event.getY());

            if(event.getX() < screen.centerX()) {
                player.applyForce(-8, -20);
            } else {
                player.applyForce(8, -20);
            }
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

            // Update positions and hitboxes
            testSprite.update(elapsed);
            player.update(elapsed);
            plat1.update(elapsed);
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
        canvas.drawRect(screen, borderPaint);

        plat1.draw(canvas);
        player.draw(canvas);
        testSprite.draw(canvas);
    }
}
