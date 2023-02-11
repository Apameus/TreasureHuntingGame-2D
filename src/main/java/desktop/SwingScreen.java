package desktop;

import graphics.Input;
import graphics.Screen;
import main.GameEngine;

import javax.swing.*;
import java.awt.*;

public final class SwingScreen implements Screen {

    private final Canvas canvas;
    private final KeyHandler keyH;

    public SwingScreen(int width, int height) {
        keyH = new KeyHandler();
        canvas = new Canvas();

        showFrame(width, height);
        canvas.createBufferStrategy(2);
    }

    public void showFrame(int screenWidth, int screenHeight) {

        JFrame frame = new JFrame("Dungeon Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panel.add(canvas, BorderLayout.CENTER);

        frame.add(panel);
        frame.pack();

        frame.addKeyListener(keyH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public Input input() {
        return null;
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }
}
