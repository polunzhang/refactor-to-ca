package tw.teddysoft.tasks.adapter.presenter;

import java.io.PrintWriter;
import tw.teddysoft.tasks.usecase.ProjectDto;
import tw.teddysoft.tasks.usecase.TaskDto;
import tw.teddysoft.tasks.usecase.TodoListDto;
import tw.teddysoft.tasks.usecase.port.out.ShowPresenter;

public class ShowConsolePresenter implements ShowPresenter {

  private final PrintWriter out;

  public ShowConsolePresenter(PrintWriter out) {
    this.out = out;
  }

  @Override
  public void present(TodoListDto doListDto) {
    for (ProjectDto project : doListDto.projectDtos) {
      out.println(project.name);
      for (TaskDto task : project.taskDtos) {
        out.printf("    [%c] %s: %s%n", (task.done ? 'x' : ' '), task.id, task.description);
      }
      out.println();
    }
  }
}
