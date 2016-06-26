import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GameFrame extends JFrame implements Runnable, KeyListener {
    private boolean running, pause;

    private GameFrame() {
        super("Smash");
        setSize(960, 640);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addKeyListener(this);
        createBufferStrategy(3);
        new Thread(this).start();
        running = true;
    }

    public void run() {
        while(running) {
            if(!pause) update();
            render();
        }
    }

    private void update() {
        InstanceManager.update();
    }

    private void render() {
        Graphics2D g = (Graphics2D) getGraphics();
        InstanceManager.render(g);
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }
}
