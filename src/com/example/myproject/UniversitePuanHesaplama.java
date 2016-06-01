package com.example.myproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author skorkmaz
 */
public class UniversitePuanHesaplama {

    private static final String SPLIT_STR = " ";
    private static final String NO_SCORE_STR = "---";
    private static final double DOUBLE_TOLERANCE = 1e-15;

    /**
     *
     * @return true if currentVal = [targetVal - toleranceDown, targetVal + toleranceUp]
     */
    public static boolean isEqualDouble(double currentVal, double valUp, double valDown) {
        //TODO: NaN, Inf
        return (currentVal >= valDown - DOUBLE_TOLERANCE) && (currentVal <= valUp + DOUBLE_TOLERANCE);
    }

    public static boolean startsWithNumber(String str) {
        return str.startsWith("0") || str.startsWith("1") || str.startsWith("2") || str.startsWith("3") || str.startsWith("4") || str.startsWith("5")
                || str.startsWith("6") || str.startsWith("7") || str.startsWith("8") || str.startsWith("9");
    }

    public static List<String> getPrograms(List<String> rawData, String targetScoreType, double scoreUp, double scoreDown) throws IOException {
        Set<String> scoreTypes = new HashSet<>(); //HashSet ensures uniqueness

        List<String> closestPrograms = new ArrayList<>();
        for (String line : rawData) {
            String[] dataOnLine = line.split(SPLIT_STR);
            if (startsWithNumber(dataOnLine[0])) { //score lines start with a number ("Program Kodu")
                int n = dataOnLine.length;
                if (n > 5) { //not page number string (e.g. "292 / 292")
                    String scoreStr = dataOnLine[n - 4];
                    double score = 0;
                    String scoreType = "";
                    if (!scoreStr.equals(NO_SCORE_STR)) {
                        String modScoreStr = scoreStr.replace(",", ".");
                        score = Double.parseDouble(modScoreStr);
                        scoreType = dataOnLine[n - 7];
                        scoreTypes.add(scoreType);
                    }
                    String programName = "";
                    for (int i = 0; i < n - 7; i++) {
                        programName = programName + dataOnLine[i];
                        if (i < n - 8) {
                            programName = programName + " ";
                        }
                    }
                    if (scoreType.equals(targetScoreType) && isEqualDouble(score, scoreUp, scoreDown)) {
                        closestPrograms.add(scoreStr + ", " + scoreType + ", " + programName);
                    }
                    //System.out.printf("programName = %s, score = %1.5f\n", programName, score);
                }
            }
        }
        System.out.println("Available score types: " + scoreTypes);
        return closestPrograms;
    }

    public static void main(String[] args) throws IOException {
        /*
         score types: TS-2, TS-1, MF-4, DİL-3, DİL-2, YGS-6, DİL-1, YGS-5, YGS-4, YGS-3, YGS-2, (Açıköğretim), YGS-1, TM-3, MF-1, TM-2, MF-2, TM-1, MF-3
         */

        String targetScoreType = "MF-4";
        double scoreUp = 500;
        double scoreDown = 490;

        List<String> closestPrograms = getPrograms(UniversityScoreData2015.getRaw(), targetScoreType, scoreUp, scoreDown);
        System.out.printf("\nPrograms within [%1.5f, %1.5f] points of target %s:\n", scoreDown, scoreUp, targetScoreType);
        for (String cp : closestPrograms) {
            System.out.println(cp);
        }

    }

}
