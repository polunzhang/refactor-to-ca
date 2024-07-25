package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.Entity;

public final class Task implements Entity<TaskId> {

  private final String description;
  private final TaskId taskId;
  private boolean done;

  public Task(TaskId id, String description, boolean done) {
    this.taskId = id;
    this.description = description;
    this.done = done;
  }

  public String getDescription() {
    return description;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  @Override
  public TaskId getId() {
    return taskId;
  }
}
