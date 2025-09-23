package br.edu.example.api.app.request.student.mapper;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import br.edu.example.api.app.request.student.dto.StudentCreateDTO;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.student.factory.StudentFactory;
import br.edu.example.api.core.student.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentCreateMapper implements InputMapper<Student, StudentCreateDTO> {
    private final InputMapper<Address, AddressCreateDTO> addressCreateMapper;
    private final StudentFactory studentFactory;

    @Override
    public Student toModel(StudentCreateDTO entity) {
        Address address = addressCreateMapper.toModel(entity.getAddress());
        return studentFactory.create(
                entity.getName(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getPhone(),
                address,
                entity.getRawPassword()
        );
    }
}
