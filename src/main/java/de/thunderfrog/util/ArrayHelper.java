package de.thunderfrog.util;

import de.thunderfrog.MoodleData;
import java.util.*;

public class ArrayHelper {

    /**
     * Sortierfunktion für die ArrayListe
     * @param list
     */
    public static void sortList(List<MoodleData> list){
        list.sort(Comparator.comparing(MoodleData::getDateTime));
    }


}
