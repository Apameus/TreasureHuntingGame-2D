package engine.loop;

import engine.GameEngine;

public interface GameLoop {

    static GameLoop createFrameBasedGameLoop(Runnable onUpdate, Runnable onRender){
        return new FrameBasedGameLoop(onUpdate, onRender);
    }

    void start();

    void stop();

    @FunctionalInterface
    interface Factory {
        GameLoop create(Runnable onUpdate, Runnable onRender);
    }

}
