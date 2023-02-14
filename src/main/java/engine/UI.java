package engine;

import engine.loop.FrameBasedGameLoop;
import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI{
    //
    GameEngine gp;
    BufferedImage keyImage ;
    Font font_arial_40, font_arial_80B;
    //
    int messageCounter = 0;
    public String message = "";
    public boolean messageOn = false;
    //
    public boolean gameFinished = false;
    //
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");


    public UI(GameEngine gp) {
        this.gp = gp;
        font_arial_40 = new Font("Arial", Font.PLAIN, 40);
        font_arial_80B = new Font("Arial", Font.BOLD, 80);
        Key key = new Key();
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2){

        if (gameFinished){
            FrameBasedGameLoop.shouldRun = false;
            g2.setFont(font_arial_40);
            g2.setColor(Color.white);

            String text = "You found the treasure!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2 - gp.tileSize * 3 ;
            g2.drawString(text, x, y);

            text = "Your time is: " + dFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + gp.tileSize * 4 ;
            g2.drawString(text, x, y);

            g2.setFont(font_arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + gp.tileSize * 2 ;
            g2.drawString(text, x, y);

            //gp.gameLoop.shouldRun = false;
        }

        else {
            g2.setFont(font_arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.playerKeys, 74, 65);

            // ΤΙΜΕ
            playTime += (double) 1 / 60;
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter == 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
