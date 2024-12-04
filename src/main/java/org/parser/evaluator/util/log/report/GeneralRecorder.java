package org.parser.evaluator.util.log.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeneralRecorder {

    private final File file;

    public GeneralRecorder(){
        String folderPath = "test res";

        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new RuntimeException("Folder " + folderPath + " does not exist");
        }
        String fileName = "test_results_"+getCurrentTimeString("yyyy-MM-dd_HH-mm-ss")+".csv";
        file = new File(folder, fileName);
    }


    //save results to a CSV file
    public void record(LogContext logContext) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(logContext.toString(false, ";") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentTimeString(String format){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return currentDateTime.format(formatter);
    }

}
