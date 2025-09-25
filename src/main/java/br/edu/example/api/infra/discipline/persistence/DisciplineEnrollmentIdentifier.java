package br.edu.example.api.infra.discipline.persistence;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class DisciplineEnrollmentIdentifier {
    private UUID studentId;
    private String disciplineCode;
}
