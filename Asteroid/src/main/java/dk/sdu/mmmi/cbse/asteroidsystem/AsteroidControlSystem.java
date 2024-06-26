package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {

    private Random random = new Random();

    private AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
    private long lastAsteroidSpawn = System.nanoTime();

    @Override
    public void process(GameData gameData, World world) {
        if (System.nanoTime() - lastAsteroidSpawn > 2500000000L) {
            Entity asteroid = asteroidPlugin.createAsteroid(gameData);
            world.addEntity(asteroid);
            lastAsteroidSpawn = System.nanoTime();
        }

        for (Entity asteroid : world.getEntities(Asteroid.class)) {

            if (asteroid.getHealth() <= 0 && asteroid.getSize() > 30) {
                asteroidPlugin.splitAsteroid(asteroid, world);
                asteroid.setValid(false);
            } else if (asteroid.getHealth() <= 0) {
                asteroid.setValid(false);
            }

            move(asteroid);

            if (asteroid.getX() < 0) {
                asteroid.setX(1);
                asteroid.setRotation(asteroid.getRotation() + random.nextInt(91) + 90);
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(gameData.getDisplayWidth()-1);
                asteroid.setRotation(asteroid.getRotation() + random.nextInt(91) + 90);
            }

            if (asteroid.getY() < 0) {
                asteroid.setY(1);
                asteroid.setRotation(asteroid.getRotation() + random.nextInt(91) + 90);
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(gameData.getDisplayHeight()-1);
                asteroid.setRotation(asteroid.getRotation() + random.nextInt(91) + 90);
            }
        }
    }

    private void move(Entity asteroid) {
        double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
        double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));
        asteroid.setX(asteroid.getX() + changeX);
        asteroid.setY(asteroid.getY() + changeY);
    }
}