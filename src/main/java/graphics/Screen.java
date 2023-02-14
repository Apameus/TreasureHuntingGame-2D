package graphics;

import java.awt.*;
import java.util.function.Consumer;

public interface Screen {

    Input input();
    void render(Consumer<Graphics2D> action);

    int width();

    int height();
}
