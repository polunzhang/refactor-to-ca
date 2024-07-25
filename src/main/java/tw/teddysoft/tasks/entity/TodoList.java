package tw.teddysoft.tasks.entity;

import java.util.ArrayList;
import java.util.List;
import tw.teddysoft.ezddd.core.entity.AggregateRoot;
import tw.teddysoft.ezddd.core.entity.DomainEvent;

public class TodoList extends AggregateRoot<TodoListId, DomainEvent> {

  private final TodoListId id;
  private final List<Project> projects;

  public TodoList(TodoListId id) {
    this.id = id;
    this.projects = new ArrayList<>();
  }

  public void put(ProjectName name, List<Task> tasks) {
    this.projects.add(new Project(name, tasks));
  }

  public List<Task> get(ProjectName name) {
    return projects.stream().filter(e -> e.getName().equals(name)).findFirst()
        .map(Project::getTasks).orElse(null);
  }

  public List<Project> entrySet() {
    return projects;
  }

  @Override
  public TodoListId getId() {
    return id;
  }
}