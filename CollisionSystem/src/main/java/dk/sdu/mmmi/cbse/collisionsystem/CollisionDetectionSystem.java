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

    protected void checkCollisions(Entity entity, World world) {
        System.out.println("Function is run.");
        for (Entity otherEntity : world.getEntities()) {
            System.out.println("For-loop is run. ");
            if (otherEntity.getHealth() == 0 || entity.equals(otherEntity) || otherEntity.getColor().equals(entity.getColor())) {
                System.out.println("Collision doesn't occur.");
                continue;
            }

            if (checkCollision(entity, otherEntity)) {
                handleCollision(entity, otherEntity);
                System.out.println("Collision occurs.");
            }
        }
    }

    public Boolean checkCollision(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    protected void handleCollision(Entity entity, Entity otherEntity) {
        entity.setHealth(entity.getHealth() - 1);

        if (otherEntity.getSize() != 0) {
            entity.setRotation(calculateRotation(entity, otherEntity));
        } else {
            otherEntity.setHealth(otherEntity.getHealth() - 1);
        }
    }

    protected double calculateRotation(Entity entity1, Entity entity2) {
        double angle = Math.atan2(entity2.getY() - entity1.getY(), entity2.getX() - entity1.getX());

        return 2 * angle - entity1.getRotation();
    }
}
