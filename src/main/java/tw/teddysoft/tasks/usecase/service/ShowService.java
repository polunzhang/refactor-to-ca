package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.port.TodoListMapper;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class ShowService implements ShowUseCase {

  private final ToDoListRepository repository;

  public ShowService(ToDoListRepository repository) {
    this.repository = repository;
  }


  @Override
  public ShowOutput execute(ShowInput input) throws UseCaseFailureException {
    TodoList todoList = repository.findById(TodoListId.of(input.toDoListId)).get();
    ShowOutput output = new ShowOutput();
    output.todoListDto = TodoListMapper.toDto(todoList);
    return output.succeed().setMessage("");
  }
}