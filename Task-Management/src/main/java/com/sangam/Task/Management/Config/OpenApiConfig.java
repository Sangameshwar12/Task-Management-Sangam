package com.sangam.Task.Management.Config;

import com.sangam.Task.Management.Constants.TaskStatus; // Ensure this is the correct import path for your TaskStatus enum
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        Contact contact = new Contact()
                .email("support@taskmanagement.com")
                .name("Task Management Support")
                .url("https://www.taskmanagement.com");

        License license = new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0.html");

        return new Info()
                .title("Task Management API")
                .version("1.0")
                .contact(contact)
                .description("API for managing tasks, including creation, updates, and querying of tasks.")
                .termsOfService("http://www.taskmanagement.com/terms")
                .license(license);
    }
}
