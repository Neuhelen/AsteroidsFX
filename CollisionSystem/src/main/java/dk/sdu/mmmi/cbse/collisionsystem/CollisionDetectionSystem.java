package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.asteroidsystem.Asteroid;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;

public class CollisionDetectionSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Asteroid) {
                checkCollisions(entity, world);
            }

            if (entity instanceof Enemy) {
                checkCollisions(entity, world);
            }

            if (entity instanceof Player) {
                checkPlayerCollisions((Player) entity, world);
            }
        }
    }



    private void checkCollisions(Entity entity, World world) {
        for (Entity otherEntity : world.getEntities()) {
            if (entity.equals(otherEntity)) {
                continue;
            }

            // Check if the entities have collided using Pythagorean theorem
            if (checkCollision(entity, otherEntity)) {
                // Handle the collision (destroy the entities, split asteroids, etc.)
                handleCollision(entity, otherEntity, world);
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

                // Adjust the threshold based on your game's requirements
                if (isPointInsidePolygon(x1, y1, polygon2, entity2) || isPointInsidePolygon(x2, y2, polygon1, entity1)) {
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision
    }

    private void handleCollision(Entity entity, Entity otherEntity, World world) {
        if(!(otherEntity instanceof Bullet)) {
            entity.setRotation(calculateRotation(entity, otherEntity));
        }
    }

    private void handlePlayerCollision(Player player, Entity otherEntity, World world) {
        System.out.println(player.getHealth());
        if(player.getHealth() != 1) {
            player.setRotation(calculateRotation(player, otherEntity));
            player.setHealth(player.getHealth() - 1);
        } else {
            world.removeEntity(player);
        }
    }

    private double calculateRotation(Entity entity1, Entity entity2) {
        // Calculate the angle between the centers of the two entities
        double angle = Math.atan2(entity2.getY() - entity1.getY(), entity2.getX() - entity1.getX());

        // Calculate the new rotation as the reflection of the current rotation
        double newRotation = 2 * angle - entity1.getRotation();

        return newRotation;
    }

    private boolean isPointInsidePolygon(double x, double y, double[] polygon, Entity entity) {
        int count = 0;
        int numPoints = polygon.length / 2;

        for (int i = 0, j = numPoints - 1; i < numPoints; j = i++) {
            double xi = polygon[i * 2] + entity.getX();
            double yi = polygon[i * 2 + 1] + entity.getY();
            double xj = polygon[j * 2] + entity.getX();
            double yj = polygon[j * 2 + 1] + entity.getY();

            if ((yi > y) != (yj > y) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
                count++;
            }
        }

        return count % 2 == 1;
    }
}
