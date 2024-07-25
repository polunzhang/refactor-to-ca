package tw.teddysoft.tasks.entity;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ReadOnlyProject extends Project {

  public ReadOnlyProject(Project project) {
    super(project.getName(), project.getTasks());
  }

  @Override
  public List<Task> getTasks() {
    return super.getTasks().stream().map(ReadOnlyTask::new).collect(Collectors.toList());
  }

  @Override
  public void setTaskDone(TaskId taskId, boolean done) {
    throw new UnsupportedOperationException("Read only");
  }

  @Override
  public void addTask(Task task) {
    throw new UnsupportedOperationException("Read only");
  }
}