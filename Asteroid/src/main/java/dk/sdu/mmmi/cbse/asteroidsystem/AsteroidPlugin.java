package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;
    Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        asteroid.setPolygonCoordinates(createShape(random.nextInt(50)));
        asteroid.setColor("RED");

        int spawnLocation = random.nextInt(4);

        switch (spawnLocation) {
            case 0:
                // Spawn on the top side
                asteroid.setX(random.nextDouble(0,1) * gameData.getDisplayWidth());
                asteroid.setY(gameData.getDisplayHeight());
                break;
            case 1:
                // Spawn on the right side
                asteroid.setX(gameData.getDisplayWidth());
                asteroid.setY(random.nextDouble(0,1) * gameData.getDisplayHeight());
                break;
            case 2:
                // Spawn on the bottom side
                asteroid.setX(random.nextDouble(0,1) * gameData.getDisplayWidth());
                asteroid.setY(0);
                break;
            case 3:
                // Spawn on the left side
                asteroid.setX(0);
                asteroid.setY(random.nextDouble(0,1) * gameData.getDisplayHeight());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + random.nextInt(4));
        }

        return asteroid;
    }

    public double[] createShape (int size) {
        Random randSize = new Random();
        double big = randSize.nextDouble((size/2),size);
        double small = randSize.nextDouble(0, size/2);
        return new double[]{
                big,-small,
                small, -big,
                -small, -big,
                -big,-small,
                -big,small,
                -small, big,
                small, big,
                big,small
        };
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroid);
    }
}