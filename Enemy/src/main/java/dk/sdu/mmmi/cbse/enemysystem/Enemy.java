package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;


public class Enemy extends Entity {

    private int lastShotTime = 0;


    public int getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(int lastShotTime) {
        this.lastShotTime = lastShotTime;
    }
}
