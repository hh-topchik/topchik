package ru.hh.topchik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.topchik.resource.AppResource;

@Configuration
@Import(AppResource.class)
public class JerseyConfig {
}
