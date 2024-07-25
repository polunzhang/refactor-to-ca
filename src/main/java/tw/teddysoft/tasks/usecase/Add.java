package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import java.util.List;
import tw.teddysoft.tasks.TaskList;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.usecase.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.out.ToDoListRepository;

public class Add {


  private final TodoList todoList;
  private final PrintWriter out;
  private final ToDoListRepository repository;

  public Add(TodoList todoList, PrintWriter out, ToDoListRepository repository) {
    this.todoList = todoList;
    this.out = out;
    this.repository = repository;
  }

  public void add(String commandLine) {
    String[] subcommandRest = commandLine.split(" ", 2);
    String subcommand = subcommandRest[0];
    if (subcommand.equals("project")) {

      AddProjectUseCase addProjectUseCase = new AddProjectService(repository);
      AddProjectInput addProjectInput = new AddProjectInput();
      addProjectInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
      addProjectInput.projectName = subcommandRest[1];
      addProjectUseCase.execute(addProjectInput);

    } else if (subcommand.equals("task")) {
      String[] projectTask = subcommandRest[1].split(" ", 2);
      addTask(ProjectName.of(projectTask[0]), projectTask[1]);
    }
  }

  public void addProject(ProjectName name) {
    todoList.addProject(name);
  }

  public void addTask(ProjectName project, String description) {
    List<Task> projectTasks = todoList.getTasks(project);
    if (projectTasks == null) {
      out.printf("Could not find a project with the name \"%s\".", project);
      out.println();
      return;
    }
    todoList.addTask(project, description, false);
  }
}