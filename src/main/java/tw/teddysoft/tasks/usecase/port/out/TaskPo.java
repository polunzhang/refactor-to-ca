package tw.teddysoft.tasks.usecase.port.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskPo {
  private String id;

  private String description;

  private Boolean done;
}
