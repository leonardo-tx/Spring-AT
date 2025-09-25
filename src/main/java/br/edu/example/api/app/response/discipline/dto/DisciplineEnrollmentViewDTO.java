package br.edu.example.api.app.response.discipline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplineEnrollmentViewDTO {
    private String code;
    private UUID studentId;
    private Double grade;
}
