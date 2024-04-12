package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.person.MembershipPoints;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Points;
import seedu.address.model.person.orders.Order;

/**
 * Changes the order of an existing person in the address book.
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "addorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an order to the order list of the person identified by the given name.\n"
            + "Parameters: n/MEMBER_NAME i/ITEM_NAME [q/QUANTITY]\n"
            + "NOTE: If quantity is not specified, it will default to 1.\n"
            + "Example: " + COMMAND_WORD + " n/Alex Yeoh i/Chocolate Chip Cookies 100g q/3";

    public static final String MESSAGE_ADD_ORDER_SUCCESS = "Added order to Person: %1$s";

    public static final String MESSAGE_ITEM_NOT_FOUND = "Item not found in the inventory";
    public static final String MESSAGE_MAX_POINTS_REACHED = "Maximum points limit reached.";
    public static final String MESSAGE_MAX_MEMBERSHIP_POINTS_REACHED = "Maximum membership points limit reached.";


    public final Name name;
    private final Item item;
    public final int quantity;

    public final LocalDateTime orderDateTime;

    /**
     * @param name of the person to add the order to
     * @param item ordered
     * @param quantity of specified item ordered
     */
    public AddOrderCommand(Name name, Item item, int quantity) {
        requireAllNonNull(name, item, quantity);

        this.name = name;
        this.item = item;
        this.quantity = quantity;
        this.orderDateTime = null;
    }

    /**
     * @param name of the person to add the order to
     * @param item ordered
     * @param quantity of specified item ordered
     * @param orderDateTime of the order
     */
    public AddOrderCommand(Name name, Item item,
                           int quantity, LocalDateTime orderDateTime) {
        requireAllNonNull(name, item, quantity, orderDateTime);

        this.name = name;
        this.item = item;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Optional<Person> personOptional = lastShownList.stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(name.fullName))
                .findFirst();

        if (personOptional.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        Person personToUpdate = personOptional.get();

        Order newOrder = orderDateTime != null ? new Order(item, quantity, orderDateTime) : new Order(item, quantity);
        personToUpdate.addOrders(newOrder);

        // Add points based on the item's point value and the quantity ordered.
        Points pointsToAdd = new Points(item.getPoints() * quantity);
        personToUpdate.addPoints(pointsToAdd);

        MembershipPoints membershipPointsToAdd = new MembershipPoints(item.getPoints() * quantity);
        personToUpdate.addMembershipPoints(membershipPointsToAdd);

        model.setPerson(personToUpdate, personToUpdate);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String resultMessage = generateResultMessage(personToUpdate, pointsToAdd, membershipPointsToAdd);
        return new CommandResult(resultMessage);
    }


    /**
     * Generates the result message for the command execution.
     */
    private String generateResultMessage(Person person, Points pointsToAdd, MembershipPoints membershipPointsToAdd) {
        boolean maxPointsReached = person.getPoints().getValue() == Person.MAX_POINTS;
        boolean maxMembershipPointsReached = person.getMembershipPoints().getValue() == Person.MAX_POINTS;
        StringBuilder sb = new StringBuilder(String.format(MESSAGE_ADD_ORDER_SUCCESS, person.getName()));

        if (maxPointsReached && maxMembershipPointsReached) {
            sb.append(" ").append(MESSAGE_MAX_POINTS_REACHED).append(" ").append(MESSAGE_MAX_MEMBERSHIP_POINTS_REACHED);
        } else if (maxPointsReached) {
            sb.append(" ").append(MESSAGE_MAX_POINTS_REACHED);
        } else if (maxMembershipPointsReached) {
            sb.append(" ").append(MESSAGE_MAX_MEMBERSHIP_POINTS_REACHED);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOrderCommand)) {
            return false;
        }

        AddOrderCommand e = (AddOrderCommand) other;
        return name.equals(e.name)
                && item.equals(e.item) && (quantity == e.quantity);
    }

    @Override
    public String toString() {
        return "AddOrderCommand{"
                + "personNamePredicate=" + name
                + ", itemName='" + item + '\''
                + ", quantity=" + quantity
                + ", orderDateTime=" + orderDateTime
                + '}';
    }
}
