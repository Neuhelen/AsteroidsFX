package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;


public class CollisionDetectionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getHealth() != 0) {
                if (entity.getSize() != 0) {
                    checkCollisions(entity, world);
                }
            }
        }
    }

    private void checkCollisions(Entity entity, World world) {
        for (Entity otherEntity : world.getEntities()) {
            if (otherEntity.getHealth() == 0 || entity.equals(otherEntity) || otherEntity.getColor().equals(entity.getColor())) {
                continue;
            }

            if (checkCollision(entity, otherEntity)) {
                handleCollision(entity, otherEntity);
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

                if (isPointInsidePolygon(x1, y1, polygon2, entity2) || isPointInsidePolygon(x2, y2, polygon1, entity1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void handleCollision(Entity entity, Entity otherEntity) {
        entity.setHealth(entity.getHealth() - 1);

        if (otherEntity.getSize() != 0) {
            entity.setRotation(calculateRotation(entity, otherEntity));
        } else {
            otherEntity.setHealth(otherEntity.getHealth() - 1);
        }
    }

    private double calculateRotation(Entity entity1, Entity entity2) {
        double angle = Math.atan2(entity2.getY() - entity1.getY(), entity2.getX() - entity1.getX());

        return 2 * angle - entity1.getRotation();
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
