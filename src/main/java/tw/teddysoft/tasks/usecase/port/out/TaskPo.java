package tw.teddysoft.tasks.usecase.port.out;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskPo {
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "description")
  private String description;

  @Column(name = "done")
  private Boolean done;
}
