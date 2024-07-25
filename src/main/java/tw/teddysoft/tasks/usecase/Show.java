package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TodoList;

public class Show {

  private final TodoList todoList;
  private final PrintWriter out;

  public Show(TodoList todoList, PrintWriter out) {
    this.todoList = todoList;
    this.out = out;
  }

  public void show() {
    for (Project project : todoList.getProjects()) {
      out.println(project.getName());
      for (Task task : project.getTasks()) {
        out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(),
            task.getDescription());
      }
      out.println();
    }
  }
}