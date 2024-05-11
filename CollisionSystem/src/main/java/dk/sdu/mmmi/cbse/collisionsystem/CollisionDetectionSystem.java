package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.Random;


public class CollisionDetectionSystem implements IPostEntityProcessingService {

    private Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getHealth() != 0) {
                if (entity.getRadius() != 0) {
                    checkCollisions(entity, world);
                }
            }
        }
    }

    protected void checkCollisions(Entity entity, World world) {
        for (Entity otherEntity : world.getEntities()) {
            if (otherEntity.getHealth() == 0 || entity.equals(otherEntity) || otherEntity.getColor().equals(entity.getColor())) {
                continue;
            }

            if (checkCollision(entity, otherEntity)) {
                handleCollision(entity, otherEntity);
            }
        }
    }

    public Boolean checkCollision(Entity entity1, Entity entity2) {
        double differenceX = entity1.getX() - entity2.getX();
        double differenceY = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(differenceX * differenceX + differenceY * differenceY);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    protected void handleCollision(Entity entity, Entity otherEntity) {
        entity.setHealth(entity.getHealth() - 1);

        if (otherEntity.getSize() != 0) {
            entity.setRotation(entity.getRotation() + random.nextInt(46) + 45);
        } 
    }
}
