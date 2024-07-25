package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;
import tw.teddysoft.tasks.TaskList;

public class Help {

  private final PrintWriter out;

  public Help(PrintWriter out) {
    this.out = out;
  }

  public void help() {
    out.println("Commands:");
    out.println("  show");
    out.println("  add project <project name>");
    out.println("  add task <project name> <task description>");
    out.println("  check <task ID>");
    out.println("  uncheck <task ID>");
    out.println();
  }
}