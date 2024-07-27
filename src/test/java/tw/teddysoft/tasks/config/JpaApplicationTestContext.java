package tw.teddysoft.tasks.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import tw.teddysoft.tasks.SpringBootApplicationTest;
import tw.teddysoft.tasks.io.spring.ToDoListSpringBootApp;

@ComponentScan(
    basePackages = {"tw.teddysoft.tasks"},
    excludeFilters = {
      @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ToDoListSpringBootApp.class),
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          value = SpringBootApplicationTest.class)
    })
@EntityScan(basePackages = {"tw.teddysoft.tasks"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public abstract class JpaApplicationTestContext {}
