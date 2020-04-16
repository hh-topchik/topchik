package ru.hh.topchik.config;

import dao.WeeklyResultDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.nab.datasource.DataSourceFactory;
import ru.hh.nab.datasource.DataSourceType;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.hibernate.NabHibernateCommonConfig;
import ru.hh.nab.starter.NabCommonConfig;
import ru.hh.topchik.service.WeeklyResultMapper;
import ru.hh.topchik.service.WeeklyResultService;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@Import({
    NabCommonConfig.class,
    NabHibernateCommonConfig.class,
    WeeklyResultDaoImpl.class,
    WeeklyResultService.class,
    WeeklyResultMapper.class
})
public class CommonConfig {
  @Bean
  public MappingConfig mappingConfig() {
    MappingConfig mappingConfig = new MappingConfig();
    mappingConfig.addPackagesToScan("entity", "enums");
    return mappingConfig;
  }

  @Bean
  DataSource dataSource(DataSourceFactory dataSourceFactory, FileSettings settings) {
    return dataSourceFactory.create(DataSourceType.MASTER, false, settings);
  }
}
