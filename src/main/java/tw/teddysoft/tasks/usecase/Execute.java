package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import tw.teddysoft.tasks.entity.TodoList;
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
        new Add(todoList, out, repository).add(commandRest[1]);
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

  private void check(String idString) {
    new SetDone(todoList, out).setDone(idString, true);
  }

  private void uncheck(String idString) {
    new SetDone(todoList, out).setDone(idString, false);
  }

}
