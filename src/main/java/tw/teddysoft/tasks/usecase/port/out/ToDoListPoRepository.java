package tw.teddysoft.tasks.usecase.port.out;

import java.util.Optional;

public interface ToDoListPoRepository {
  Optional<ToDoListPo> findById(String id);

  void save(ToDoListPo toDoListPo);

  void delete(ToDoListPo toDoListPo);
}
