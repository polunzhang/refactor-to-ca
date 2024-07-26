package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.in.task.set_down.SetDownInput;
import tw.teddysoft.tasks.usecase.in.task.set_down.SetDownUseCase;
import tw.teddysoft.tasks.usecase.out.ToDoListRepository;

public class SetDoneService implements SetDownUseCase {

  private final TodoList todoList;
  private final PrintWriter out;

  private final ToDoListRepository repository;

  public SetDoneService(TodoList todoList, PrintWriter out, ToDoListRepository repository) {
    this.todoList = todoList;
    this.out = out;
    this.repository = repository;
  }


  @Override
  public CqrsOutput execute(SetDownInput input) throws UseCaseFailureException {
    TodoList todoList = repository.findById(TodoListId.of(input.todoListId)).get();

    if (!todoList.containsTask(TaskId.of(input.taskId))) {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("Could not find a task with an ID of %s.", input.taskId));
      sb.append("\r\n");
      return CqrsOutput.create().fail().setMessage(sb.toString());
    }

    todoList.setDone(TaskId.of(input.taskId),input.isDone);
    repository.save(todoList);

    return CqrsOutput.create().succeed().setId(todoList.getId().value());
  }
}