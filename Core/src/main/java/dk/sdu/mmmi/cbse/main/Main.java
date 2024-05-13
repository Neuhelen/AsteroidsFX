package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {


    public static void main(String[] args) {
        launch(dk.sdu.mmmi.cbse.main.Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        Game game = context.getBean(Game.class);
        game.start(window);
        game.render();
    }
}