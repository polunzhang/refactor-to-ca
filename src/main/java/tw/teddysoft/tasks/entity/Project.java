package tw.teddysoft.tasks.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import tw.teddysoft.ezddd.core.entity.Entity;

@Data
public class Project implements Entity<ProjectName> {

  private ProjectName name;

  private final List<Task> tasks;

  public Project(ProjectName name) {

    this.name = name;
    this.tasks = new ArrayList<>();
  }

  public Project(ProjectName name, List<Task> tasks) {
    this(name);
    this.tasks.addAll(tasks);
  }

  @Override
  public ProjectName getId() {
    return name;
  }

  public boolean containsTask(TaskId taskId) {
    return this.tasks.stream().anyMatch(task -> task.getId().equals(taskId));
  }

  public void setTaskDone(TaskId taskId, boolean done) {
    this.tasks.stream().filter(task -> task.getId().equals(taskId)).findFirst()
        .ifPresent(task -> task.setDone(done));
  }

  public void addTask(Task task) {
    this.tasks.add(task);
  }
}