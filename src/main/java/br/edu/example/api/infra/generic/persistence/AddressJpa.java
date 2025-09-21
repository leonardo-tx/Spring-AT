package br.edu.example.api.infra.generic.persistence;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressJpa {
    @Field(name = "street")
    private String street;

    @Field(name = "number")
    private String number;

    @Field(name = "complement")
    private String complement;

    @Field(name = "neighborhood")
    private String neighborhood;

    @Field(name = "city")
    private String city;

    @Field(name = "state")
    private String state;

    @Field(name = "cep")
    private String cep;
}
