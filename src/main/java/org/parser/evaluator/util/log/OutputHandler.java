package org.parser.evaluator.util.log;

import org.parser.evaluator.util.log.report.GeneralRecorder;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.log.report.TableReporter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class OutputHandler {

    private static Map<String, Boolean> config = new HashMap<>(Map.of(
            "CONSOLE", true,
            "GENERAL CSV", false,
            "TABLE", false
    ));

    private static final Map<String, Consumer<LogContext>> outputStrategies = Map.of(
            "CONSOLE", OutputHandler::logToConsole,
            "GENERAL CSV", OutputHandler::logToCSV,
            "TABLE", OutputHandler::logToTable
    );

    private static TableReporter tableReport = null;
    private static GeneralRecorder generalRecorder = null;

    public static void setConfig(boolean console, boolean csv, boolean table) {
        config.put("CONSOLE", console);
        config.put("GENERAL CSV", csv);
        config.put("TABLE", table);

        if(table) tableReport = new TableReporter();
        if(csv) generalRecorder = new GeneralRecorder();
    }

    public static void log(LogContext logContext) {
        config.forEach((key, isEnabled) -> {
            if (isEnabled) {
                Consumer<LogContext> strategy = outputStrategies.get(key);
                if (strategy != null) {
                    strategy.accept(logContext);
                }
            }
        });
    }

    public static void log(String message) {
        if(config.get("CONSOLE")) {
            System.out.println();
            System.out.println(message);
        }
    }

    private static void logToConsole(LogContext logContext) {
        System.out.println();
        System.out.println(logContext.toString(true, ", \n"));
    }

    private static void logToCSV(LogContext logContext) {
        generalRecorder.record(logContext);
    }

    private static void logToTable(LogContext logContext) {
        tableReport.generate(logContext);
    }
}
