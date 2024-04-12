package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Catalogue;

/**
 * Parses user input.
 */
public class AddressBookParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private final Catalogue catalogue;

    public AddressBookParser(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {
            case SeedDataCommand.COMMAND_WORD:
                return new SeedDataCommand();
            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);
            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);
            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);
            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();
            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);
            case AddMemPointsCommand.COMMAND_WORD:
                return new AddMemPointsCommandParser().parse(arguments);
            case ListCommand.COMMAND_WORD:
                return new ListCommand();
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();
            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();
            case AddPointsCommand.COMMAND_WORD:
                return new AddPointsCommandParser().parse(arguments);
            case AddOrderCommand.COMMAND_WORD:
                return new AddOrderCommandParser(catalogue).parse(arguments);
            case AddItemCommand.COMMAND_WORD:
                return new AddItemCommandParser().parse(arguments);
            case DeleteItemCommand.COMMAND_WORD:
                return new DeleteItemCommandParser().parse(arguments);
            case RedeemPointsCommand.COMMAND_WORD:
                return new RedeemPointsCommandParser().parse(arguments);
            default:
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
