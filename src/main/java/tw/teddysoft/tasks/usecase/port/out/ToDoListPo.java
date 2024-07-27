package tw.teddysoft.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class ToDoListPo {
  private String id;

  private Long lastId;

  @Getter(AccessLevel.NONE)
  private Set<ProjectPo> projectPos;

  public List<ProjectPo> getProjectPos() {
    return new ArrayList<>(projectPos);
  }

  public void setProjectPos(Set<ProjectPo> projectPos) {
    this.projectPos = new HashSet<>(projectPos);
  }
}
