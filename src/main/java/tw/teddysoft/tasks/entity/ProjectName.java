package tw.teddysoft.tasks.entity;


public record ProjectName(String string) {

  public static ProjectName of(String s) {
    return new ProjectName(s);
  }

  @Override
  public String toString() {
    return string;
  }

}
