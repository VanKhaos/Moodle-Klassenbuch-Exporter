package de.thunderfrog.util;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.Date;
import java.util.List;

import de.thunderfrog.MoodleData;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.dom.Document;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;


public class PDFCreator {

    public static void generate(List<MoodleData> list) throws IOException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        //  Context in das Template schreiben
        Context ctx = new Context();

        String weekday = null;
        String startdate = null;

        for (int i = 0; i < list.size(); i++) {
            ctx.setVariable("WEEKDAY_" + i, list.get(i).getWeekday());
            ctx.setVariable("STARTDATE_" + i, DateFormatter.parseDateTime(list.get(i).getDateTime()));
        }

//        for(MoodleData element : list){
//
//            ctx.setVariable("WEEKDAY_1", weekday);
//            ctx.setVariable("STARTDATE_1", startdate);
//
//            weekday = element.getWeekday();
//            startdate = DateFormatter.parseDateTime(element.getDateTime());
//        }

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
            String inputHTML = "temp.html";
            String outputPdf = "Output.pdf";
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

    private static Document html5ParseDocument(String inputHTML) throws IOException{
        org.jsoup.nodes.Document doc;
        System.out.println("parsing ...");
        doc = Jsoup.parse(new File(inputHTML), "UTF-8");
        System.out.println("parsing done ...");
        System.out.println(doc);
        return new W3CDom().fromJsoup(doc);
    }

    private static void htmlToPdf(String inputHTML, String outputPdf) throws IOException {
        Document doc = html5ParseDocument(inputHTML);
        String baseUri = FileSystems.getDefault()
                .getPath("D:/", "#Java/", "Moodle Klassenbuch Exporter/")
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


}
