package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QTY;

import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Catalogue;
import seedu.address.model.item.Item;
import seedu.address.model.person.Name;

import java.util.Optional;

/**
 * Parses input arguments and creates a new AddOrderCommand object
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {
    private Catalogue catalogue;

    public AddOrderCommandParser(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ITEM, PREFIX_QTY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ITEM) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElse(""));

        String itemName = argMultimap.getValue(PREFIX_ITEM).orElse("").trim();
        Item item = this.catalogue.findItem(itemName);
        if (item == null) {
            throw new ParseException("Item not found in the catalog.");
        }

        int quantity = argMultimap.getValue(PREFIX_QTY).isPresent() ? Integer.parseInt(argMultimap.getValue(PREFIX_QTY).get()) : 1;
        if (quantity <= 0) {
            throw new ParseException("Quantity must be a positive integer.");
        }

        return new AddOrderCommand(name, item, quantity);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            Optional<String> value = argumentMultimap.getValue(prefix);
            if (!value.isPresent() || value.get().isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
