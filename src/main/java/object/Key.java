package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Key extends SuperObject{

    public Key() {
        name = "KEY";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
