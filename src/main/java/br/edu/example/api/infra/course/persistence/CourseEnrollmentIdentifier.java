package br.edu.example.api.infra.course.persistence;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CourseEnrollmentIdentifier {
    private UUID studentId;
    private String courseId;
}
