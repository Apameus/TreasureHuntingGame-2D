package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class GamePanel extends JPanel implements Runnable{

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
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRaw * tileSize;

    // FPS
    int fps = 60;
    final long NANOS_PER_SECOND = Duration.ofSeconds(1).toNanos();
    final double DRAW_INTERVAL = (double) NANOS_PER_SECOND / fps;  // 0.016666 seconds

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        //this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void run() {

        double delta = 0;
        long lastUpdateTime = System.nanoTime();

        long timer = 0;
        long drawCount = 0;

        while (gameThread != null){

            long now = System.nanoTime();
            delta += (now - lastUpdateTime) / DRAW_INTERVAL;
            timer += now - lastUpdateTime;

            lastUpdateTime = now;

            if (delta >= 1){
                update();
                repaint();
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

    public void update(){
        player.update();
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
