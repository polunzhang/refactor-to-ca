package tw.teddysoft.tasks.adapter.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListInMemoryRepository implements ToDoListRepository {

  private final List<TodoList> store;

  public ToDoListInMemoryRepository() {
    this.store = new ArrayList<>();
  }

  @Override
  public void save(TodoList toDoList) {
    store.removeIf(x -> x.getId().equals(toDoList.getId()));
    store.add(toDoList);
  }

  @Override
  public void delete(TodoList toDoList) {
    store.removeIf(x -> x.getId().equals(toDoList.getId()));
  }

  @Override
  public Optional<TodoList> findById(TodoListId toDoListId) {
    return store.stream().filter(x -> x.getId().equals(toDoListId)).findFirst();
  }
}
