package tw.teddysoft.tasks.usecase.port;

import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.usecase.TodoListDto;

public class TodoListMapper {

  public static TodoListDto toDto(TodoList toDoList) {
    TodoListDto toDoListDto = new TodoListDto();
    toDoListDto.id = toDoList.getId().value();
    toDoListDto.projectDtos = ProjectMapper.toDto(toDoList.getProjects());
    return toDoListDto;
  }


}
