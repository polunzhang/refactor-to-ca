package tw.teddysoft.tasks.usecase.port;

import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.TodoListDto;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

public class TodoListMapper {

  public static TodoListDto toDto(TodoList toDoList) {
    TodoListDto toDoListDto = new TodoListDto();
    toDoListDto.id = toDoList.getId().value();
    toDoListDto.projectDtos = ProjectMapper.toDto(toDoList.getProjects());
    return toDoListDto;
  }

  public static TodoList toDomain(ToDoListPo toDoListPo) {
    return new TodoList(
        TodoListId.of(toDoListPo.getId()),
        ProjectMapper.toDomain(toDoListPo.getProjectPos()),
        toDoListPo.getLastId());
  }

  public static ToDoListPo toPo(TodoList toDoList) {
    ToDoListPo toDoListPo = new ToDoListPo(toDoList.getId().value(), toDoList.getTaskLastId());
    toDoListPo.setProjectPos(ProjectMapper.toPo(toDoList.getProjects()));
    return toDoListPo;
  }
}
