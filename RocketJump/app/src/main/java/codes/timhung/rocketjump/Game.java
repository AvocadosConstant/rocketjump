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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Game {

    private enum GameState {
        PAUSED, RUNNING
    }

    private final int ROCKET_RADIUS = 40;
    private final int EXPLOSION_RADIUS = 180;
    private Context context;
    private SurfaceHolder holder;
    private Rect screen;
    private Resources resources;
    private GameState state = GameState.PAUSED;

    private Sprite testSprite;
    private Player player;
    private Platform plat1;

    private ArrayList<Rocket> rockets;
    private ArrayList<Explosion> explosions;
    boolean canFire;
    int rocketCD;

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
        rockets = new ArrayList<>();
        explosions = new ArrayList<>();
        canFire = false;
        rocketCD = 0;
    }

    public void init() {
    }

    public void onTouchEvent(MotionEvent event) {
        if (state == GameState.RUNNING) {
            testSprite.setX(event.getX());
            testSprite.setY(event.getY());

            // Fire a rocket from the player to the event location
            if(canFire) {
                // Calculate rocket trajectory vector
                Log.d("ONTOUCH", "Spawning rocket");
                double vx = event.getX() - player.getHitbox().centerX();
                double vy = event.getY() - player.getHitbox().centerY();
                double length = Math.sqrt(vx * vx + vy * vy);
                rockets.add(new Rocket(
                        null,
                        new Rect(
                                player.getHitbox().centerX() - ROCKET_RADIUS,
                                player.getHitbox().centerY() - ROCKET_RADIUS,
                                player.getHitbox().centerX() + ROCKET_RADIUS,
                                player.getHitbox().centerY() + ROCKET_RADIUS
                        ),
                        screen,
                        vx / length,
                        vy / length
                ));

                canFire = false;
                rocketCD = 0;
            }

            /*
            if(event.getX() < screen.centerX()) {
                player.applyForce(-8, -20);
            } else {
                player.applyForce(8, -20);
            }
            */
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
            rocketCD++;
            if(rocketCD > 30) canFire = true;

            // Iterate over the rockets
            Iterator<Rocket> rIter = rockets.iterator();
            while(rIter.hasNext()) {
                Rocket rocket = rIter.next();
                Log.d("ROCKET_COL", "(" + rocket.getX() + ", " + rocket.getY() + ")");
                // If rocket collides with a solid object or goes off screen
                if(Rect.intersects(rocket.getHitbox(), plat1.getHitbox())
                        || rocket.getX() + ROCKET_RADIUS <= screen.left
                        || rocket.getX() + ROCKET_RADIUS >= screen.right
                        || rocket.getY() + ROCKET_RADIUS >= screen.bottom) {
                    // Create an explosion at it's impact
                    Log.d("UPDATE", "Rocket is exploding");
                    rocket.exists = false;
                    explosions.add(new Explosion(
                            null,
                            new Rect(
                                    rocket.getHitbox().centerX() - EXPLOSION_RADIUS,
                                    rocket.getHitbox().centerY() - EXPLOSION_RADIUS,
                                    rocket.getHitbox().centerX() + EXPLOSION_RADIUS,
                                    rocket.getHitbox().centerY() + EXPLOSION_RADIUS
                            ),
                            screen
                    ));
                    // Remove the rocket
                    rIter.remove();
                } else if(rocket.getY() + ROCKET_RADIUS <= screen.top - ROCKET_RADIUS * 3) {
                    // Rocket is above the top of the screen
                    rIter.remove();
                } else rocket.update(elapsed); // Rocket is still flying
            }

            // Iterate over the explosions
            Iterator<Explosion> eIter = explosions.iterator();
            while (eIter.hasNext()) {
                Explosion exp = eIter.next(); // must be called before you can call i.remove()
                if(!exp.live) eIter.remove();
                else {
                    if(Rect.intersects(player.getHitbox(), exp.getHitbox()) && exp.isForceful()) {
                        // Player is in explosion! Blast off!
                        double dx = player.getHitbox().centerX() - exp.getHitbox().centerX();
                        double dy = player.getHitbox().centerY() - exp.getHitbox().centerY();
                        double length = Math.sqrt(dx * dx + dy * dy);
                        dx *= 20 / length;
                        dy *= 20 / length;
                        Log.d("EXP_FORCE", "dx: " + dx + " | dy: " + dy);
                        player.applyForce(dx, dy);
                    }
                    exp.update(elapsed);
                }
            }
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

        for(Rocket rocket: rockets) rocket.draw(canvas);
        for(Explosion exp : explosions) exp.draw(canvas);
        plat1.draw(canvas);
        player.draw(canvas);
        testSprite.draw(canvas);
    }
}
