package ru.hh.topchik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.topchik.resource.AchievementResource;

@Configuration
@Import(AchievementResource.class)
public class JerseyConfig {
}
