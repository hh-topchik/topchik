package ru.hh.topchik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.topchik.resource.HelloResource;

@Configuration
@Import(HelloResource.class)
public class JerseyConfig {
}
