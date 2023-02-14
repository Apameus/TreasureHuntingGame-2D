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
    final int originalTileSizes = 16; // 16x16 Tile
    public final int tileSize = originalTileSizes * 3; // 48x48 Tile
    public final int maxScreenRow = 16;
    public final int maxScreenCol = 12;
    public final int screenWidth = tileSize * maxScreenRow; // 768 pixels
    public final int screenHeight = tileSize * maxScreenCol; // 576 pixels



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
    public SuperObject[] obj = new SuperObject[10];

    public GameLoop gameLoop;
    public Screen screen;

    public GameEngine(Screen screen, GameLoop.Factory factory) {
        this.screen = screen;
        gameLoop = factory.create(this::update, this::onRender);
        player = new Player(this, keyH);

        setUpGame();
        gameLoop.start();
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
