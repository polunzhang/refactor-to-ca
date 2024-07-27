package tw.teddysoft.tasks.adapter.repository;

import java.util.Optional;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

public interface ToDoListRepositoryPeer {
  Optional<ToDoListPo> findById(String id);

  void save(ToDoListPo toDoListPo);

  void delete(ToDoListPo toDoListPo);
}
