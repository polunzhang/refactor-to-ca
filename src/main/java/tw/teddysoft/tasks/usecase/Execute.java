package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.TaskList;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.usecase.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.out.ToDoListRepository;

public class Execute {

  private final TodoList todoList;
  private final PrintWriter out;

  private final ToDoListRepository repository;

  public Execute(TodoList todoList, PrintWriter out, ToDoListRepository repository) {
    this.todoList = todoList;
    this.out = out;
    this.repository = repository;
  }

  public void execute(String commandLine) {
    String[] commandRest = commandLine.split(" ", 2);
    String command = commandRest[0];
    switch (command) {
      case "show":
        new Show(todoList, out).show();
        break;
      case "add":
        add(commandRest[1]);
        break;
      case "check":
        check(commandRest[1]);
        break;
      case "uncheck":
        uncheck(commandRest[1]);
        break;
      case "help":
        new Help(out).help();
        break;
      default:
        new Error(out).error(command);
        break;
    }
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
      AddTaskUseCase addTaskUseCase = new AddTaskService(todoList, out, repository);
      AddTaskInput addTaskInput = new AddTaskInput();
      addTaskInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
      addTaskInput.projectName = projectTask[0];
      addTaskInput.description = projectTask[1];
      addTaskInput.done = false;
      out.print(addTaskUseCase.execute(addTaskInput).getMessage());

    }
  }


  private void check(String idString) {
    new SetDone(todoList, out).setDone(idString, true);
  }

  private void uncheck(String idString) {
    new SetDone(todoList, out).setDone(idString, false);
  }

}
