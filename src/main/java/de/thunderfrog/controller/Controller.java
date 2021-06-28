package de.thunderfrog.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import de.thunderfrog.MoodleData;
import de.thunderfrog.util.ArrayHelper;
import de.thunderfrog.util.CSVImporter;
import de.thunderfrog.util.PDFCreator;
import de.thunderfrog.util.DateFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Controller {

    @FXML
    public Label txtFileLoad;
    public Label txtSavePDF;
    public DatePicker datePickTo;
    public DatePicker datePickFrom;

    private List<MoodleData> list;

    /**
     * Buttonclick Event um die CSV zu laden
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void openFile(ActionEvent actionEvent) {
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
                list = CSVImporter.getListFromCsv(file);

                //  ArrayList Sortieren - Montag first!
                if (list != null) {
                    ArrayHelper.sortList(list);
                }

                //  StatusText Updaten
                txtFileLoad.setTextFill(Color.GREEN);
                txtFileLoad.setText("Status: " + list.size() + " Daten geladen!");

            //  Fehlerbehandlung
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

    /**
     * Buttonclick Event um die PDF zu erstellen
     * @param actionEvent
     */
    public void savePDF(ActionEvent actionEvent) {
        try {
            //  Funktionsaufruf um die PDF zu erstellen
            PDFCreator.generate(list);

            //  Status Text auf der GUI updaten
            txtSavePDF.setTextFill(Color.GREEN);
            txtSavePDF.setText("Status: PDF erstellt!");

            long epoch = System.currentTimeMillis()/1000;
            LocalDate dateFrom = datePickFrom.getValue();
            LocalDate dateTo = datePickTo.getValue();

            ZonedDateTime epochFrom = dateFrom.atStartOfDay(ZoneId.systemDefault());
            ZonedDateTime epochTo = dateTo.atStartOfDay(ZoneId.systemDefault());

            System.out.println("Datum von " + epochFrom + " bis " + epochTo);

        //  Fehlerbehandlung
        }catch (IOException e){
            txtSavePDF.setTextFill(Color.RED);
            txtSavePDF.setText("Status: Es ist ein Fehler aufgetreten");
            e.printStackTrace();
        }


    }
}
