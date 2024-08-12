package csc480;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController {

    @FXML
    ListView<Scout> scoutList;
    @FXML
    private StackPane mainScreen;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button button;

    @FXML
    void initialize() {
    }

    @FXML
    void newScoutBtn(ActionEvent event) throws IOException {

    }

    public void addScout(Scout newScout) {
        scoutList.getItems().add(newScout);
    }

    public void setVista(Node node) {
        mainScreen.getChildren().setAll(node);
    }

}
