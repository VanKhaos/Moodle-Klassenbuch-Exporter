package de.thunderfrog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 400, 170);
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(App.class.getResource("/images/education.png"))));
        stage.setTitle("BFZ - Moodle Klassenbuch Exporter");
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