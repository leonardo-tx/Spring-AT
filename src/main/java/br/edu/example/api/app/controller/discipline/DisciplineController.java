package br.edu.example.api.app.controller.discipline;

import br.edu.example.api.app.request.discipline.dto.DisciplineCreateDTO;
import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.app.response.discipline.dto.DisciplineViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.service.DisciplineService;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discipline")
@RequiredArgsConstructor
public class DisciplineController {
    private final DisciplineService disciplineService;
    private final ContextService contextService;
    private final InputMapper<Discipline, DisciplineCreateDTO> disciplineCreateMapper;
    private final OutputMapper<Discipline, DisciplineViewDTO> disciplineViewMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<DisciplineViewDTO>> create(@RequestBody DisciplineCreateDTO dto) {
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineCreateMapper.toModel(dto);

        Discipline createdDiscipline = disciplineService.create(discipline, currentUser);
        DisciplineViewDTO disciplineViewDTO = disciplineViewMapper.toEntity(createdDiscipline);

        return ApiResponse.success(disciplineViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ApiResponse<DisciplineViewDTO>> update(
            @PathVariable String code,
            @RequestBody DisciplineCreateDTO dto
    ) {
        User currentUser = contextService.getCurrentUser();
        DisciplineCode existingDisciplineCode = DisciplineCode.valueOf(code);
        Discipline discipline = disciplineCreateMapper.toModel(dto);

        Discipline updatedDiscipline = disciplineService.update(existingDisciplineCode, discipline, currentUser);
        DisciplineViewDTO disciplineViewDTO = disciplineViewMapper.toEntity(updatedDiscipline);

        return ApiResponse.success(disciplineViewDTO).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<DisciplineViewDTO>> getByCode(@PathVariable String code) {
        DisciplineCode disciplineCode = DisciplineCode.valueOf(code);
        Discipline discipline = disciplineService.getByCode(disciplineCode);

        DisciplineViewDTO disciplineViewDTO = disciplineViewMapper.toEntity(discipline);
        return ApiResponse.success(disciplineViewDTO).createResponse(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DisciplineViewDTO>>> getAll() {
        User currentUser = contextService.getCurrentUser();
        List<DisciplineViewDTO> disciplineViewDTOs = disciplineService.getAll(currentUser)
                .stream()
                .map(disciplineViewMapper::toEntity)
                .toList();
        return ApiResponse.success(disciplineViewDTOs).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String code) {
        DisciplineCode disciplineCode = DisciplineCode.valueOf(code);
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineService.getByCode(disciplineCode);

        disciplineService.delete(discipline, currentUser);
        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
