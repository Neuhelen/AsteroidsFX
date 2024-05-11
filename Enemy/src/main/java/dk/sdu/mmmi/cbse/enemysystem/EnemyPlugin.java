package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    private Random random = new Random();

    public EnemyPlugin(){
    }

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    public Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-10,-10,20,0,-10,10);
        enemyShip.setColor("RED");
        enemyShip.setRadius(16);

        int spawnLocation = random.nextInt(4);

        switch (spawnLocation) {
            case 0:
                enemyShip.setX(random.nextDouble(0,1) * gameData.getDisplayWidth());
                enemyShip.setY(gameData.getDisplayHeight());
                break;
            case 1:
                enemyShip.setX(gameData.getDisplayWidth());
                enemyShip.setY(random.nextDouble(0,1) * gameData.getDisplayHeight());
                break;
            case 2:
                enemyShip.setX(random.nextDouble(0,1) * gameData.getDisplayWidth());
                enemyShip.setY(0);
                break;
            case 3:
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
        for (Entity entity : world.getEntities()) {
            if (entity.getClass() == Enemy.class) {
                world.removeEntity(entity);
            }
        }
    }
}