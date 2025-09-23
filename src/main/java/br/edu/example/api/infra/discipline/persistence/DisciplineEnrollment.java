package br.edu.example.api.infra.discipline.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "disciplineEnrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplineEnrollment {
    @Id
    private DisciplineEnrollmentIdentifier id;

    @Field(name = "grade")
    private Double grade;
}
