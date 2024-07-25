package tw.teddysoft.tasks.usecase;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.out.ToDoListRepository;

public class AddProjectService implements AddProjectUseCase {

  private final ToDoListRepository repository;

  public AddProjectService(ToDoListRepository repository) {
    this.repository = repository;
  }

  @Override
  public CqrsOutput execute(AddProjectInput input) throws UseCaseFailureException {
    TodoList todoList = repository.findById(TodoListId.of(input.toDoListId)).get();
    todoList.addProject(ProjectName.of(input.projectName));
    repository.save(todoList);

    return CqrsOutput.create().succeed().setId(input.toDoListId);
  }
}
