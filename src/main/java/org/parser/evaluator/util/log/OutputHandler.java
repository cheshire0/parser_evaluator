package org.parser.evaluator.util.log;

import org.parser.evaluator.util.log.report.GeneralRecorder;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.log.report.TableReporter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class OutputHandler {

    // Configuration map to control the output destinations (console, CSV, table)
    private static Map<String, Boolean> config = new HashMap<>(Map.of(
            "CONSOLE", true,        // Enable console output by default
            "GENERAL CSV", false,   // Disable CSV output by default
            "TABLE", false          // Disable table output by default
    ));

    // Map of strategies to handle the different output types
    private static final Map<String, Consumer<LogContext>> outputStrategies = Map.of(
            "CONSOLE", OutputHandler::logToConsole,    // Console output strategy
            "GENERAL CSV", OutputHandler::logToCSV,    // CSV output strategy
            "TABLE", OutputHandler::logToTable         // Table output strategy
    );

    // Instances for generating table reports and general CSV reports
    private static TableReporter tableReport = null;
    private static GeneralRecorder generalRecorder = null;

    /**
     * Sets the configuration for different log outputs (console, CSV, table).
     * Initializes the reporters based on the flags.
     * @param console Whether to enable console output
     * @param csv Whether to enable CSV output
     * @param table Whether to enable table output
     */
    public static void setConfig(boolean console, boolean csv, boolean table) {
        // Update the configuration map with the new settings
        config.put("CONSOLE", console);
        config.put("GENERAL CSV", csv);
        config.put("TABLE", table);

        // Initialize the relevant reporters if the corresponding output type is enabled
        if (table) tableReport = new TableReporter();
        if (csv) generalRecorder = new GeneralRecorder();
    }

    /**
     * Logs the provided LogContext based on the enabled configurations.
     * @param logContext The log context containing information about the test results
     */
    public static void log(LogContext logContext) {
        // For each output type, check if it's enabled and call the corresponding logging strategy
        config.forEach((key, isEnabled) -> {
            if (isEnabled) {
                Consumer<LogContext> strategy = outputStrategies.get(key);
                if (strategy != null) {
                    strategy.accept(logContext);  // Apply the logging strategy
                }
            }
        });
    }

    /**
     * Logs a message to the console (if enabled in the config).
     * @param message The message to log
     */
    public static void log(String message) {
        if (config.get("CONSOLE")) {
            System.out.println();  // Adding a blank line for better readability
            System.out.println(message);  // Print the message to the console
        }
    }

    // Private method to log the LogContext to the console
    private static void logToConsole(LogContext logContext) {
        // Print the log context to the console with custom formatting
        System.out.println();
        System.out.println(logContext.toString(true, ", \n"));
    }

    // Private method to log the LogContext to a CSV file
    private static void logToCSV(LogContext logContext) {
        // Record the log context in the general CSV report
        generalRecorder.record(logContext);
    }

    // Private method to log the LogContext to a table report
    private static void logToTable(LogContext logContext) {
        // Generate the table report with the log context
        tableReport.generate(logContext);
    }
}
