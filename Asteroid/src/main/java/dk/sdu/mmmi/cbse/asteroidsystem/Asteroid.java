package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;


public class Asteroid extends Entity {
    private double size = 1;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
