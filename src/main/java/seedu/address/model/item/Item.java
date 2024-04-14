package seedu.address.model.item;

/**
 * Represents an Item in the catalogue.
 */
public class Item implements Cloneable {

    public static final String MESSAGE_CONSTRAINTS =
            "Item names should contain alphabets and spaces, and cannot be blank.";
    public static final String ITEM_NAME_VALIDATION_REGEX = "^[a-zA-Z][a-zA-Z\\s]*$";

    private String name;
    private int points;

    /**
     * Creates a new item with the given name and points.
     */
    public Item(String newName, int newPoints) {
        name = newName;
        points = newPoints;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public Item clone() {
        return new Item(name, points);
    }

    public boolean isSameItem(Item otherItem) {
        return otherItem != null && otherItem.getName().equals(name);
    }
    @Override
    public String toString() {
        return name + " (" + points + " points)";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Item // instanceof handles nulls
                && name.equals(((Item) other).name)
                && points == ((Item) other).points); // state check
    }

    /**
     * Returns true if a given string is a valid item name.
     */
    public static boolean isValidItemName(String test) {
        return test.matches(ITEM_NAME_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        //combine both names and points, then hash
        String hashString = String.format("%s%d", name, points);
        return hashString.hashCode();
    }
}
