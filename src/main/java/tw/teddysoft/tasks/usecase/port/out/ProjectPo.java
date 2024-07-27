package tw.teddysoft.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "project")
@Data
public class ProjectPo implements Comparable<ProjectPo> {
  @Id
  @Column(name = "name")
  private String name;

  @Column(name = "project_order")
  private int order;
  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "id_fk")
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
