package tw.teddysoft.tasks.entity;

import java.util.ArrayList;
import java.util.List;

public class TodoList {

  private final List<Project> projects;

  public TodoList() {
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
}