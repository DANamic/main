package seedu.planner.model.student;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;
import java.util.OptionalDouble;

import seedu.planner.model.grades.Grade;
import seedu.planner.model.module.ModuleCode;

/**
 * Class to repesent the Enrollment of a {@code Student} into a {@code Module} by use of the {@code ModuleCode}.
 */
public class Enrollment {
    private ModuleCode code;
    private Optional<Grade> grade;
    private int credit;

    public Enrollment(ModuleCode code, Optional<Grade> grade, int credit) {
        requireAllNonNull(code, grade, credit);
        this.code = code;
        this.grade = grade;
        this.credit = credit;
    }

    public ModuleCode getCode() {
        return code;
    }

    public void setCode(ModuleCode code) {
        this.code = code;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public ModuleCode getModuleCode() {
        return code;
    }

    public Optional<Grade> getGrade() {
        return grade;
    }

    public void setGrade(Optional<Grade> grade) {
        this.grade = grade;
    }

    public OptionalDouble getGradePoint() {
        if (grade.isPresent()) {
            return grade.get().getGradePoint();
        } else {
            return OptionalDouble.empty();
        }
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Enrollment)) {
            return false;
        } else {
            Enrollment otherEnrollment = ((Enrollment) other);
            return code.equals(otherEnrollment.code)
                    && grade.equals(otherEnrollment.grade)
                    && credit == otherEnrollment.credit;
        }
    }
}
