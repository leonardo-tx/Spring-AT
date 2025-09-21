package br.edu.example.api.infra.user.persistence;

import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.infra.generic.persistence.AddressJpa;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CompoundIndexes({
        @CompoundIndex(name = "email_idx", def = "{'email' : 1}", unique = true)
})
public class UserJpa {
    @Id
    private UUID id;

    @Field(name = "email")
    private String email;

    @Field(name = "encryptedPassword")
    private String encryptedPassword;

    @Field(name = "name")
    private String name;

    @Field(name = "cpf")
    private String cpf;

    @Field(name = "phone")
    private String phone;

    @Field(name = "address")
    private AddressJpa address;

    @Field(name = "role")
    private UserRole role;
}
