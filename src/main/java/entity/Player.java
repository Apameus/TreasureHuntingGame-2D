package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getInput("/player/boy_up_1.png"));
            up2 = ImageIO.read(getInput("/player/boy_up_2.png"));
            down1 = ImageIO.read(getInput("/player/boy_down_1.png"));
            down2 = ImageIO.read(getInput("/player/boy_down_2.png"));
            left1 = ImageIO.read(getInput("/player/boy_left_1.png"));
            left2 = ImageIO.read(getInput("/player/boy_left_2.png"));
            right1 = ImageIO.read(getInput("/player/boy_right_1.png"));
            right2 = ImageIO.read(getInput("/player/boy_right_2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInput(String name) {
        return Objects.requireNonNull(getClass().getResourceAsStream(name));
    }

    public void update(){

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if (keyH.upPressed){
                direction = "up";
                worldY -= speed;
            }
            if (keyH.downPressed){
                direction = "down";
                worldY += speed;
            }
            if (keyH.leftPressed){
                direction = "left";
                worldX -= speed;
            }
            if (keyH.rightPressed){
                direction = "right";
                worldX += speed;
            }

            spriteCounter++;
            if (spriteCounter > 12){
                if (spriteNumber == 1){
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }


    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.RED);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        switch (direction){
            case "up" -> {
                if (spriteNumber == 1){ image = up1; }
                else if (spriteNumber == 2) { image = up2; }
            }
            case "down" -> {
                if (spriteNumber == 1){ image = down1; }
                else if (spriteNumber == 2) { image = down2; }
            }
            case "left" -> {
                if (spriteNumber == 1){ image = left1; }
                else if (spriteNumber == 2) { image = left2; }
            }
            case "right" -> {
                if (spriteNumber == 1){ image = right1; }
                else if (spriteNumber == 2) { image = right2; }
            }
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
