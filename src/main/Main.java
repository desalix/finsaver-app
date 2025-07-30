package main;

import view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {



    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView(stage);
        stage.setTitle("Finsaver");
        stage.setScene(mainView.createScene());
        stage.show();
    }


    public static void main(String[] args) {
        launch();


    }
}

