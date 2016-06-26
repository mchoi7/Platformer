import java.util.HashMap;
import java.util.Map;

class SpriteManager {
    private static SpriteManager res = new SpriteManager();

    private Map<String, Sprite> spriteMap = new HashMap<>();

    private SpriteManager() {
        loadSprite("test");
    }

    private void loadSprite(String spriteIndex) {

        res.spriteMap.put(spriteIndex, new Sprite());
    }

    static Sprite getSprite(String spriteIndex) {
        return res.spriteMap.get(spriteIndex);
    }

}
