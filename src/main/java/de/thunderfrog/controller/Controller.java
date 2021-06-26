package de.thunderfrog.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import de.thunderfrog.MoodleData;
import de.thunderfrog.util.ArrayHelper;
import de.thunderfrog.util.CSVImporter;
import de.thunderfrog.util.PDFCreator;
import de.thunderfrog.util.DateFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Controller {

    @FXML
    public Label txtFileLoad;

    @FXML
    public void openFile(ActionEvent actionEvent) throws IOException {
        //  FileChooser Dialog zum Laden der Datei
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Öffne Moodle Klassenbuch Export Datei");

        //  FileChooser Filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Moodle Klassenbuch Export", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //  FileChooser Anzeigen
        File file = fileChooser.showOpenDialog(null);

        //  Überprüfen ob wir wirklich eine Datei haben und nix anderes
        if(file != null){
            try {
                //  Laden der CSV Daten in die Liste für Moodledaten
                List<MoodleData> list = CSVImporter.getListFromCsv(file);

                //  ArrayList Sortieren - Montag first!
                if (list != null) {
                    ArrayHelper.sortList(list);
                }

                if (list != null) {
                    for(MoodleData element : list){
                        System.out.println(DateFormatter.parseDateTime(element.getDateTime()));
                        System.out.println(element.getSubject());
                        System.out.println(element.getLessonOne());
                        System.out.println(element.getLessonTwo());
                        System.out.println(element.getLessonThree());
                        System.out.println(element.getLessonFour());
                        System.out.println(element.getLessonFive());
                        System.out.println(element.getLessonSix());
                        System.out.println(element.getLessonSeven());
                        System.out.println(element.getLessonEight());
                        System.out.println(element.getLecturer());
                    }
                }

                //  StatusText Updaten
                txtFileLoad.setTextFill(Color.GREEN);
                txtFileLoad.setText("Status: " + list.size() + " Daten geladen!");

                PDFCreator.generate(list);

            } catch (IOException e) {
                txtFileLoad.setTextFill(Color.RED);
                txtFileLoad.setText("Status: Es ist ein Fehler aufgetreten");
                e.printStackTrace();
            } catch (IllegalArgumentException e){
                //  Zeigt ein Fehler an wenn der CSV Header nicht übereinstimmt
                txtFileLoad.setTextFill(Color.RED);
                txtFileLoad.setText("Status: Fehlerhafte CSV Datei!");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
