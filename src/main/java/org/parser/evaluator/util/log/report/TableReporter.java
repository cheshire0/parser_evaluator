package org.parser.evaluator.util.log.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.parser.evaluator.util.log.report.GeneralRecorder.getCurrentTimeString;

public class TableReporter {

    // The file where the report will be saved
    protected File file = null;

    // The folder where the report file will be stored
    protected File folder = null;

    // Parent folder path where the results will be saved
    protected final String parentFolderPath = "C:\\Users\\sebok\\IdeaProjects\\parser_evaluator\\table res";

    // Constructor initializes the reporter and creates the folder for storing the reports
    public TableReporter(){
        // Generate a unique folder name based on the current timestamp
        String newFolderName = getCurrentTimeString("yyyy-MM-dd_HH-mm-ss");

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

        setFile("default");
    }

    // Set the output file for saving the test results
    protected void setFile(String testName) {
        // Generate the file name by combining the class name with the test name
        String fileName = this + "_" + testName + ".csv";
        file = new File(folder, fileName);
    }

    // Method to generate a report and write the log context to the CSV file
    public void generate(LogContext context) {
        // Set the file name based on the test name in the LogContext
        setFile(context.test().toString());

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(context.toString(false, ";") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Override the toString method to return the name of the report type
    @Override
    public String toString() {
        return "table_report";
    }
}
