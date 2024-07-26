package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpDto;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpOutput;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.out.HelpPresenter;

public class HelpService implements HelpUseCase {
  private final HelpPresenter presenter;

  public HelpService(HelpPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public HelpOutput execute(HelpInput input) throws UseCaseFailureException {
    HelpOutput helpOutput = new HelpOutput();
    HelpDto helpDto = new HelpDto();

    helpDto.header = "Commands:";
    helpDto.commands.add("  show");
    helpDto.commands.add("  add project <project name>");
    helpDto.commands.add("  add task <project name> <task description>");
    helpDto.commands.add("  check <task ID>");
    helpDto.commands.add("  uncheck <task ID>");
    helpOutput.helpDto = helpDto;

    presenter.present(helpDto);

    return helpOutput.succeed();
  }
}