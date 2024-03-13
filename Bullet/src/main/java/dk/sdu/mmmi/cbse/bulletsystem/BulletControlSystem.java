package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {


    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            //This part sets the initial bullet velocity based on the shooter's rotation.
            double bulletDirectionX = Math.cos(Math.toRadians(bullet.getRotation()));
            double bulletDirectionY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + bulletDirectionX * bullet.getVelocityX());
            bullet.setY(bullet.getY() + bulletDirectionY * bullet.getVelocityY());

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
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setHealth(1);
        bullet.setSize(0);

        //This part sets the initial position to the shooter's position.
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());

        //This part sets the initial rotation to the shooter's rotation.
        bullet.setRotation(shooter.getRotation());

        bullet.setVelocity(shooter.getVelocityX() * 1.5, shooter.getVelocityY() * 1.5);

        bullet.setPolygonCoordinates(-2,2,2,2,2,-2,-2,-2);

        bullet.setColor(shooter.getColor());

        return bullet;
    }
}