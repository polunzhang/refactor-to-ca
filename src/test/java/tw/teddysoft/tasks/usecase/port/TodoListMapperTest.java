package tw.teddysoft.tasks.usecase.port;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.TodoListDto;
import tw.teddysoft.tasks.usecase.port.out.ProjectPo;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

class TodoListMapperTest {
  @Test
  public void toDto() {
    TodoListId toDoListId = new TodoListId("123456");
    TodoList toDoList = new TodoList(toDoListId, 5);
    ProjectName projectName = new ProjectName("Test");
    toDoList.addProject(projectName);
    toDoList.addProject(ProjectName.of("p2"));
    toDoList.addTask(projectName, "Read DDD", false);
    toDoList.addTask(projectName, "Read CA", false);
    toDoList.addTask(projectName, "Read Pattern", true);

    TodoListDto toDoListDto = TodoListMapper.toDto(toDoList);

    assertEquals(toDoList.getId().value(), toDoListDto.id);
    assertEquals(toDoList.getProjects().size(), toDoListDto.projectDtos.size());
    for (int j = 0; j < toDoList.getProjects().size(); j++) {
      for (int i = 0; i < toDoList.getProjects().get(j).getTasks().size(); i++) {
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).getId().value(),
            toDoListDto.projectDtos.get(j).taskDtos.get(i).id);
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).getDescription(),
            toDoListDto.projectDtos.get(j).taskDtos.get(i).description);
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).isDone(),
            toDoListDto.projectDtos.get(j).taskDtos.get(i).done);
      }
    }
  }

  @Test
  public void toDomain() {
    ToDoListPo toDoListPo = new ToDoListPo();
    toDoListPo.setId("testId");
    toDoListPo.setLastId(1L);

    ProjectPo projectPo = new ProjectPo();
    String name = "Test Project";
    int order = 0;
    projectPo.setName(name);
    projectPo.setOrder(order);
    List<TaskPo> taskPos = new ArrayList<>();
    taskPos.add(new TaskPo("1", "Study Refactoring", false));
    projectPo.setTaskPos(taskPos);
    List<ProjectPo> projectPos = new ArrayList<>();
    projectPos.add(projectPo);
    toDoListPo.setProjectPos(projectPos);

    TodoList toDoList = TodoListMapper.toDomain(toDoListPo);

    assertEquals("testId", toDoList.getId().value());
    assertEquals(1L, toDoList.getTaskLastId());
    assertEquals(toDoList.getProjects().size(), toDoListPo.getProjectPos().size());

    for (int j = 0; j < toDoList.getProjects().size(); j++) {
      for (int i = 0; i < toDoList.getProjects().get(j).getTasks().size(); i++) {
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).getId().value(),
            toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getId());
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).getDescription(),
            toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDescription());
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).isDone(),
            toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDone());
      }
    }
  }

  @Test
  public void toPo() {
    TodoListId toDoListId = new TodoListId("123456");
    TodoList toDoList = new TodoList(toDoListId, 5);
    ProjectName projectName = new ProjectName("Test");
    toDoList.addProject(projectName);
    toDoList.addProject(ProjectName.of("p2"));
    toDoList.addTask(projectName, "Read DDD", false);
    toDoList.addTask(projectName, "Read CA", false);
    toDoList.addTask(projectName, "Read Pattern", true);

    ToDoListPo toDoListPo = TodoListMapper.toPo(toDoList);

    assertEquals(toDoList.getId().value(), toDoListPo.getId());
    assertEquals(toDoList.getProjects().size(), toDoListPo.getProjectPos().size());
    for (int j = 0; j < toDoList.getProjects().size(); j++) {
      for (int i = 0; i < toDoList.getProjects().get(j).getTasks().size(); i++) {
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).getId().value(),
            toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getId());
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).getDescription(),
            toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDescription());
        assertEquals(
            toDoList.getProjects().get(j).getTasks().get(i).isDone(),
            toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDone());
      }
    }
  }
}
