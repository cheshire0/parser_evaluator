package org.parser.evaluator.util.log.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeneralRecorder {

    // The file where the test results will be recorded
    private final File file;

    // Constructor: Initializes the recorder by creating a file in the "test res" folder
    public GeneralRecorder(){
        // Define the folder path where the test results will be stored
        String folderPath = "test res";

        File folder = new File(folderPath);

        // Check if the folder exists; if not, throw an exception
        if (!folder.exists()) {
            throw new RuntimeException("Folder " + folderPath + " does not exist");
        }

        String fileName = "test_results_" + getCurrentTimeString("yyyy-MM-dd_HH-mm-ss") + ".csv";
        file = new File(folder, fileName);
    }

    // Method to save the results to the CSV file
    public void record(LogContext logContext) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(logContext.toString(false, ";") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static method to get the current date and time as a string in the specified format
    public static String getCurrentTimeString(String format){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return currentDateTime.format(formatter);
    }

}
