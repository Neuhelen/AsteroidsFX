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
        // Process bullets in the world
        for (Entity bullet : world.getEntities(Bullet.class)) {
            // Update bullet position based on velocity
            updateBulletPosition(bullet, gameData);

            // Check for collisions or if the bullet is out of bounds
            checkBulletCollision(bullet, world);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        // Implement bullet creation logic here
        // You might want to set the bullet's initial position, velocity, and other properties
        Entity bullet = new Bullet();
        bullet.setX(shooter.getX()); // Set initial position to the shooter's position
        bullet.setY(shooter.getY());
        bullet.setRotation(shooter.getRotation()); // Set initial rotation to the shooter's rotation

        // Set initial bullet velocity based on the shooter's rotation
        double bulletSpeed = 300; // Adjust the speed as needed
        double bulletDirectionX = Math.cos(Math.toRadians(shooter.getRotation()));
        double bulletDirectionY = Math.sin(Math.toRadians(shooter.getRotation()));
        bullet.setVelocity(bulletSpeed * bulletDirectionX, bulletSpeed * bulletDirectionY);

        return bullet;
    }

    private void updateBulletPosition(Entity bullet, GameData gameData) {
        // Implement bullet position update logic based on its velocity
        double x = bullet.getX() + bullet.getVelocityX() * gameData.getDelta();
        double y = bullet.getY() + bullet.getVelocityY() * gameData.getDelta();
        bullet.setX(x);
        bullet.setY(y);
    }

    private void checkBulletCollision(Entity bullet, World world) {
        // Implement collision logic here
        // Check if the bullet collides with other entities (e.g., enemies, asteroids)
        // Remove the bullet if it's out of bounds or hit something
        world.removeEntity(bullet);
    }
}