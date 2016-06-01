package com.example.myproject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author skorkmaz
 */
public class UniversityScoreData2015 {

    private static final List<String> rawData = new ArrayList();
    private static final String TURKISH_ENCODING = "ISO-8859-9";

    public static List<String> getRaw() throws FileNotFoundException, IOException {
        if (rawData.isEmpty()) {
            System.out.println("Reading raw data...");
            InputStream is = UniversityScoreData2015.class.getClassLoader().getResourceAsStream("com/example/myproject/resources/osym2015puanlari.txt");
            InputStreamReader isr = new InputStreamReader(is, TURKISH_ENCODING);            
            try (BufferedReader bufferedReader = new BufferedReader(isr)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    rawData.add(line);
                }
            }
            System.out.println("Finished reading.");
        }
        return rawData;
    }
}
