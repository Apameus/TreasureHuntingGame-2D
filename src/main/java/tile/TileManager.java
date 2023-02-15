package tile;

import engine.GameEngine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static engine.GameEngine.tileSize;

public class TileManager {

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRaw = 50;
    public Tile[] tile;
    public int[][] mapTileNumber;

    public TileManager() {
        this.tile = new Tile[50];
        mapTileNumber = new int[maxWorldCol][maxWorldRaw];

        setTileImage();
        loadMap();
    }

    private void setTileImage(){

        set(10, "grass00", false);
        set(11, "grass01", false);
        set(12, "water00", true);
        set(13, "water01", true);
        set(14, "water02", true);
        set(15, "water03", true);
        set(16, "water04", true);
        set(17, "water05", true);
        set(18, "water06", true);
        set(19, "water07", true);
        set(20, "water08", true);
        set(21, "water09", true);
        set(22, "water10", true);
        set(23, "water11", true);
        set(24, "water12", true);
        set(25, "water13", true);
        set(26, "road00", false);
        set(27, "road01", false);
        set(28, "road02", false);
        set(29, "road03", false);
        set(30, "road04", false);
        set(31, "road05", false);
        set(32, "road06", false);
        set(33, "road07", false);
        set(34, "road08", false);
        set(35, "road09", false);
        set(36, "road10", false);
        set(37, "road11", false);
        set(38, "road12", false);
        set(39, "earth", false);
        set(40, "wall", true);
        set(41, "tree", true);
    }

    private void set(int index, String name, boolean collision){
        String fileName = "/tiles/" + name + ".png";
        tile[index] = new Tile();
        tile[index].image = getImage(fileName);
        tile[index].collision = collision;
    }

    private BufferedImage getImage(String name) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(name)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMap() {
        List<String> lines = getAllLines("src/main/resources/maps/world2.txt");

        int col = 0;
        int row = 0;
        int i = 0;

        while (row < maxWorldRaw){
            String line = lines.get(i);
            String[] numbers = line.split(" ");

            while (col < maxWorldCol){
                int num = Integer.parseInt(numbers[col]);
                mapTileNumber[col][row] = num;
                col++;
            }
            col = 0;
            row++;
            i++;
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < maxWorldCol && worldRow < maxWorldRaw) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * tileSize;
            int worldY = worldRow * tileSize;
            int screenX = worldX - GameEngine.player.worldX + GameEngine.player.screenX;
            int screenY = worldY - GameEngine.player.worldY + GameEngine.player.screenY;

            // Magical performer increase
            if (    worldX + tileSize > GameEngine.player.worldX - GameEngine.player.screenX &&
                    worldX - tileSize < GameEngine.player.worldX + GameEngine.player.screenX &&
                    worldY + tileSize > GameEngine.player.worldY - GameEngine.player.screenX &&
                    worldY - tileSize < GameEngine.player.worldY + GameEngine.player.screenX){

                g2.drawImage(tile[tileNum].image, screenX, screenY, tileSize, tileSize, null);
            }

            worldCol++;

            if (worldCol == maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    private List<String> getAllLines(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
