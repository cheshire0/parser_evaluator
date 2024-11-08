package org.parser.evaluator.util;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class Logger {

    private static File file;

    static{
        String folderPath = "test res";

        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new RuntimeException("Folder " + folderPath + " does not exist");
        }
        String fileName = "test_results_"+getCurrentTimeString("yyyy-MM-dd_HH-mm-ss")+".csv";
        file = new File(folder, fileName);
    }

    //save results to a CSV file
    public static void saveResultsToCSV(Test test, IParser parser, Object result) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(test.toString() + "; "
                    + parser.toString() + "; "
                    + result.toString() + "; "
                    + getCurrentTimeString("yyyy-MM-dd HH:mm:ss") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //save results to a CSV file
    public static void saveResultsToCSV(List<Object> results) {
        try (FileWriter writer = new FileWriter(file)) {
            for (Object result : results) {
                writer.write(result.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTimeString(String format){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return currentDateTime.format(formatter);
    }

}
