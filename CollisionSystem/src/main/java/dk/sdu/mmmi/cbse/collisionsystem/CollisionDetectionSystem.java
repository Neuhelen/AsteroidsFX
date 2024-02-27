package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.asteroidsystem.Asteroid;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.Random;

public class CollisionDetectionSystem implements IEntityProcessingService {
    Random random = new Random();
    double collisionRadius = 50;

    @Override
    public void process(GameData gameData, World world) {
        // Loop through all entities in the world
        for (Entity entity : world.getEntities(Asteroid.class)) {
            // Check for collisions with asteroids
            if (entity instanceof Asteroid) {
                checkAsteroidCollisions((Asteroid) entity, world);
            }

            // Add additional checks for other entity types (player, enemy, bullets) as needed
            // ...

            // Example: Check collisions with player
            if (entity instanceof Player) {
                checkPlayerCollisions((Player) entity, world);
            }
        }
    }

    private void checkAsteroidCollisions(Asteroid asteroid, World world) {
        // Loop through all entities in the world
        for (Entity otherEntity : world.getEntities()) {
            // Skip checking collision with itself
            if (asteroid.equals(otherEntity)) {
                continue;
            }

            // Check if the entities have collided using Pythagorean theorem
            if (checkCollision(asteroid, otherEntity)) {
                // Handle the collision (destroy the entities, split asteroids, etc.)
                handleAsteroidCollision(asteroid, otherEntity, world);
            }
        }
    }

    private void checkPlayerCollisions(Player player, World world) {
        // Loop through all entities in the world
        for (Entity otherEntity : world.getEntities()) {
            // Skip checking collision with itself
            if (player.equals(otherEntity)) {
                continue;
            }

            // Check if the entities have collided using Pythagorean theorem
            if (checkCollision(player, otherEntity)) {
                // Handle the collision (destroy the entities, decrement health, etc.)
                handlePlayerCollision(player, otherEntity, world);
            }
        }
    }

    private boolean checkCollision(Entity entity1, Entity entity2) {
        double[] polygon1 = entity1.getPolygonCoordinates();
        double[] polygon2 = entity2.getPolygonCoordinates();

        for (int i = 0; i < polygon1.length; i += 2) {
            double x1 = polygon1[i] + entity1.getX();
            double y1 = polygon1[i + 1] + entity1.getY();

            for (int j = 0; j < polygon2.length; j += 2) {
                double x2 = polygon2[j] + entity2.getX();
                double y2 = polygon2[j + 1] + entity2.getY();

                // Check for collision using Pythagorean theorem
                double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

                // Adjust the threshold based on your game's requirements
                if (distance < collisionRadius) {
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision
    }

    private void handleAsteroidCollision(Asteroid asteroid, Entity otherEntity, World world) {
        if(!(otherEntity instanceof Player)) {
            asteroid.setRotation(asteroid.getRotation() - random.nextInt(180) + 90);
            otherEntity.setRotation(otherEntity.getRotation() - random.nextInt(180) + 90);
        }
    }

    private void handlePlayerCollision(Player player, Entity otherEntity, World world) {
        if(player.getHealth() != 1) {
            player.setRotation(player.getRotation() - random.nextInt(180) + 90);
            otherEntity.setRotation(otherEntity.getRotation() - random.nextInt(180) + 90);
        } else {
            world.removeEntity(player);
        }
    }
}
