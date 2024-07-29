package tw.teddysoft.tasks.adapter.repository;

import java.util.Optional;
import tw.teddysoft.ezddd.core.usecase.RepositorySaveException;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.port.TodoListMapper;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListCrudRepository implements ToDoListRepository {

  private ToDoListCrudRepositoryPeer peer;

  public ToDoListCrudRepository(ToDoListCrudRepositoryPeer peer) {
    this.peer = peer;
  }

  @Override
  public Optional<TodoList> findById(TodoListId todoListId) {
    return peer.findById(todoListId.value()).map(TodoListMapper::toDomain);
  }

  @Override
  public void save(TodoList data) throws RepositorySaveException {
    peer.save(TodoListMapper.toPo(data));
  }

  @Override
  public void delete(TodoList data) {
    peer.delete(TodoListMapper.toPo(data));
  }
}
