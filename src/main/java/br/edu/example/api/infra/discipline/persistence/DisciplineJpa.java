package br.edu.example.api.infra.discipline.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document(collection = "disciplines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplineJpa {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "teacherId")
    private UUID teacherId;

    @Field(name = "courseId")
    private String courseId;
}
