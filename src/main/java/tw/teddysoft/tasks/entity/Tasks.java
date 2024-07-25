package tw.teddysoft.tasks.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tasks {

  private final List<Project> projects;

  public Tasks() {
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