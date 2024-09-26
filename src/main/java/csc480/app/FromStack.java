package csc480.app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author blj0011
 */

public class FromStack extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch();

    }

        @Override
        public void start(Stage primaryStage) {
            VBox root = new VBox();

            StackPane stackpane = new StackPane();

            Label label = new Label("Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world");
            VBox.setVgrow(label, Priority.ALWAYS);
            label.wrapTextProperty().set(true);

            label.setOnMouseClicked(new MouseEventEventHandler(label, stackpane, root));
            root.setOnMouseClicked(new MouseEventEventHandler(label, stackpane, root));

            stackpane.getChildren().add(label);

            root.getChildren().add(stackpane);

            Scene scene = new Scene(root, 300, 250);

            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();


        }


    private static class MouseEventEventHandler implements EventHandler<MouseEvent> {
        private final Label label;
        private final StackPane stackpane;
        private final VBox root;

        public MouseEventEventHandler(Label label, StackPane stackpane, VBox root) {
            this.label = label;
            this.stackpane = stackpane;
            this.root = root;
        }
        public MouseEventEventHandler(StackPane stackpane, VBox root) {
            this.label = null;
            this.stackpane = stackpane;
            this.root = root;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    if (label !=null && mouseEvent.getSource() == label && label.isVisible()) {
                        label.setVisible(false);
                        TextArea textarea = new TextArea(label.getText());
                        textarea.setPrefHeight(label.getHeight() + 5);
                        stackpane.getChildren().add(textarea);
//                        System.out.println("double clicked " + mouseEvent.getSource().toString());
                        mouseEvent.consume();
                    }
                } else if (mouseEvent.getClickCount() >= 1 ){
                    if(label !=null && mouseEvent.getSource() == root && !label.isVisible()){
                        label.setVisible(true);
                        stackpane.getChildren().removeIf(node -> node instanceof TextArea);
//                        System.out.println("double clicked " + mouseEvent.getSource().toString());
                        mouseEvent.consume();
                    }
                }
            }
        }
    }
}