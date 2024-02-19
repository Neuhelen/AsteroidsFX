package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private Random random = new Random();
    private long lastShotTime = System.nanoTime();
    private long shotCooldown = 250000L;

    EnemyPlugin enemyPlugin = new EnemyPlugin();
    private long lastEnemySpawn = System.nanoTime();
    private long enemyCooldown = 500000L;


    @Override
    public void process(GameData gameData, World world) {
        if (System.nanoTime() - lastEnemySpawn > enemyCooldown) {
            Enemy enemy = new Enemy();
            enemy.setPolygonCoordinates(-10,-10,20,0,-10,10);
            enemyPlugin.createEnemyShip(gameData);
            world.addEntity(enemy);
            lastEnemySpawn = System.nanoTime(); // Reset the timer
        }


        for (Entity enemy : world.getEntities(Enemy.class)) {

            moveRandomly(enemy);

            if (System.nanoTime() - lastShotTime > shotCooldown) {
                for (BulletSPI bullet : getBulletSPIs()) {
                    world.addEntity(bullet.createBullet(enemy, gameData));
                }
                lastShotTime = System.nanoTime();
            }

            if (enemy.getX() < 0) {
                enemy.setX(1);
                enemy.setRotation(enemy.getRotation() + random.nextInt(91) + 90);
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(gameData.getDisplayWidth()-1);
                enemy.setRotation(enemy.getRotation() + random.nextInt(91) + 90);
            }

            if (enemy.getY() < 0) {
                enemy.setY(1);
                enemy.setRotation(enemy.getRotation() + random.nextInt(91) + 90);
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(gameData.getDisplayHeight()-1);
                enemy.setRotation(enemy.getRotation() + random.nextInt(91) + 90);
            }
        }
    }

    private void moveRandomly(Entity enemy) {
        enemy.setRotation(enemy.getRotation() + random.nextDouble() * 10 - 5);

        double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
        double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
        enemy.setX(enemy.getX() + changeX);
        enemy.setY(enemy.getY() + changeY);
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}