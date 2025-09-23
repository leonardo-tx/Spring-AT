package br.edu.example.api.infra.course.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courseEnrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEnrollment {
    @Id
    private CourseEnrollmentIdentifier id;
}
