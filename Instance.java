import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

abstract class Instance {
    private double x, y;
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
}
