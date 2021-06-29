module de.thunderfrog {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    requires thymeleaf;
    requires java.xml;
    requires openhtmltopdf.pdfbox;
    requires org.jsoup;


    opens de.thunderfrog to javafx.fxml;
    exports de.thunderfrog;
    exports de.thunderfrog.controller;
}