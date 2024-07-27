package tw.teddysoft.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToDoListPo {
  private String id;

  private Long lastId;

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
