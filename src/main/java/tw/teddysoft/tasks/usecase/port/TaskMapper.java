package tw.teddysoft.tasks.usecase.port;

import java.util.List;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.usecase.TaskDto;

public class TaskMapper {

  public static TaskDto toDto(Task task) {
    TaskDto taskDto = new TaskDto();
    taskDto.id = task.getId().value();
    taskDto.description = task.getDescription();
    taskDto.done = task.isDone();
    return taskDto;
  }

  public static List<TaskDto> toDto(List<Task> tasks) {
    return tasks.stream().map(TaskMapper::toDto).toList();
  }
}
