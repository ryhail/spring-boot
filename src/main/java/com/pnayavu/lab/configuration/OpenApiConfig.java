package com.pnayavu.lab.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Anime API",
                description = "Getting information about anime titles",
                version = "alpha",
                contact = @Contact(
                        name = "Egor Zenkovich",
                        email = "egor.zenkovich2005@gmail.com"
                )
        )
)
public class OpenApiConfig {

}
