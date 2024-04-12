package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.item.exceptions.DuplicateItemException;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * A item is considered unique by comparing using {@code Item#isSameItem(Item)}. As such, adding and updating of
 * items uses Item#isSameItem(Item) for equality to ensure that the item being added or updated is
 * unique in terms of identity in the Catalogue.
 *
 * Supports a minimal set of list operations.
 *
 * @see Item#isSameItem(Item)
 */
public class Catalogue implements Iterable<Item> {

    private final ObservableList<Item> internalList = FXCollections.observableArrayList();
    private final ObservableList<Item> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public Catalogue() {}

    public ObservableList<Item> getItemList() {
        return internalList;
    }

    /**
     * Returns false if the list contains an item with the same name.
     */
    public boolean addItem(Item newItem) {
        for (Item item: internalList) {
            if (item.isSameItem(newItem)) {
                return false;
            }
        }
        internalList.add(newItem);
        return true;
    }

    /**
     * Returns if this catalogue contains an item matching the name of the given string
     */
    public boolean hasItem(String name) {
        for (Item item: internalList) {
            String itemName = item.getName().toLowerCase();
            if (itemName.equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a clone of the item in this catalogue that has a name matching the given String
     */
    public Item findItem(String name) {
        requireNonNull(name);
        name = name.toLowerCase();
        for (Item item: internalList) {
            String itemName = item.getName();
            if (itemName.equalsIgnoreCase(name)) {
                return item.clone();
            }
        }
        return null;
    }

    /**
     * Removes the item in this {@code Catalogue} with name matching the given String
     *
     * @return {@code Item} removed from the catalogue, or {@code null} if no items were removed
     */
    public Item removeItem(String name) {
        for (Item item: internalList) {
            if (item.getName().equalsIgnoreCase(name)) {
                internalList.remove(item);
                return item;
            }
        }
        return null;
    }

    public void setItems(List<Item> items) {
        if (!itemsAreUnique(items)) {
            throw new DuplicateItemException();
        }
        internalList.setAll(items);
    }

    public void setItems(Catalogue replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    @Override
    public Iterator<Item> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns an unmodifiable view of the item list.
     * This list will not contain any duplicate items.
     *
     * @return an unmodifiable view of the items list.
     */
    public ObservableList<Item> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if the list contains only unique items.
     */
    public boolean itemsAreUnique(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).getName().equals(items.get(j).getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Catalogue // instanceof handles nulls
                && internalList.equals(((Catalogue) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
