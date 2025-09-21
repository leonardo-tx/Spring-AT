package br.edu.example.api.core.auth.model;

import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.generic.model.Person;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public final class User implements UserDetails {
    private final Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + person.getRole().name());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return person.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return person.getEmail().getValue();
    }

    public boolean hasPermission(PermissionFlag permissionFlag) {
        return person.hasPermission(permissionFlag);
    }
}
