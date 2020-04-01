package seedu.planner.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.planner.model.Planner;
import seedu.planner.model.module.Module;
import seedu.planner.model.module.ModuleCode;
import seedu.planner.model.student.Enrollment;
import seedu.planner.model.student.Major;
import seedu.planner.model.student.Student;
import seedu.planner.model.student.TimeTable;
import seedu.planner.model.student.TimeTableMap;
import seedu.planner.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Planner} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS2040")),
            new Module(new ModuleCode("CS2103T"))
        };
    }

    public static Planner getSamplePlanner() {
        Planner samplePlanner = new Planner();
        Student student = SampleDataUtil.getSampleStudent();
        samplePlanner.addStudent(student);
        samplePlanner.activateStudent(student); // TODO: allow serialization of planner with no active student
        return samplePlanner;
    }

    public static Student getSampleStudent() {
        Student sampleStudent = new Student(new seedu.planner.model.student.Name("Mark"), new Major("CS"),
            SampleDataUtil.getSampleTimeTableMap());
        return sampleStudent;
    }

    public static TimeTableMap getSampleTimeTableMap() {
        TimeTableMap timeTableMap = new TimeTableMap();
        return timeTableMap;
    }

    /**
     * Returns a non-empty (@code TimeTableMap) which (@code Student) can immediately use.
     *
     * @return Non-empty (@code TimeTableMap)
     */
    public static TimeTable getSampleTimeTable() {
        TimeTable timeTable = new TimeTable();
        timeTable.addEnrollment(new Enrollment(new ModuleCode("CS2040"), Optional.empty(), 4));
        return timeTable;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}