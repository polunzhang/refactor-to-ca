package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.Entity;

public final class ReadOnlyTask extends Task {

  public ReadOnlyTask(Task task) {
    super(task.getId(), task.getDescription(), task.isDone());
  }

  @Override
  public void setDone(boolean done) {
    throw new UnsupportedOperationException("Read only");
  }
}
