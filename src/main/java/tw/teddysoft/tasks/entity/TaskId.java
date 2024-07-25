package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record TaskId(String value) implements ValueObject {

  @Override
  public String toString() {
    return value;
  }

  public static TaskId of(long value) {
    return new TaskId(String.valueOf(value));
  }

  public static TaskId of(String value) {
    return new TaskId(value);
  }

}