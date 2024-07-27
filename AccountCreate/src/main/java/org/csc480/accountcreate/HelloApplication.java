package org.csc480.accountcreate;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();




    }

    public void addScout() throws IOException {
        Stage accountStage = new Stage();
        FXMLLoader accountFxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AccountCreation.fxml"));
        Scene accountCreationScene = new Scene(accountFxmlLoader.load(), 400, 300);
        accountStage.setTitle("Add Scout");
        accountStage.setScene(accountCreationScene);
        accountStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}