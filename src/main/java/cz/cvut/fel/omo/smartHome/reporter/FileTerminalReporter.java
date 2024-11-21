package cz.cvut.fel.omo.smartHome.reporter;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

public class FileTerminalReporter implements ReporterStrategy {

    /**
     * Configures a Reporter by adding a ConsoleHandler and a FileHandler with a custom formatter for logging.
     *
     * Adds a ConsoleHandler and a FileHandler to the Reporter with a CustomFormatter for formatting log messages.
     * ConsoleHandler logs messages to the console, and FileHandler logs messages to "report.txt."
     *
     * @param reporter The Reporter instance to be configured.
     * @throws RuntimeException If an IOException occurs during file handler setup.
     */
    @Override
    public void configure(Reporter reporter) {
        // Console
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        reporter.addHandler(consoleHandler);

        // File
        try {
            FileHandler fileHandler = new FileHandler("report.txt");
            fileHandler.setFormatter(new CustomFormatter());
            reporter.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
