package de.thunderfrog;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Standard JavaFX Start procedure
 */
public class App extends Application {

    private static Scene scene;

    @FXML
    private DatePicker datePickFrom;

    @Override
    public void start(Stage stage) throws IOException {
        //  Laden und Initialisieren der GUI mittels FXML
        scene = new Scene(loadFXML("main"), 240, 340);
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(App.class.getResource("/images/education.png"))));
        stage.setTitle("BFZ - MKE");

        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop(){
        System.exit(0);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}