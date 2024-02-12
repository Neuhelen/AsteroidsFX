package dk.sdu.mmmi.cbse.common.data;

public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();

    private long lastFrameTime;
    private float delta;

    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }


    public GameData() {
        // Initialize lastFrameTime to the current time
        lastFrameTime = System.nanoTime();
    }

    public void update() {
        // Calculate delta time in seconds
        long currentTime = System.nanoTime();
        delta = (float) ((currentTime - lastFrameTime) / 1e9);
        lastFrameTime = currentTime;
    }

    public float getDelta() {
        return delta;
    }
}
