package com.example.tuan3.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Service
public class RolePermissionConfig {
    private final Properties roleMap = new Properties();

    public RolePermissionConfig() throws IOException {
        ClassPathResource resource = new ClassPathResource("role.properties");
        try (InputStream input = resource.getInputStream()) {
            roleMap.load(input);
        }
    }
    public String getRequiredRole(String uri){
        return roleMap.getProperty(uri);
    }
}
