package main;

import javafx.application.Application;
import javafx.stage.Stage;
import viewer.WelcomePanelController;

import java.io.IOException;

public class Main extends Application {

    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("AirBnB Property Viewer");
        WelcomePanelController controller = new WelcomePanelController();
        controller.createWelcomePanel(primaryStage);
    }

    public static void main(String[] args){
        launch(args);
    }

}

