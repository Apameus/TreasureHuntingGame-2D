package entity;

import graphics.Control;
import engine.GameEngine;
import desktop.KeyHandler;
import graphics.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static entity.Direction.*;
import static engine.GameEngine.*;
import static graphics.Control.CONTROLS;

public class Player extends Entity{
    GameEngine gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int playerKeys = 0;

    public Player(GameEngine gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = screenWidth / 2 - (tileSize / 2);
        screenY = screenHeight / 2 - (tileSize / 2);

        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = tileSize * 23;
        worldY = tileSize * 21;
        speed = 4;
        direction = DOWN;
    }

    public void getPlayerImage(){
            up1 = getImage("/player/boy_up_1.png");
            up2 = getImage("/player/boy_up_2.png");
            down1 = getImage("/player/boy_down_1.png");
            down2 = getImage("/player/boy_down_2.png");
            left1 = getImage("/player/boy_left_1.png");
            left2 = getImage("/player/boy_left_2.png");
            right1 = getImage("/player/boy_right_1.png");
            right2 = getImage("/player/boy_right_2.png");
    }

    private BufferedImage getImage(String name) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(name)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Input input){

        for (Control control : CONTROLS) {
            if (input.isActive(control)) {

                direction = control.direction();

                // CHECK TILE COLLISION
                collisionOn = false;
                colChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = colChecker.checkObject(this, true);
                pickUpObjects(objIndex);

                // IF COLLISION is FALSE, PLAYER CAN MOVE
                if (!collisionOn) {
                    control.direction().move(this);
                }

                // MOVING MOTION COUNTER
                spriteCounter++;
                if (spriteCounter > 12) {
                    if (spriteNumber == 1) {
                        spriteNumber = 2;
                    } else if (spriteNumber == 2) {
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
            }
        }
    }

    // MOVING MOTION
    public void draw(Graphics2D g2){

        BufferedImage image = null;
        switch (direction){
            case UP -> {
                if (spriteNumber == 1){ image = up1; }
                else if (spriteNumber == 2) { image = up2; }
            }
            case DOWN -> {
                if (spriteNumber == 1){ image = down1; }
                else if (spriteNumber == 2) { image = down2; }
            }
            case LEFT -> {
                if (spriteNumber == 1){ image = left1; }
                else if (spriteNumber == 2) { image = left2; }
            }
            case RIGHT -> {
                if (spriteNumber == 1){ image = right1; }
                else if (spriteNumber == 2) { image = right2; }
            }
        }
        g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
    }

    public void pickUpObjects(int i){
        if (i != 999){
            String objName = obj[i].name;
            switch (objName){
                case "KEY" -> {
                    gp.playSE(1);
                    playerKeys++;
                    obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                }
                case "DOOR" -> {
                    if (playerKeys > 0){
                        gp.playSE(4);
                        obj[i] = null;
                        playerKeys--;
                    }
                }
                case "BOOTS" -> {
                    gp.playSE(3);
                    speed += 2;
                    obj[i] = null;
                    gp.ui.showMessage("Speed Boost!");
                }
                case "CHEST" -> {
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(2);
                }
            }
        }
    }
}
