package dk.sdu.mmmi.cbse.scoresystem;

import dk.sdu.mmmi.cbse.commonscore.IScoreSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoreSystem implements IScoreSystem {
    private int score = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoreSystem.class, args);
    }

    @Override
    @GetMapping("/score")
    public int addToScore(@RequestParam(value = "points") int points) {
        score += points;
        return score;
    }
}
