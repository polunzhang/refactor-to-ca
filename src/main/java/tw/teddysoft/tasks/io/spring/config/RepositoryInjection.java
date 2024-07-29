package tw.teddysoft.tasks.io.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import tw.teddysoft.tasks.adapter.repository.ToDoListCrudRepositoryPeer;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryPeerRepository;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

@PropertySource(value = "classpath:/application.properties")
@Configuration("ToDoListRepositoryInjection")
public class RepositoryInjection {
  private final ToDoListCrudRepositoryPeer toDoListCrudRepositoryPeer;

  @Autowired
  public RepositoryInjection(ToDoListCrudRepositoryPeer toDoListCrudRepositoryPeer) {
    this.toDoListCrudRepositoryPeer = toDoListCrudRepositoryPeer;
  }

//  @Bean(name = "toDoListRepository")
//  public ToDoListRepository toDoListRepository() {
//    return new ToDoListCrudRepository(toDoListCrudRepositoryPeer);
//  }

    @Bean(name = "toDoListRepository")
    public ToDoListRepository toDoListRepository() {
      return new ToDoListInMemoryRepository(new ToDoListInMemoryPeerRepository());
    }
}
