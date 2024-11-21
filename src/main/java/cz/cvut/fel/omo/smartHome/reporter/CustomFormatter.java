package cz.cvut.fel.omo.smartHome.reporter;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class CustomFormatter extends Formatter {
    /**
     * Formats a log record into a string for logging purposes.
     *
     * This implementation returns the message from the log record as a formatted string.
     *
     * @param record The log record to be formatted.
     * @return A formatted string representing the log record's message.
     */
    @Override
    public String format(LogRecord record) {
        return record.getMessage();
    }
}