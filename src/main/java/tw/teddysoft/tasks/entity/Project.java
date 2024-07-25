package tw.teddysoft.tasks.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import tw.teddysoft.ezddd.core.entity.Entity;

@Data
public class Project implements Entity<ProjectName> {

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

  @Override
  public ProjectName getId() {
    return name;
  }
}