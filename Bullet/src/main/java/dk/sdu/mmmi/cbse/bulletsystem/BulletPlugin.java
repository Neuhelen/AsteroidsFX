package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService, BulletSPI {

    private Entity bullet;

    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        bullet = new Bullet();
        bullet.setHealth(1);
        bullet.setSize(0);

        //This part sets the initial position to the shooter's position.
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());

        //This part sets the initial rotation to the shooter's rotation.
        bullet.setRotation(shooter.getRotation());

        bullet.setVelocity(shooter.getVelocityX() * 1.5, shooter.getVelocityY() * 1.5);

        bullet.setPolygonCoordinates(-2,2,2,2,2,-2,-2,-2);

        bullet.setColor(shooter.getColor());

        bullet.setRadius(1);

        return bullet;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }

}
