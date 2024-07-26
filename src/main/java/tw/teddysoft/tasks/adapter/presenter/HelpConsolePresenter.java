package tw.teddysoft.tasks.adapter.presenter;

import java.io.PrintWriter;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpDto;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpOutput;
import tw.teddysoft.tasks.usecase.port.out.HelpPresenter;

public class HelpConsolePresenter implements HelpPresenter {

  private final PrintWriter out;

  public HelpConsolePresenter(PrintWriter out) {
    this.out = out;
  }

  @Override
  public void present(HelpDto helpDto) {
    out.printf("%s\r\n",helpDto.header);
    for (String command : helpDto.commands) {
      out.printf("%s\r\n", command);
    }
    out.println();
  }
}
