package tw.teddysoft.tasks.usecase.port;

import java.util.List;
import java.util.stream.Collectors;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.usecase.TaskDto;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;

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

  public static Task toDomain(TaskPo taskPo) {
    return new Task(TaskId.of(taskPo.getId()), taskPo.getDescription(), taskPo.getDone());
  }

  public static List<Task> toDomain(List<TaskPo> taskPos) {
    return taskPos.stream().map(TaskMapper::toDomain).toList();
  }

  public static TaskPo toPo(Task task) {
    return new TaskPo(task.getId().value(), task.getDescription(), task.isDone());
  }

  public static List<TaskPo> toPo(List<Task> tasks) {
    return tasks.stream().map(TaskMapper::toPo).collect(Collectors.toList());
  }

}
