package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.allergen.Allergen;
import seedu.address.model.person.orders.Order;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Allergen> allergens = new HashSet<>();
    private final ArrayList<Order> orders = new ArrayList<>();

    private Points points;
    private MembershipPoints membershipPoints;
    public static final int MAX_POINTS = 2_000_000_000;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, MembershipPoints membershipPoints,
                  Set<Allergen> allergens, Points points, ArrayList<Order> orders) {
        requireAllNonNull(name, phone, email, address, membershipPoints, allergens, points, orders);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.membershipPoints = membershipPoints;
        this.allergens.addAll(allergens);
        this.points = points;
        this.orders.addAll(orders);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public MembershipPoints getMembershipPoints() {
        return membershipPoints;
    }

    /**
     * Returns an immutable allergen set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Allergen> getAllergens() {
        return Collections.unmodifiableSet(allergens);
    }

    public Points getPoints() {
        return points;
    }

    /**
     * Returns a copy of the orders of this Person
     */
    public ArrayList<Order> getOrders() {
        ArrayList<Order> copy = new ArrayList<>();
        for (Order order : orders) {
            copy.add(order.clone());
        }
        return copy;
    }

    /**
     * Adds an Order to the list of orders of this Person
     */
    public void addOrders(Order order) {
        this.orders.add(order);
    }

    /**
     * Adds points to the current points and membership points.
     * If adding the specified points would exceed the maximum points allowed,
     * sets the points to the maximum instead.
     *
     * @param pointsToAdd The number of points to add.
     */
    public void addPoints(Points pointsToAdd) {
        int newPointsValue = Math.min(this.points.getValue() + pointsToAdd.getValue(), MAX_POINTS);
        this.points = new Points(newPointsValue);
    }

    public void addMembershipPoints(MembershipPoints pointsToAdd) {
        int newMembershipPointsValue = Math.min(this.membershipPoints.getValue() + pointsToAdd.getValue(), MAX_POINTS);
        this.membershipPoints = new MembershipPoints(newMembershipPointsValue);
    }


    /**
     * Updates the points of this person and returns a new Person instance with updated points.
     * @param newPoints The new points value to set.
     * @return A new Person instance with updated points.
     */
    public Person setPoints(Points newPoints) {
        return new Person(this.name, this.phone, this.email, this.address, this.membershipPoints,
                this.allergens, newPoints, this.orders);
    }

    /**
     * Updates the membership points of this person and returns a new Person instance with updated membership points.
     * @param newMembershipPoints The new membership points value to set.
     * @return A new Person instance with updated membership points.
     */
    public Person setMembershipPoints(MembershipPoints newMembershipPoints) {
        return new Person(this.name, this.phone, this.email, this.address, newMembershipPoints,
                this.allergens, this.points, this.orders);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && membershipPoints.equals(otherPerson.membershipPoints)
                && allergens.equals(otherPerson.allergens)
                && points.equals(otherPerson.points)
                && orders.equals(otherPerson.orders);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, membershipPoints, allergens, points, orders);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("membership", membershipPoints)
                .add("allergens", allergens)
                .add("points", points)
                .add("orders", orders)
                .toString();
    }

}
