package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import java.util.List;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TodoList;

public class Add {


  private final TodoList todoList;
  private final PrintWriter out;

  public Add(TodoList todoList, PrintWriter out) {
    this.todoList = todoList;
    this.out = out;
  }

  public void add(String commandLine) {
    String[] subcommandRest = commandLine.split(" ", 2);
    String subcommand = subcommandRest[0];
    if (subcommand.equals("project")) {
      addProject(ProjectName.of(subcommandRest[1]));
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