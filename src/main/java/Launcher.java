import desktop.SwingScreen;
import engine.GameEngine;
import engine.loop.GameLoop;

import static engine.GameEngine.screenHeight;
import static engine.GameEngine.screenWidth;

public final class Launcher {

    public static void main(String[] args) {
        SwingScreen screen = new SwingScreen(screenWidth, screenHeight);
        GameEngine gameEngine = new GameEngine(screen, GameLoop::createFrameBasedGameLoop);

    }
}
