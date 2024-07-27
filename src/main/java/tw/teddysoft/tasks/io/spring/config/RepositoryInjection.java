package tw.teddysoft.tasks.io.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryPeerRepository;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

@PropertySource(value = "classpath:/application.properties")
@Configuration("ToDoListRepositoryInjection")
public class RepositoryInjection {

  @Bean(name = "toDoListRepository")
  public ToDoListRepository toDoListRepository() {
    return new ToDoListInMemoryRepository(new ToDoListInMemoryPeerRepository());
  }
}
