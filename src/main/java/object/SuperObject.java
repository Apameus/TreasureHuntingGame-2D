package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    BufferedImage image;
    String name;
    boolean collision = false;
    public int worldX;
    public int worldY;

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Magical performer increase
        if (    worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenX &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenX){

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }
}
