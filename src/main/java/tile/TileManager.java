package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNumber;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tile = new Tile[10];
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRaw];

        getTileImage();
        loadMap();
    }

    private void getTileImage(){

        tile[0] = new Tile();
        tile[1] = new Tile();
        tile[2] = new Tile();
        tile[3] = new Tile();
        tile[4] = new Tile();
        tile[5] = new Tile();
        try {
            tile[0].image = ImageIO.read(getInput("/tiles/grass.png"));
            tile[1].image = ImageIO.read(getInput("/tiles/wall.png"));
            tile[2].image = ImageIO.read(getInput("/tiles/water.png"));
            tile[3].image = ImageIO.read(getInput("/tiles/earth.png"));
            tile[4].image = ImageIO.read(getInput("/tiles/tree.png"));
            tile[5].image = ImageIO.read(getInput("/tiles/sand.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInput(String name) {
        return Objects.requireNonNull(getClass().getResourceAsStream(name));
    }

    public void loadMap() {
        List<String> lines = getAllLines("src/main/resources/maps/world1.txt");

        int col = 0;
        int row = 0;
        int i = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRaw){
            String line = lines.get(i);
            String[] numbers = line.split(" ");

            while (col < gp.maxWorldCol){
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

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRaw) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Magical performer increase
            if (    worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenX &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenX){

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }

            worldCol++;


            if (worldCol == gp.maxWorldCol) {
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
