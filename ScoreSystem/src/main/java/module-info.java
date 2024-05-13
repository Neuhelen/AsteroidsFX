module ScoreSystem {
    requires spring.boot;
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires CommonScore;
    provides dk.sdu.mmmi.cbse.commonscore.IScoreSystem with dk.sdu.mmmi.cbse.scoresystem.ScoreSystem;
}


