import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

module CollisionSystem {
    requires Common;
    requires Asteroid;
    requires Player;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetectionSystem;
    
}
