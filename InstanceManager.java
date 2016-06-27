import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class InstanceManager {
    private static InstanceManager res = new InstanceManager();

    private List<Instance> instanceList = new ArrayList<>();
    private boolean[][] solidMap;

    private InstanceManager() {}

    static void update() {

    }

    static void render(Graphics2D g) {

    }

    static boolean[][] getSolidMap() {
        return res.solidMap;
    }

    static List<Instance> getInstanceList() {
        return res.instanceList;
    }
}
