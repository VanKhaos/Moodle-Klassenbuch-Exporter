package de.thunderfrog.util;

import de.thunderfrog.MoodleData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CSVImporter {
    /**
     * @param filename
     * @return
     * @throws IOException
     */
    public static List<MoodleData> getListFromCsv(File filename) throws IOException {
        System.out.println("Datei wird gesucht & geladen");
        try {
            Reader in = new FileReader(filename);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(in);
            ArrayList<MoodleData> list = new ArrayList<>();

            for (CSVRecord record : records) {

                MoodleData moodleData = new MoodleData();
                moodleData.setDateTime(record.get("Datum der Stunde"));
                moodleData.setWeekday(record.get("Wochentag"));
                moodleData.setSubject(record.get("Fach"));
                moodleData.setLessonOne(record.get("Stunde_1"));
                moodleData.setLessonTwo(record.get("Stunde_2"));
                moodleData.setLessonThree(record.get("Stunde_3"));
                moodleData.setLessonFour(record.get("Stunde_4"));
                moodleData.setLessonFive(record.get("Stunde_5"));
                moodleData.setLessonSix(record.get("Stunde_6"));
                moodleData.setLessonSeven(record.get("Stunde_7"));
                moodleData.setLessonEight(record.get("Stunde_8"));
                moodleData.setLecturer(record.get("Dozent"));

                list.add(moodleData);


            }
            System.out.println("Datei geladen " + list.size());
            return list;
        } catch (FileNotFoundException ex) {

            System.out.println("Datei nicht gefunden!");
        }
        return null;
    }
}
