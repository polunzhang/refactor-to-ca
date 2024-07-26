package tw.teddysoft.tasks.usecase.in.task.set_down;

import tw.teddysoft.ezddd.core.usecase.Input;
import tw.teddysoft.tasks.entity.TaskId;

public class SetDownInput implements Input {
  public String todoListId;
  public String taskId;
  public boolean isDone;
}
