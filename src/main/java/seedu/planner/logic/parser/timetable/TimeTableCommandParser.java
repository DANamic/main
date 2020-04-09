package seedu.planner.logic.parser.timetable;

import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_SUBCOMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.logic.commands.timetable.TimeTableActiveCommand;
import seedu.planner.logic.commands.timetable.TimeTableAddCommand;
import seedu.planner.logic.commands.timetable.TimeTableCommand;
import seedu.planner.logic.commands.timetable.TimeTableListCommand;
import seedu.planner.logic.commands.timetable.TimeTableRemoveCommand;
import seedu.planner.logic.parser.Parser;
import seedu.planner.logic.parser.exceptions.ParseException;

//@@author thetruevincentchow

/**
 * Parses sub-commands of the "timetable" command and creates a new TimeTableCommand object
 */
public class TimeTableCommandParser implements Parser<TimeTableCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+) ?(?<arguments>.*)");

    /**
     * Parses user subcommand input into command for execution.
     *
     * @param userInput user subcommand input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TimeTableCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_SUBCOMMAND, TimeTableCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case TimeTableRemoveCommand.COMMAND_WORD:
            return new TimeTableRemoveCommandParser().parse(arguments);

        case TimeTableAddCommand.COMMAND_WORD:
            return new TimeTableAddCommandParser().parse(arguments);

        case TimeTableActiveCommand.COMMAND_WORD:
            return new TimeTableActiveCommandParser().parse(arguments);

        case TimeTableListCommand.COMMAND_WORD:
            return new TimeTableListCommand();

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_SUBCOMMAND, TimeTableCommand.MESSAGE_USAGE));
        }
    }
}
//@@author
