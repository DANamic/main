package seedu.address.model.student;


import seedu.address.model.programmes.DegreeProgramme;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Degrees degrees;
    private final Major major;

    // Timetables
    public final TimeTableMap timeTableMap;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name) {
        this(name, new Degrees(), null);
    }

    //TODO: add `degrees` field in `JsonAdaptedStudent` and remove this constructor
    public Student(Name name, Major major) {
        requireAllNonNull(name);
        this.name = name;
        this.degrees = null;
        this.major = major;
        this.timeTableMap = new TimeTableMap();
    }

    public Student(Name name, Major major, TimeTableMap timeTableMap) {
        requireAllNonNull(name, major, timeTableMap);
        this.name = name;
        this.degrees = null;
        this.major = major;
        this.timeTableMap = timeTableMap;
    }

    public Student(Name name, Degrees degrees, Major major) {
        requireAllNonNull(name, major);
        this.name = name;
        this.degrees = degrees;
        this.major = major;
        this.timeTableMap = new TimeTableMap();
    }

    public Name getName() {
        return name;
    }

    public Major getMajor() {
        return major;
    }

    public Degrees getDegrees() {
        return degrees;
    }

    public TimeTableMap getTimeTableMap() {
        return timeTableMap;
    }

    public boolean addDegrees(DegreeProgramme degree) {
        this.degrees.addDegree(degree);
        return true;
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

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        //TODO: initialize and compare `degrees`
        return otherStudent.getName().equals(getName())
                && otherStudent.getMajor().equals(getMajor())
                //&& otherStudent.getDegrees().equals(getDegrees())
                && otherStudent.getTimeTableMap().equals(getTimeTableMap());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, major, timeTableMap);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(PREFIX_NAME).append(getName()).append(" ")
                .append(PREFIX_MAJOR).append(getName());
        return builder.toString();
    }

    public boolean isSameStudent(Student student) {
        return this.equals(student);
    }

}
