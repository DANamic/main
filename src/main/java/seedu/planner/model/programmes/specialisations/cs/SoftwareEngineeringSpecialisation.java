package seedu.planner.model.programmes.specialisations.cs;

import java.util.ArrayList;

import seedu.planner.model.module.ModuleCode;

/**
 * Class to represent the Software Engineering Specialisation for Computer Science Degree Programme, specifying the
 * primaries and electives which are associated with the specialisation.
 */
public class SoftwareEngineeringSpecialisation extends GenericCsSpecialisation {

    /**
     * Default constructor for {@code SoftwareEngineeringSpecialisation} specifying the {@code ModuleCode} for primaries
     * and electives
     */
    public SoftwareEngineeringSpecialisation() {
        setName("Software Engineering");
        primaries = new ArrayList<>();
        electives = new ArrayList<>();

        primaries.add(new ModuleCode("CS2103T"));
        primaries.add(new ModuleCode("CS3219"));
        primaries.add(new ModuleCode("CS4211"));
        primaries.add(new ModuleCode("CS4218"));
        primaries.add(new ModuleCode("CS4239"));

        electives.add(new ModuleCode("CS3216"));
        electives.add(new ModuleCode("CS3217"));
        electives.add(new ModuleCode("CS3226"));
        electives.add(new ModuleCode("CS3234"));
        electives.add(new ModuleCode("CS5219"));
        electives.add(new ModuleCode("CS5232"));
        electives.add(new ModuleCode("CS5272"));
    }

    /**
     * Returns the Name of the specialisation
     *
     * @return The Name of the specialisation
     */
    public String toString() {
        return this.getName();
    }

}