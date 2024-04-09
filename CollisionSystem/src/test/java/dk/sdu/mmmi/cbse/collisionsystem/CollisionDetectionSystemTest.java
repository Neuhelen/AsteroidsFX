package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectionSystemTest {
    private CollisionDetectionSystem collisionSystem;
    @Mock
    private World world;
    Entity entity1 = new Entity();
    Entity entity2 = new Entity();

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        world = Mockito.mock(World.class);
        Mockito.when(world.getEntities()).thenReturn(Arrays.asList(entity1, entity2));
        collisionSystem = new CollisionDetectionSystem();
        entity1.setX(100);
        entity1.setY(100);
        entity1.setSize(10);
        entity1.setRadius(10);
        entity1.setColor("GREY");
        entity2.setX(100);
        entity2.setY(100);
        entity2.setSize(10);
        entity2.setRadius(10);
        entity2.setColor("RED");
        world.addEntity(entity1);
        world.addEntity(entity2);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public void testCheckCollisions_noCollision() {
        entity2.setX(200);

        for (Entity entity : world.getEntities()) {
            if (entity.getHealth() != 0) {
                if (entity.getSize() != 0) {
                    collisionSystem.checkCollisions(entity, world);
                }
            }
        }

        assertEquals(5, entity1.getHealth());
        assertEquals(5, entity2.getHealth());
    }

    @org.junit.jupiter.api.Test
    public void testCheckCollisions_collision() {
        for (Entity entity : world.getEntities()) {
            if (entity.getHealth() != 0) {
                if (entity.getSize() != 0) {
                    collisionSystem.checkCollisions(entity, world);
                }
            }
        }

        assertEquals(4, entity1.getHealth());
        assertEquals(4, entity2.getHealth());
    }


    @org.junit.jupiter.api.Test
    public void testHandleCollision() {
        entity1.setRotation(100);
        entity2.setRotation(200);

        collisionSystem.handleCollision(entity1, entity2);

        assertEquals(4, entity1.getHealth());
        assertTrue(entity1.getRotation() != 100);
    }

    @org.junit.jupiter.api.Test
    public void testCalculateRotation() {
        entity1.setX(0);
        entity1.setY(0);
        entity2.setX(10);
        entity2.setY(10);

        double rotation = collisionSystem.calculateRotation(entity1, entity2);

        assertEquals(Math.PI/2, rotation, 0.0001);
    }
}