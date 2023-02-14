package desktop;

import graphics.Input;
import graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.function.Consumer;

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
        return keyH;
    }

    @Override
    public void render(Consumer<Graphics2D> action) {
        BufferStrategy buffer = canvas.getBufferStrategy();
        do {
            do {
                Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();

                action.accept(graphics);

                graphics.dispose();
            }while (buffer.contentsRestored());
            buffer.show();
        }while (buffer.contentsLost());
    }

    @Override
    public int width() {
        return canvas.getWidth();
    }

    @Override
    public int height() {
        return canvas.getHeight();
    }
}
