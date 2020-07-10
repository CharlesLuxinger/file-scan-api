package com.github.charlesluxinger.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "File Scan Api",
                version = "v1.0.0",
                description = "A test project with the objective of checking some Github repository and returning the number of files and the number of lines per group of files",
                contact = @Contact(name = "Charles Luxinger", email = "charlesluxinger@gmail.com")
        ),
        servers = @Server(url = "http://localhost:9009/api/v1", description = "Dev Server")
)
public class OpenAPIConfig {}