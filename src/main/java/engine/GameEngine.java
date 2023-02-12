package engine;

import desktop.KeyHandler;
import desktop.SwingScreen;
import engine.loop.FrameBasedGameLoop;
import engine.loop.GameLoop;
import entity.CollisionChecker;
import entity.Player;
import graphics.Screen;
import object.SuperObject;
import tile.TileManager;

import java.time.Duration;

public final class GameEngine{

    // SCREEN SETTINGS
    final int originalTileSizes = 16; // 16x16 Tile
    final int scale = 3;

    public final int tileSize = originalTileSizes * scale; // 48x48 Tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRaw = 50;


    // SYSTEM
    public UI ui = new UI(this);
    Sound soundEffect = new Sound();
    KeyHandler keyH = new KeyHandler();
    Sound backgroundMusic = new Sound();
    public TileManager tileManager = new TileManager(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public CollisionChecker colChecker = new CollisionChecker(this);


    // ENTITY & OBJECT
    public Player player;
    public SuperObject obj[] = new SuperObject[10];

    public GameLoop gameLoop;
    public Screen screen;

    public GameEngine(Screen screen, GameLoop.Factory factory) {
//        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
//        this.setDoubleBuffered(true);
//        this.addKeyListener(keyH);
        this.screen = screen;

        gameLoop = factory.create(this::update, this::onRender);

        player = new Player(this, keyH);

        setUpGame();

        gameLoop.start();

//        startGameThread();

    }
    public void setUpGame(){
        assetSetter.setObject();
        playBackgroundMusic(0);
    }

    public void update(){
        player.update(screen.input());
    }

    public void onRender(){

        screen.render(graphics -> {

            // TILE
            tileManager.draw(graphics);

            // OBJECT
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null){
                    obj[i].draw(graphics, this);
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


    public static void main(String[] args) {
        SwingScreen screen = new SwingScreen(768, 576);
        GameEngine gameEngine = new GameEngine(screen, GameLoop::createFrameBasedGameLoop);

    }
}
