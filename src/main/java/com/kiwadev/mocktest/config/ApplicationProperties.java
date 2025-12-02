package com.kiwadev.mocktest.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "mocktest.config")
public class ApplicationProperties {
    private String jwtSecretKey;
    private int jwtAccessExpirationMs;
    private int jwtRefreshExpirationMs;
}
