package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import tw.teddysoft.tasks.TaskList;
import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.TodoList;

public class SetDone {

  private final TodoList todoList;
  private final PrintWriter out;

  public SetDone(TodoList todoList, PrintWriter out) {
    this.todoList = todoList;
    this.out = out;
  }

  public void setDone(String idString, boolean done) {
    var id = TaskId.of(Integer.parseInt(idString));
    for (Project project : todoList.getProjects()) {
      for (Task task : project.getTasks()) {
        if (task.getId().equals(id)) {
          todoList.setDone(id, done);
          return;
        }
      }
    }
    out.printf("Could not find a task with an ID of %s.", id);
    out.println();
  }
}