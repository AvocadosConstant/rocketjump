package codes.timhung.rocketjump;

/**
 * Created by Tim Hung on 2/11/2017.
 */

public class GameThread extends Thread {
    private Game game;
    private volatile boolean running = true;
    private final int FRAME_RATE = 1000;

    public GameThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.init();
        long lastTime = System.currentTimeMillis();

        // Game loop
        while (running) {
            long now = System.currentTimeMillis();
            long elapsed = now - lastTime;

            if (elapsed < FRAME_RATE) {
                game.update(elapsed);
                game.draw();
            }
            lastTime = now;
        }
    }

    public void shutdown() {
        running = false;
    }
}