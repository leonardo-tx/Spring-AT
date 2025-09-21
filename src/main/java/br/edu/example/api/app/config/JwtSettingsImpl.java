package br.edu.example.api.app.config;

import br.edu.example.api.core.auth.config.JwtSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.settings")
public class JwtSettingsImpl implements JwtSettings {
    private long expirationInSeconds;
}
