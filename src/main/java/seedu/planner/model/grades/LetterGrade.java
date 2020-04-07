package seedu.planner.model.grades;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public enum LetterGrade {
    A_PLUS(5.0, "A+"),
    A(5.0, "A"),
    A_MINUS(4.5, "A-"),
    B_PLUS(4.0, "B+"),
    B(3.5, "B"),
    B_MINUS(3.0, "B-"),
    C_PLUS(2.5, "C+"),
    C(2.0, "C-"),
    D_PLUS(1.5, "D+"),
    D(1.0, "D"),
    F(0.0, "F"),
    CS("CS"),
    CU("CU"),
    W("W"),
    EXE("EXE");

    public static final String MESSAGE_CONSTRAINTS =
        "Letter grades must be one of the following: " + getConcatenatedString();

    public final OptionalDouble points;
    public final boolean isSu;
    private final String inputName;

    LetterGrade(double points, String inputName) {
        this.points = OptionalDouble.of(points);
        this.inputName = inputName;
        this.isSu = false;
    }

    LetterGrade(String inputName) {
        this.points = OptionalDouble.empty();
        this.inputName = inputName;
        this.isSu = true;
    }

    private static String getConcatenatedString() {
        return Arrays.stream(LetterGrade.values()).map(LetterGrade::toString)
            .collect(Collectors.joining(", "));
    }

    public static LetterGrade fromInputName(String letterGrade) {
        requireAllNonNull(letterGrade);
        for (LetterGrade grade : LetterGrade.values()) {
            if (grade.inputName.equals(letterGrade)) {
                return grade;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid input name for LetterGrade: %1$s", letterGrade));
    }

    @Override
    public String toString() {
        return inputName;
    }
}