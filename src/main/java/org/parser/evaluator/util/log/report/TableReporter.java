package org.parser.evaluator.util.log.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.parser.evaluator.util.log.report.GeneralRecorder.getCurrentTimeString;
import static org.parser.evaluator.util.log.report.GeneralRecorder.makeFolder;

public class TableReporter {

    // The file where the report will be saved
    protected File file = null;

    // The folder where the report file will be stored
    protected File folder = null;

    // Constructor initializes the reporter and creates the folder for storing the reports
    public TableReporter() {
        // Set the folder field to the new folder
        folder = makeFolder(System.getProperty("user.dir") + "\\table_res", getCurrentTimeString("yyyy-MM-dd_HH-mm-ss"));
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
