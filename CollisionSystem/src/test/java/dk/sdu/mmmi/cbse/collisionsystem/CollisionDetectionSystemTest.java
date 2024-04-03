package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectionSystemTest {
    private CollisionDetectionSystem collisionSystem;

    @Mock
    private World world;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        collisionSystem = new CollisionDetectionSystem();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public void testCheckCollisions_noCollision() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        entity1.setX(100);
        entity1.setY(100);
        entity1.setHealth(1);
        entity1.setSize(10);
        entity1.setRadius(10);
        entity1.setColor("GREY");
        entity2.setX(100);
        entity2.setY(100);
        entity2.setHealth(1);
        entity2.setSize(10);
        entity2.setRadius(10);
        entity2.setColor("RED");
        world.addEntity(entity1);
        world.addEntity(entity2);

        collisionSystem.checkCollisions(entity1, world);

        assertEquals(1, entity1.getHealth());
        assertEquals(1, entity2.getHealth());
    }

    @org.junit.jupiter.api.Test
    public void testCheckCollisions_collision() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        entity1.setX(100);
        entity1.setY(100);
        entity1.setHealth(1);
        entity1.setSize(10);
        entity1.setRadius(10);
        entity1.setColor("GREY");
        entity2.setX(100);
        entity2.setY(100);
        entity2.setHealth(1);
        entity2.setSize(10);
        entity2.setRadius(10);
        entity2.setColor("RED");
        world.addEntity(entity1);
        world.addEntity(entity2);

        collisionSystem.checkCollisions(entity1, world);

        assertEquals(0, entity1.getHealth());
        assertEquals(0, entity2.getHealth());
    }


    @org.junit.jupiter.api.Test
    public void testHandleCollision() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        entity1.setHealth(1);
        entity1.setSize(10);
        entity1.setRotation(100);
        entity2.setHealth(1);
        entity2.setSize(10);
        entity2.setRotation(200);
        world.addEntity(entity1);
        world.addEntity(entity2);

        collisionSystem.handleCollision(entity1, entity2);

        assertEquals(0, entity1.getHealth());
        assertTrue(entity1.getRotation() != 100);
    }

    @org.junit.jupiter.api.Test
    public void testCalculateRotation() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity();
        entity1.setSize(1);
        entity2.setSize(1);
        entity1.setX(0);
        entity1.setY(0);
        entity2.setX(100);
        entity2.setY(0);

        double rotation = collisionSystem.calculateRotation(entity1, entity2);

        assertEquals(Math.PI, rotation, 0.0001);
    }

}