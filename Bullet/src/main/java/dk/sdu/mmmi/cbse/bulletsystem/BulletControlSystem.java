package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            //This part sets the initial bullet velocity based on the shooter's rotation.
            double bulletDirectionX = Math.cos(Math.toRadians(bullet.getRotation()));
            double bulletDirectionY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + bulletDirectionX * bullet.getVelocityX());
            bullet.setY(bullet.getY() + bulletDirectionY * bullet.getVelocityY());
            bullet.setRadius(1);

            if (bullet.getX() < 0) {
                bullet.setHealth(0);
            }

            if (bullet.getX() > gameData.getDisplayWidth()) {
                bullet.setHealth(0);
            }

            if (bullet.getY() < 0) {
                bullet.setHealth(0);
            }

            if (bullet.getY() > gameData.getDisplayHeight()) {
                bullet.setHealth(0);
            }

            if (bullet.getHealth() == 0) {
                bullet.setValid(false);
            }
        }
    }
}