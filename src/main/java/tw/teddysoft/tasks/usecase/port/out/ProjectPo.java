package tw.teddysoft.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ProjectPo implements Comparable<ProjectPo> {
  private String name;

  private int order;

  private List<TaskPo> taskPos;

  public ProjectPo() {
    this.taskPos = new ArrayList<>();
  }

  public ProjectPo(String name, int order) {
    this();
    this.name = name;
    this.order = order;
  }

  public List<TaskPo> getTaskPos() {
    return new ArrayList<>(taskPos);
  }

  public void setTaskPos(List<TaskPo> taskPos) {
    this.taskPos = new ArrayList<>(taskPos);
  }

  @Override
  public int compareTo(@NotNull ProjectPo that) {
    return this.getOrder() - that.getOrder();
  }
}
