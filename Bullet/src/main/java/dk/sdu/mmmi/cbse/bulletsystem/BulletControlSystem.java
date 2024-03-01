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
            //this part sets the initial bullet velocity based on the shooter's rotation.
            double bulletDirectionX = Math.cos(Math.toRadians(bullet.getRotation()));
            double bulletDirectionY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + bulletDirectionX * bullet.getVelocityX());
            bullet.setY(bullet.getY() + bulletDirectionY * bullet.getVelocityY());
        }

        /*
        //This part processes bullets in the world.
        for (Entity bullet : world.getEntities(Bullet.class)) {
            //This part updates the bullets' position based on velocity.
            updateBulletPosition(bullet, gameData);

            //This part checks for collisions.
            checkBulletCollision(bullet, world);
        }
        */
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setHealth(1);

        //This part sets the initial position to the shooter's position.
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());

        //This part sets the initial rotation to the shooter's rotation.
        bullet.setRotation(shooter.getRotation());

        bullet.setVelocity(1, 1);

        bullet.setPolygonCoordinates(-2,2,2,2,2,-2,-2,-2);

        bullet.setColor(shooter.getColor());

        return bullet;
    }
    /*
    private void updateBulletPosition(Entity bullet, GameData gameData) {
        // Implement bullet position update logic based on its velocity
        double x = bullet.getX() + bullet.getVelocityX() * gameData.getDelta();
        double y = bullet.getY() + bullet.getVelocityY() * gameData.getDelta();
        bullet.setX(x);
        bullet.setY(y);
    }
    //
    private void checkBulletCollision(Entity bullet, World world) {
        // Implement collision logic here
        // Check if the bullet collides with other entities (e.g., enemies, asteroids)
        // Remove the bullet if it's out of bounds or hit something
        world.removeEntity(bullet);
    }
    */
}