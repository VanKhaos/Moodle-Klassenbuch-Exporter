package de.thunderfrog.util;

import java.io.*;
import java.nio.file.FileSystems;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import de.thunderfrog.MoodleData;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.dom.Document;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class PDFCreator {

    /**
     * PDF aus HTML generieren mit dem Inhalt einer Liste von MoodleData
     * @param list
     * @param date
     * @param txtKursName
     * @throws IOException
     */
    public static void generate(List<MoodleData> list, String date, String txtKursName) throws IOException, ParseException {
        //  Template System von Thymeleaf initialisieren
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        //  Context in das Template schreiben
        Context ctx = new Context();
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            if(DateFormatter.parseDateTime(list.get(i).getDateTime()).compareTo(date) >= 0){
                //System.out.println("j = " + j);
                ctx.setVariable("WEEKDAYSTART", date);

                //  Selected Date plus 4 Tage
                String nDate = date;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Calendar c = Calendar.getInstance();
                c.setTime(simpleDateFormat.parse(nDate));
                c.add(Calendar.DATE,4);
                nDate = simpleDateFormat.format(c.getTime());


                ctx.setVariable("WEEKDAYEND", nDate);
                ctx.setVariable("WEEKDAY_" + j, list.get(i).getWeekday());
                ctx.setVariable("STARTDATE_" + j, DateFormatter.parseDateTime(list.get(i).getDateTime()));
                ctx.setVariable("SUBJECT_" + j, list.get(i).getSubject());
                ctx.setVariable("LESSONONE_" + j, list.get(i).getLessonOne());
                ctx.setVariable("LESSONTWO_" + j, list.get(i).getLessonTwo());
                ctx.setVariable("LESSONTHREE_" + j, list.get(i).getLessonThree());
                ctx.setVariable("LESSONFOUR_" + j, list.get(i).getLessonFour());
                ctx.setVariable("LESSONFIVE_" + j, list.get(i).getLessonFive());
                ctx.setVariable("LESSONSIX_" + j, list.get(i).getLessonSix());
                ctx.setVariable("LESSONSEVEN_" + j, list.get(i).getLessonSeven());
                ctx.setVariable("LESSONEIGHT_" + j, list.get(i).getLessonEight());
                ctx.setVariable("LECTURER_" + j, list.get(i).getLecturer());
                j++;
            }else{
                System.out.println("Datum Ungleich!");
            }
        }

        //  Context mit den Platzhaltern im Template zusammenfügen
        String htmlContent = templateEngine.process("template/klassenbuch", ctx);

        //  HTML Template in einer Temp-Datei schreiben
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("temp.html"));
            writer.write(htmlContent);
            writer.close();
            System.out.println("Template created!");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        //  HTML Template aus der Datei in ein PDF Umwandeln
        try {
            //  Aktuelles Datum für die Output Datei
            LocalDate localDate = LocalDate.now();
            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));

            //  Name der Temp & Output Datei
            String inputHTML = "temp.html";
            String outputPdf = txtKursName + " - Moodle Klassenbuch Export " + formattedDate + ".pdf";

            //  HTML Template zu PDF erstellen
            htmlToPdf(inputHTML, outputPdf);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //  HTML Temp Datei wieder löschen
        File tempFile = new File("temp.html");
        if(tempFile.delete()){
            System.out.println("Temp File deleted!");
        }else{
            System.out.println("Failed to delete the File");
        }

    }

    /**
     * HTML Template wird in PDF Umgewandelt und abgespeichert
     * @param inputHTML
     * @param outputPdf
     * @throws IOException
     */
    private static void htmlToPdf(String inputHTML, String outputPdf) throws IOException {
        Document doc = html5ParseDocument(inputHTML);

        String baseUri = FileSystems.getDefault()
                .getPath("src", "main", "resources","template")
                .toUri()
                .toString();

        OutputStream os = new FileOutputStream(outputPdf);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withUri(outputPdf);
        builder.toStream(os);

        builder.withW3cDocument(doc, baseUri);
        //builder.useUriResolver(new MyResolver());
        builder.run();
        System.out.println("PDF generation completed");
        os.close();
    }

    /**
     * HTML Template mit Jsoup in HTML5 Parsen
     * @param inputHTML
     * @return
     * @throws IOException
     */
    private static Document html5ParseDocument(String inputHTML) throws IOException{
        org.jsoup.nodes.Document doc;
        System.out.println("parsing ...");
        doc = Jsoup.parse(new File(inputHTML), "UTF-8");
        System.out.println("parsing done ...");
        //System.out.println(doc);
        return new W3CDom().fromJsoup(doc);
    }

}
