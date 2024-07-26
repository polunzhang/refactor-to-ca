package tw.teddysoft.tasks.usecase.service;

import java.io.PrintWriter;
import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class AddTaskService implements AddTaskUseCase {


  private final ToDoListRepository repository;

  public AddTaskService(ToDoListRepository repository) {
    this.repository = repository;
  }

  @Override
  public CqrsOutput execute(AddTaskInput input) throws UseCaseFailureException {

    TodoList todoList = repository.findById(TodoListId.of(input.toDoListId)).get();

    if (todoList.getProject(ProjectName.of(input.projectName)).isEmpty()) {
      StringBuffer sb = new StringBuffer();
      sb.append(String.format("Could not find a project with the name \"%s\".", input.projectName));
      sb.append("\r\n");
      return CqrsOutput.create().fail().setMessage(sb.toString());
    }
    todoList.addTask(ProjectName.of(input.projectName), input.description, false);

    repository.save(todoList);
    return CqrsOutput.create().succeed().setId(todoList.getId().value());
  }
}