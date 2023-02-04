package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends SuperObject{

    public Boots() {
        name = "BOOTS";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
