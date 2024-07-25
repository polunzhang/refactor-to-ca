package tw.teddysoft.tasks.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class Project {

  private ProjectName name;

  private final List<Task> tasks;

  public Project() {
    this.tasks = new ArrayList<>();
  }

  public Project(ProjectName name, List<Task> tasks) {
    this();
    this.name = name;
    tasks.addAll(tasks);
  }

}