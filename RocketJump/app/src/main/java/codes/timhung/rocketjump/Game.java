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
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class Game {

    private enum GameState {
        PAUSED, RUNNING
    }

    private final int ROCKET_WIDTH = 80;
    private final int ROCKET_HEIGHT = 40;
    private final int EXPLOSION_RADIUS = 180;
    private Context context;
    private SurfaceHolder holder;
    private Rect screen;
    private Resources resources;
    private GameState state = GameState.PAUSED;

    private Player player;

    private LinkedList<Platform> platforms;
    private ArrayList<Rocket> rockets;
    private ArrayList<Explosion> explosions;
    boolean canFire;
    int rocketCD;

    long elevation;
    private final int NUM_PLATS_PER_SCREEN = 4;
    private int PLAT_SPACING;
    private int SCROLL_THRESH;

    public Game(Context context, Rect screen, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.screen = screen;
        this.holder = holder;
        this.resources = resources;
        PLAT_SPACING = screen.height() / NUM_PLATS_PER_SCREEN;

        player = new Player( null, resources, new Rect(
                        screen.width()/2,
                        screen.height() - 420,
                        screen.width()/2 + 160,
                        screen.height() - 100),
                screen);

        platforms = new LinkedList<>();
        spawnPlatforms(12, 400, 40);
        rockets = new ArrayList<>();
        explosions = new ArrayList<>();
        canFire = false;
        rocketCD = 0;
        elevation = 0;
        SCROLL_THRESH = screen.width() / 2 + player.getHeight();
    }

    public void spawnPlatforms(int num, int width, int height) {
        Random rng = new Random();
        int nextY = screen.bottom - 600;
        for(int i = 0; i < num; i++) {
            if(platforms.size() > 0) nextY = (int) platforms.peekLast().getY() - PLAT_SPACING;
            platforms.add(new Platform(
                    null, resources, screen,
                    screen.left + rng.nextInt(screen.width() - width - 1) + 1,
                    nextY,
                    width, height));
        }
    }

    public void onTouchEvent(MotionEvent event) {
        if (state == GameState.RUNNING) {
            fireRocket(event);
        } else {
            state = GameState.RUNNING;
        }
    }

    public void fireRocket(MotionEvent event) {
        // Fire a rocket from the player to the event location
        if(canFire) {
            // Calculate rocket trajectory vector
            Log.d("ONTOUCH", "Spawning rocket");
            double vx = event.getX() - player.getHitbox().centerX();
            double vy = event.getY() - elevation - player.getHitbox().centerY();
            double length = Math.sqrt(vx * vx + vy * vy);
            rockets.add(new Rocket(
                    null, resources,
                    new Rect(
                            player.getHitbox().centerX() - ROCKET_WIDTH,
                            player.getHitbox().centerY() - ROCKET_HEIGHT,
                            player.getHitbox().centerX() + ROCKET_WIDTH,
                            player.getHitbox().centerY() + ROCKET_HEIGHT
                    ),
                    screen,
                    vx / length,
                    vy / length
            ));

            canFire = false;
            rocketCD = 0;
        }
    }

    /**
     * Game logic is checked here! Hitboxes, movement, etc.
     * @param elapsed Time since game started
     */
    public void update(Long elapsed) {
        if(state == GameState.RUNNING){
            //Log.d("UPDATE", "Elevation: " + elevation);

            // Update positions and hitboxes
            rocketCD++;
            if(rocketCD > 20) canFire = true;

            // Calculate rocket collisions
            handleRockets(elapsed);

            // Calculate explosion propulsion
            handleExplosions(elapsed);

            // Calculate platform position
            for(Platform plat : platforms) {
                // Player is on a platform
                if(player.vy >= 0 && (plat.getHitbox().contains((int) player.getX(), (int) player.getBottom())
                || plat.getHitbox().contains((int) player.getRight(), (int) player.getBottom()))){
                    player.setY(plat.getY() - player.getHeight());
                    player.vy = 0;
                    player.setX(player.getX() + plat.vx);
                    player.applyFrictionX();
                    Log.d("PLAT_DET", "On a platform");
                }
                plat.update(elapsed);
            }

            // Calculate player position
            player.update(elapsed);

            // Update new elevation
            if(player.getY() < screen.bottom - SCROLL_THRESH) {
                elevation += screen.bottom - player.getY() - SCROLL_THRESH;
            }

            if(platforms.peekFirst().getY() > screen.bottom) {
                Log.d("UPDATE", "generating new platform");
                platforms.poll();
                spawnPlatforms(1, 400, 40);
            }
        }
    }

    public void handleRockets(Long elapsed) {
        // Iterate over the rockets
        Iterator<Rocket> rIter = rockets.iterator();
        while(rIter.hasNext()) {
            Rocket rocket = rIter.next();
            Log.d("ROCKET_COL", "(" + rocket.getX() + ", " + rocket.getY() + ")");
            Platform plat = rocketHitPlatform(rocket);
            // If rocket collides with a solid object or goes off screen
            if(plat != null) {
                // Collided with platform
                // Create an explosion at it's impact
                Log.d("UPDATE", "Rocket is exploding");
                rocket.exists = false;
                explosions.add(new Explosion(
                        null, resources,
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
            } else if (rocket.getX() + ROCKET_WIDTH <= screen.left
                    || rocket.getX() + ROCKET_WIDTH >= screen.right
                    || rocket.getY() + ROCKET_WIDTH >= screen.bottom) {
                // Collided with wall
                // Create an explosion at it's impact
                Log.d("UPDATE", "Rocket is exploding");
                rocket.exists = false;
                explosions.add(new Explosion(
                        null, resources,
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
            } else if (rocket.getY() + ROCKET_HEIGHT <= screen.top - ROCKET_HEIGHT * 3) {
                // Rocket is above the top of the screen
                rIter.remove();
            } else rocket.update(elapsed); // Rocket is still flying
        }
    }

    public Platform rocketHitPlatform(Rocket rocket) {
        for(Platform plat : platforms) {
            if(Rect.intersects(rocket.getHitbox(), plat.getHitbox())) return plat;
        } return null;
    }

    public void handleExplosions(Long elapsed) {
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

        canvas.translate(0, elevation);
        Paint borderPaint = new Paint();
        borderPaint.setStrokeWidth(24);
        borderPaint.setColor(Color.GREEN);
        borderPaint.setStyle(Paint.Style.STROKE);
        screen.offsetTo(screen.left, (int) -elevation);
        canvas.drawRect(screen, borderPaint);

        for(Rocket rocket : rockets) rocket.draw(canvas, elevation);
        for(Explosion exp : explosions) exp.draw(canvas, elevation);
        for(Platform plat : platforms) plat.draw(canvas, elevation);
        player.draw(canvas, elevation);
    }
}
