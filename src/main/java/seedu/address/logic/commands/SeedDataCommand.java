package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * Seed the address book with sample data.
 */
public class SeedDataCommand extends Command {
    public static final String COMMAND_WORD = "seeddata";
    public static final String MESSAGE_SUCCESS =
            "Added new members and catalogue from seed data into the address book successfully!";
    public static final String MESSAGE_FAILURE =
            "Every member and items in catalogue from seed data already exist in the address book!";

    private static final Person[] samplePersons = SampleDataUtil.getSamplePersons();
    private static final Item[] sampleCatalogue = SampleDataUtil.getSampleCatalogue();

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AddressBook addressBook = new AddressBook(model.getAddressBook());
        boolean hasAdded = false;
        for (Person person : samplePersons) {
            if (!addressBook.hasPerson(person)) {
                model.addPerson(person);
                hasAdded = true;
            }
        }

        for (Item item : sampleCatalogue) {
            if (!addressBook.hasItem(item.getName())) {
                model.addItem(item);
                hasAdded = true;
            }
        }

        if (hasAdded) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
