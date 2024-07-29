package tw.teddysoft.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ToDoListPo {
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "last_task_id")
  private Long lastId;

  @OneToMany(
      cascade = {CascadeType.ALL},
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  @JoinColumn(name = "id_fk")
  @OrderBy("order")
  @Getter(AccessLevel.NONE)
  private Set<ProjectPo> projectPos;

  public ToDoListPo(String id, Long lastId) {
    this.projectPos = new HashSet<>();
    this.id = id;
    this.lastId = lastId;
  }

  public List<ProjectPo> getProjectPos() {
    return new ArrayList<>(projectPos);
  }

  public void setProjectPos(List<ProjectPo> projectPos) {
    this.projectPos = new LinkedHashSet<>(projectPos);
  }
}
