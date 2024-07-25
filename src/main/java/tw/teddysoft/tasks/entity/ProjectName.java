package tw.teddysoft.tasks.entity;


import tw.teddysoft.ezddd.core.entity.ValueObject;

public record ProjectName(String string) implements ValueObject {

  public static ProjectName of(String s) {
    return new ProjectName(s);
  }

  @Override
  public String toString() {
    return string;
  }

}
