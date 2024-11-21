package cz.cvut.fel.omo.smartHome.reporter;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class Reporter {
    private static Reporter instance;
    private final Logger logger;
    private ReporterStrategy reporterStrategy;

    private Reporter() {
        this.logger = Logger.getLogger("");
        setReporterStrategy(new TerminalReporter());
    }

    /**
     * Gets the singleton instance of the Reporter.
     *
     * Creates a new instance of Reporter if it doesn't exist, ensuring thread safety using double-checked locking.
     *
     * @return The singleton instance of the Reporter.
     */
    public static Reporter getInstance() {
        if (instance == null) {
            synchronized (Reporter.class) {
                if (instance == null) {
                    instance = new Reporter();
                }
            }
        }
        return instance;
    }

    /**
     * Logs a message using the info level of the logger.
     *
     * Logs the provided message at the info level using the underlying logger.
     *
     * @param msg The message to be logged.
     */
    public void log(String msg) {
        logger.info(msg);
    }

    protected void addHandler(Handler handler) {
        logger.addHandler(handler);
    }

    /**
     * Sets a new ReporterStrategy and configures the Reporter accordingly.
     *
     * Removes all default handlers from the logger and sets a new ReporterStrategy.
     * Configures the Reporter using the provided ReporterStrategy.
     *
     * @param reporterStrategy The new ReporterStrategy to be set.
     */
    public void setReporterStrategy(ReporterStrategy reporterStrategy) {
        // clear default handlers
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }

        this.reporterStrategy = reporterStrategy;
        this.reporterStrategy.configure(this);
    }
}
