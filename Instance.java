import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

abstract class Instance {
    protected double x, y, width, height;
    private boolean active, visible, solid;
    protected int state;

    protected enum State {
        Idle
    }

    protected Map<Integer, Sprite> spriteMap;
    protected double imageSpeed;
    protected AffineTransform transform;
    private double imageIndex;

    Instance(double x, double y) {
        this.x = x;
        this.y = y;
        spriteMap = new HashMap<>();
        spriteMap.put(State.Idle.ordinal(), SpriteManager.getSprite("Unknown"));
        transform = new AffineTransform();
    }

    void update() {
        if(active) {
            input();
            physics();
            move();
            animate();
        }
    }

    void render(Graphics2D g) {
        if(!visible) {
            transform.translate(x, y);
            spriteMap.get(state).render(g, transform, imageIndex);
            transform.setToIdentity();
        }
    }

    private void animate() {
        int lastState = state;
        state();
        imageIndex = lastState == state ? imageIndex + imageSpeed : 0;
    }

    protected abstract void input();
    protected abstract void physics();
    protected abstract void move();
    protected abstract void state();

    protected boolean isIntersecting(Instance instance) {
        return 2 * (x - instance.getX()) < width + instance.getWidth() && 2 * (y - instance.getY()) < height + instance.getHeight();
    }

    protected abstract void collidesWith(Instance instance);

    private double getX() {
        return x;
    }

    private double getY() {
        return y;
    }

    private double getWidth() {
        return width;
    }

    private double getHeight() {
        return height;
    }

    private boolean isActive() {
        return active;
    }

    private boolean isVisible() {
        return visible;
    }

    private boolean isSolid() {
        return solid;
    }
}
