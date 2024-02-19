package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    public Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-10,-10,20,0,-10,10);

        Random random = new Random();
        int spawnLocation = random.nextInt(4);

        switch (spawnLocation) {
            case 0:
                // Spawn on the top side
                enemyShip.setX(random.nextDouble(0,1) * gameData.getDisplayWidth());
                enemyShip.setY(gameData.getDisplayHeight());
                break;
            case 1:
                // Spawn on the right side
                enemyShip.setX(gameData.getDisplayWidth());
                enemyShip.setY(random.nextDouble(0,1) * gameData.getDisplayHeight());
                break;
            case 2:
                // Spawn on the bottom side
                enemyShip.setX(random.nextDouble(0,1) * gameData.getDisplayWidth());
                enemyShip.setY(0);
                break;
            case 3:
                // Spawn on the left side
                enemyShip.setX(0);
                enemyShip.setY(random.nextDouble(0,1) * gameData.getDisplayHeight());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + random.nextInt(4));
        }

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }
}