package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends SuperObject{

    public Door() {
        name = "DOOR";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
