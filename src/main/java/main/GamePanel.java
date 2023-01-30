package main;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSizes = 16; // 16x16 Tile
    final int scale = 3;

    final int tileSize = originalTileSizes * scale; // 48x48 Tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int fps = 60;
    final long NANOS_PER_SECOND = Duration.ofSeconds(1).toNanos();
    final double DRAW_INTERVAL = (double) NANOS_PER_SECOND / fps;  // 0.016666 seconds

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
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

    // DEFAULT PLAYER INFO
    private int playerX = 100;
    private int playerY = 100;
    private int speed = 4   ;

    public void update(){
        if (keyH.upPressed){
            playerY -= speed;
        }
        if (keyH.downPressed){
            playerY += speed;
        }
        if (keyH.leftPressed){
            playerX -= speed;
        }
        if (keyH.rightPressed){
            playerX += speed;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
