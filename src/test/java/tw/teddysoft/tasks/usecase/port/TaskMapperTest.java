package tw.teddysoft.tasks.usecase.port;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.usecase.TaskDto;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;

class TaskMapperTest {

  @Test
  public void testToDtoMethod() {
    // Create an instance of a task
    Task task = new Task(TaskId.of("1"), "This is a test task.", false);

    // Convert the task instance to a TaskDto
    TaskDto taskDto = TaskMapper.toDto(task);

    // Assert that the taskDto's fields match the original task instance's fields
    assertEquals("1", taskDto.id);
    assertEquals("This is a test task.", taskDto.description);
    assertEquals(false, taskDto.done);
  }

  @Test
  void testToDomain() {
    // Arrange
    String id = "testId";
    String description = "test description";
    Boolean done = false;
    TaskPo taskPo = new TaskPo();
    taskPo.setId(id);
    taskPo.setDescription(description);
    taskPo.setDone(done);

    // Act
    Task task = TaskMapper.toDomain(taskPo);

    // Assert
    assertNotNull(task);
    assertEquals(id, task.getId().value());
    assertEquals(description, task.getDescription());
  }

  @Test
  public void givenTask_whenToPo_thenReturnTaskPo() {
    // Arrange
    TaskId taskId = TaskId.of("100");
    String description = "Test description";
    boolean isDone = false;
    Task task = new Task(taskId, description, isDone);

    // Act
    TaskPo result = TaskMapper.toPo(task);

    // Assert
    assertEquals(taskId.value(), result.getId());
    assertEquals(description, result.getDescription());
    assertEquals(isDone, result.getDone());
  }
}