package br.edu.example.api.app.request.teacher.mapper;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import br.edu.example.api.app.request.teacher.dto.TeacherCreateDTO;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.teacher.factory.TeacherFactory;
import br.edu.example.api.core.teacher.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherCreateMapper implements InputMapper<Teacher, TeacherCreateDTO> {
    private final InputMapper<Address, AddressCreateDTO> addressCreateMapper;
    private final TeacherFactory teacherFactory;

    @Override
    public Teacher toModel(TeacherCreateDTO entity) {
        Address address = addressCreateMapper.toModel(entity.getAddress());
        return teacherFactory.create(
                entity.getName(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getPhone(),
                address,
                entity.getRawPassword()
        );
    }
}
