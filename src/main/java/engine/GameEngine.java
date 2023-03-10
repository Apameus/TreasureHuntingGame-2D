package engine;

import desktop.KeyHandler;
import desktop.SwingScreen;
import engine.loop.GameLoop;
import entity.CollisionChecker;
import entity.Player;
import graphics.Screen;
import object.SuperObject;
import tile.TileManager;

public final class GameEngine{

    // SCREEN SETTINGS
    static final int originalTileSizes = 16; // 16x16 Tile
    public static final int tileSize = originalTileSizes * 3; // 48x48 Tile
    public static final int maxScreenRow = 16;
    public static final int maxScreenCol = 12;
    public static final int screenWidth = tileSize * maxScreenRow; // 768 pixels
    public static final int screenHeight = tileSize * maxScreenCol; // 576 pixels



    // SYSTEM
    public UI ui = new UI();
    Sound soundEffect = new Sound();
    KeyHandler keyH = new KeyHandler();
    Sound backgroundMusic = new Sound();
    public static TileManager tileManager = new TileManager();
    public static AssetSetter assetSetter = new AssetSetter();
    public static CollisionChecker colChecker = new CollisionChecker();


    // ENTITY & OBJECT
    public static Player player;
    public static SuperObject[] obj = new SuperObject[10];

    public GameLoop gameLoop;
    public Screen screen;

    public GameEngine(Screen screen, GameLoop.Factory factory) {
        this.screen = screen;
        gameLoop = factory.create(this::onUpdate, this::onRender);
        player = new Player(this, keyH);

        setUpGame();
        gameLoop.start();
    }

    public void setUpGame(){
        assetSetter.setObject();
        playBackgroundMusic(0);
    }

    public void onUpdate(){
        player.update(screen.input());
    }

    public void onRender(){

        screen.render(graphics -> {

            // TILE
            tileManager.draw(graphics);

            // OBJECT
            for (SuperObject object : obj) {
                if (object != null) {
                    object.draw(graphics, this);
                }
            }

            // PLAYER
            player.draw(graphics);

            // UI
            ui.draw(graphics);

            graphics.dispose();
        });

    }

    public void playBackgroundMusic(int i){
        backgroundMusic.setFile(i);
        backgroundMusic.play();
        backgroundMusic.loop();
    }

    public void playSE(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void stopMusic(){
        backgroundMusic.stop();
    }


}
