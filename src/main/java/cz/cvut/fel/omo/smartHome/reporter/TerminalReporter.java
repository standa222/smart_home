package cz.cvut.fel.omo.smartHome.reporter;

import java.util.logging.ConsoleHandler;

public class TerminalReporter implements ReporterStrategy {
    /**
     * Configures a Reporter by adding a ConsoleHandler with a custom formatter for logging to the console.
     *
     * Adds a ConsoleHandler to the Reporter with a CustomFormatter for formatting log messages.
     * The ConsoleHandler logs messages to the console.
     *
     * @param reporter The Reporter instance to be configured.
     */
    @Override
    public void configure(Reporter reporter) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        reporter.addHandler(consoleHandler);
    }
}
