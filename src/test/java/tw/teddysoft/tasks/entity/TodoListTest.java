package tw.teddysoft.tasks.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TodoListTest {

  @Test
  public void add_a_project_with_duplicated_name_has_no_effect(){
    TodoList toDoList = new TodoList(TodoListId.of("001"));
    toDoList.addProject(ProjectName.of("p1"));
    assertEquals(1, toDoList.getProjects().size());
    toDoList.addProject(ProjectName.of("p1"));
    assertEquals(1, toDoList.getProjects().size());
  }
}