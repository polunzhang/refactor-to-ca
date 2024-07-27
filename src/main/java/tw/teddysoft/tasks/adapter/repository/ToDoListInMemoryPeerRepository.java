package tw.teddysoft.tasks.adapter.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListInMemoryPeerRepository implements ToDoListRepositoryPeer {

  private final List<ToDoListPo> store;

  public ToDoListInMemoryPeerRepository() {
    this.store = new ArrayList<>();
  }

  @Override
  public Optional<ToDoListPo> findById(String id) {
    return store.stream().filter(x -> x.getId().equals(id)).findFirst();
  }

  @Override
  public void save(ToDoListPo toDoList) {
    store.removeIf(x -> x.getId().equals(toDoList.getId()));
    store.add(toDoList);
  }

  @Override
  public void delete(ToDoListPo toDoList) {
    store.removeIf(x -> x.getId().equals(toDoList.getId()));
  }
}
