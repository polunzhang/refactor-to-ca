package tw.teddysoft.tasks.usecase;

import java.io.PrintWriter;

public class Error {

  private final PrintWriter out;

  public Error(PrintWriter out) {
    this.out = out;
  }


  public void error(String command) {
    out.printf("I don't know what the command \"%s\" is.", command);
    out.println();
  }

}