package tw.teddysoft.tasks.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import tw.teddysoft.tasks.entity.Task;

public class Tasks {

  private final Map<ProjectName, List<Task>> tasks;

  public Tasks() {
    this.tasks = new LinkedHashMap<>();
  }

  public void put(ProjectName name, List<Task> tasks) {
    this.tasks.put(name, tasks);
  }

  public List<Task> get(ProjectName name) {
    return tasks.get(name);
  }

  public Set<Map.Entry<ProjectName, List<Task>>> entrySet() {
    return tasks.entrySet();
  }
}