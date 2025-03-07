package tw.teddysoft.tasks.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import tw.teddysoft.ezddd.core.entity.AggregateRoot;
import tw.teddysoft.ezddd.core.entity.DomainEvent;

public class TodoList extends AggregateRoot<TodoListId, DomainEvent> {

  private final TodoListId id;
  private final List<Project> projects;

  private long lastId;

  public TodoList(TodoListId id) {
    this(id, 0);
  }

  public TodoList(TodoListId id, long lastTaskId) {
    this.id = id;
    this.lastId = lastTaskId;
    this.projects = new ArrayList<>();
  }

  public TodoList(TodoListId id, long lastTaskId, List<Project> projects) {
    this(id, lastTaskId);
    this.projects.addAll(projects);
  }

  public List<Project> getProjects() {
    return projects.stream().map(ReadOnlyProject::new).collect(Collectors.toList());
  }

  public void addProject(ProjectName name) {
    if (projects.stream().anyMatch(e -> e.getName().equals(name))) {
      return;
    }
    this.projects.add(new Project(name));
  }

  public void addTask(ProjectName name, String description, boolean done) {
    getProject(name).ifPresent(e -> e.addTask(new Task(TaskId.of(taskId()), description, done)));
  }

  public Optional<Project> getProject(ProjectName name) {
    return this.projects.stream().filter(e -> e.getName().equals(name)).findFirst();
  }

  private long taskId() {
    return ++lastId;
  }

  public List<Task> getTasks(ProjectName name) {
    return projects.stream()
        .filter(e -> e.getName().equals(name))
        .findFirst()
        .map(e -> e.getTasks().stream().map(real -> (Task) new ReadOnlyTask(real)).toList())
        .orElse(null);
  }

  public boolean containsTask(TaskId taskId) {
    return this.projects.stream().anyMatch(e -> e.containsTask(taskId));
  }

  public void setDone(TaskId taskId, boolean done) {
    this.projects.stream()
        .filter(e -> e.containsTask(taskId))
        .findFirst()
        .ifPresent(e -> e.setTaskDone(taskId, done));
  }

  public long getTaskLastId() {
    return lastId;
  }

  @Override
  public TodoListId getId() {
    return id;
  }
}
