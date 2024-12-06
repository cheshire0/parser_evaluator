package org.parser.evaluator.util.log.report;

import org.jetbrains.annotations.NotNull;

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
        String folderPath = "test_res";

        File folder = new File(folderPath);

        // Check if the folder exists; if not, create it
        if (!folder.exists()) {
            folder = makeFolder(System.getProperty("user.dir"),"test_res");
        }

        String fileName = "test_results_" + getCurrentTimeString("yyyy-MM-dd_HH-mm-ss") + ".csv";
        file = new File(folder, fileName);
    }

    // Create new folder in the project folder
    public static @NotNull File makeFolder(String parentFolderPath, String newFolderName) {
        File folder;

        // Combine the parent folder path with the new folder name to create the full path
        File newFolder = new File(parentFolderPath, newFolderName);

        // Attempt to create the folder at the specified path
        if (newFolder.mkdirs()) {
            System.out.println("Folder created successfully: " + newFolder.getAbsolutePath());
        } else {
            System.out.println("Failed to create folder.");
        }

        // Set the folder field to the new folder
        folder = new File(newFolder.getAbsolutePath());

        // Check if the folder was successfully created
        if (!folder.exists()) {
            throw new RuntimeException("Folder " + newFolder.getAbsolutePath() + " does not exist");
        }
        return folder;
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
