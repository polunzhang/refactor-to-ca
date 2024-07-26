package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.TaskList;
import tw.teddysoft.tasks.adapter.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownInput;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpOutput;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.HelpPresenter;
import tw.teddysoft.tasks.usecase.port.out.ShowPresenter;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.AddProjectService;
import tw.teddysoft.tasks.usecase.service.AddTaskService;
import tw.teddysoft.tasks.usecase.service.SetDoneService;
import tw.teddysoft.tasks.usecase.service.ShowService;

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
        ShowUseCase showUseCase = new ShowService(repository);
        ShowInput showInput = new ShowInput();
        showInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
        ShowOutput output = showUseCase.execute(showInput);
        ShowPresenter presenter = new ShowConsolePresenter(out);
        presenter.present(output.todoListDto);
        break;
      case "add":
        add(commandRest[1]);
        break;
      case "check":
        setDone(commandRest[1], true);
        break;
      case "uncheck":
        setDone(commandRest[1], false);
        break;
      case "help":
        HelpUseCase helpUseCase = new HelpService(new HelpConsolePresenter(out));
        HelpInput input = new HelpInput();
        helpUseCase.execute(input);
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

  public void setDone(String idString, boolean done) {
    SetDownInput input = new SetDownInput();
    input.isDone = done;
    input.taskId = idString;
    input.todoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
    SetDownUseCase setDownUseCase = new SetDoneService(todoList, out, repository);
    CqrsOutput execute = setDownUseCase.execute(input);
    out.printf(execute.getMessage());
  }


}
