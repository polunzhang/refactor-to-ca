package tw.teddysoft.tasks.adapter.controller;

import java.io.PrintWriter;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.TodoListApp;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownInput;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.error.ErrorInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ShowPresenter;

public class ToDoListConsoleController {

  private final PrintWriter out;
  private final ShowUseCase showUseCase;
  private final ShowPresenter showPresenter;
  private final AddProjectUseCase addProjectUseCase;
  private final AddTaskUseCase addTaskUseCase;
  private final SetDownUseCase setDoneUseCase;
  private final HelpUseCase helpUseCase;
  private final ErrorUseCase errorUseCase;

  public ToDoListConsoleController(PrintWriter out, ShowUseCase showUseCase,
      ShowPresenter showPresenter, AddProjectUseCase addProjectUseCase,
      AddTaskUseCase addTaskUseCase, SetDownUseCase setDoneUseCase, HelpUseCase helpUseCase,
      ErrorUseCase errorUseCase) {
    this.out = out;
    this.showUseCase = showUseCase;
    this.showPresenter = showPresenter;
    this.addProjectUseCase = addProjectUseCase;
    this.addTaskUseCase = addTaskUseCase;
    this.setDoneUseCase = setDoneUseCase;
    this.helpUseCase = helpUseCase;
    this.errorUseCase = errorUseCase;
  }

  public void execute(String commandLine) {
    String[] commandRest = commandLine.split(" ", 2);
    String command = commandRest[0];
    switch (command) {
      case "show":
        ShowInput showInput = new ShowInput();
        showInput.toDoListId = TodoListApp.DEFAULT_TO_DO_LIST_ID;
        ShowOutput output = showUseCase.execute(showInput);
        showPresenter.present(output.todoListDto);
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
        HelpInput input = new HelpInput();
        helpUseCase.execute(input);
        break;
      default:
        ErrorInput errorInput = new ErrorInput();
        errorInput.command = command;
        errorUseCase.execute(errorInput);
        out.print(errorUseCase.execute(errorInput).getMessage());
        break;
    }
  }

  public void add(String commandLine) {
    String[] subcommandRest = commandLine.split(" ", 2);
    String subcommand = subcommandRest[0];
    if (subcommand.equals("project")) {

      AddProjectInput addProjectInput = new AddProjectInput();
      addProjectInput.toDoListId = TodoListApp.DEFAULT_TO_DO_LIST_ID;
      addProjectInput.projectName = subcommandRest[1];
      addProjectUseCase.execute(addProjectInput);

    } else if (subcommand.equals("task")) {
      String[] projectTask = subcommandRest[1].split(" ", 2);
      AddTaskInput addTaskInput = new AddTaskInput();
      addTaskInput.toDoListId = TodoListApp.DEFAULT_TO_DO_LIST_ID;
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
    input.todoListId = TodoListApp.DEFAULT_TO_DO_LIST_ID;
    CqrsOutput execute = setDoneUseCase.execute(input);
    out.printf(execute.getMessage());
  }


}
