package tw.teddysoft.tasks.usecase.service;

import static java.lang.String.format;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.in.todoList.error.ErrorInput;
import tw.teddysoft.tasks.usecase.port.in.todoList.error.ErrorUseCase;

public class ErrorService implements ErrorUseCase {

  @Override
  public CqrsOutput execute(ErrorInput input) throws UseCaseFailureException {
    StringBuilder sb = new StringBuilder();
    sb.append(format("I don't know what the command \"%s\" is.", input.command));
    sb.append("\r\n");
    return CqrsOutput.create().fail().setMessage(sb.toString());
  }
}