package cz.cvut.fel.omo.smartHome.reporter;

import java.io.IOException;
import java.util.logging.FileHandler;

public class FileReporter implements ReporterStrategy {

    /**
     * Configures a Reporter by adding a FileHandler with a custom formatter to log messages to a file.
     *
     * Creates a new FileHandler configured to write log messages to "report.txt" and sets a CustomFormatter.
     *
     * @param reporter The Reporter instance to be configured.
     * @throws RuntimeException If an IOException occurs during file handler setup.
     */
    @Override
    public void configure(Reporter reporter) {
        try {
            FileHandler fileHandler = new FileHandler("report.txt");
            fileHandler.setFormatter(new CustomFormatter());
            reporter.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
