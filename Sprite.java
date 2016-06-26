import java.awt.*;
import java.awt.geom.AffineTransform;

class Sprite {
    private Image image[];
    private int imageNumber;

    public void render(Graphics2D g, AffineTransform transform, double imageIndex) {
        g.drawImage(image[(int) imageIndex % imageNumber], transform, null);
    }
}
