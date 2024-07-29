package tw.teddysoft.tasks.io.spring.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration("DataSourceConfiguration")
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"tw.teddysoft.tasks"},
    entityManagerFactoryRef = "toDoListEntityManagerFactory",
    transactionManagerRef = "toDoListTransactionManager")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class ToDoListDataSourceConfiguration {
  public static final String[] ENTITY_PACKAGES = {"tw.teddysoft.tasks"};

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.todolist")
  public DataSourceProperties toDoListDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.todolist.configuration")
  public DataSource toDoListDataSource() {
    return toDoListDataSourceProperties()
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean(name = "toDoListEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean toDoListTransactionManager(
      EntityManagerFactoryBuilder builder) {
    return builder.dataSource(toDoListDataSource()).packages(ENTITY_PACKAGES).build();
  }

  @Bean
  public PlatformTransactionManager toDoListTransactionManager(
      final @Qualifier("toDoListEntityManagerFactory") LocalContainerEntityManagerFactoryBean
              toDoListTransactionManager) {
    return new JpaTransactionManager(toDoListTransactionManager.getObject());
  }
}
