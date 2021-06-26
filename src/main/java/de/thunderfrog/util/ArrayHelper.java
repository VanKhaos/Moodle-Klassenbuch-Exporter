package de.thunderfrog.util;

import de.thunderfrog.MoodleData;

import java.util.*;

public class ArrayHelper {

    public static void sortList(List<MoodleData> list){
        list.sort(Comparator.comparing(MoodleData::getDateTime));
    }


}
