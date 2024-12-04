package org.parser.evaluator.util.log.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.parser.evaluator.util.log.report.GeneralRecorder.getCurrentTimeString;

public class TableReporter {
    protected File file = null;
    protected File folder = null;
    protected final String parentFolderPath = "C:\\Users\\sebok\\IdeaProjects\\parser_evaluator\\table res";

    public TableReporter(){
        String newFolderName = getCurrentTimeString("yyyy-MM-dd_HH-mm-ss");

        // Combine the parent folder and new folder name
        File newFolder = new File(parentFolderPath, newFolderName);

        // Create the folder
        if (newFolder.mkdirs()) {
            System.out.println("Folder created successfully: " + newFolder.getAbsolutePath());
        } else {
            System.out.println("Failed to create folder.");
        }

        folder = new File(newFolder.getAbsolutePath());
        if (!folder.exists()) {
            throw new RuntimeException("Folder " + newFolder.getAbsolutePath() + " does not exist");
        }
        setFile("default");
    }

    protected void setFile(String testName) {
        String fileName = this+"_"+testName+".csv";
        file = new File(folder, fileName);
    }

    public void generate(LogContext context) {
        setFile(context.getTest().toString());
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(context.toString(false,";") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "table_report";
    }
}
