package engine.loop;

import java.time.Duration;

public final class FrameBasedGameLoop implements GameLoop{

    // FPS
    int fps = 60;
    final long NANOS_PER_SECOND = Duration.ofSeconds(1).toNanos();
    final double DRAW_INTERVAL = (double) NANOS_PER_SECOND / fps;  // 0.016666 seconds

    public static boolean shouldRun;
    private final Runnable onUpdate, onRender;

    public FrameBasedGameLoop(Runnable onUpdate, Runnable onRender) {
        this.onRender = onRender;
        this.onUpdate = onUpdate;
    }

    @Override
    public void start() {
        shouldRun = true;

        double delta = 0;
        long lastUpdateTime = System.nanoTime();

        long timer = 0;
        long drawCount = 0;

        while (shouldRun){

            long now = System.nanoTime();
            delta += (now - lastUpdateTime) / DRAW_INTERVAL;
            timer += now - lastUpdateTime;

            lastUpdateTime = now;

            if (delta >= 1){
                onUpdate.run();
                onRender.run();
                delta--;

                drawCount++;
            }
            // PRINT THE FPS
            if (timer >= NANOS_PER_SECOND){
                // System.out.println(playerX + " " + playerY);
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    @Override
    public void stop() {
        shouldRun = false;
    }
}
