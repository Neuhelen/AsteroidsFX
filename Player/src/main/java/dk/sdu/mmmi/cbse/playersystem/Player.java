package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Emil
 */
public class Player extends Entity {
    private int lastShotTime = 0;

    public int getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(int lastShotTime) {
        this.lastShotTime = lastShotTime;
    }
}
