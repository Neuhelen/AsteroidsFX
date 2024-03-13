import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module CollisionSystem {
    requires Common;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetectionSystem;
}
