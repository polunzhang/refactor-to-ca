package tw.teddysoft.tasks.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

public interface ToDoListCrudRepositoryPeer extends CrudRepository<ToDoListPo, String> {}
