package br.edu.example.api.core.course.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class CourseIdentifier {
    private final String value;

    public static CourseIdentifier valueOf(CourseName name) {
        Objects.requireNonNull(name);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.getValue().length(); i++) {
            char c = Character.toLowerCase(name.getValue().charAt(i));
            if (c == ' ') {
                sb.append('-');
                continue;
            }
            sb.append(c);
        }
        return new CourseIdentifier(sb.toString());
    }

    public static CourseIdentifier valueOfUnsafe(String value) {
        return new CourseIdentifier(value);
    }
}
