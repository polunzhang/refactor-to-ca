package tw.teddysoft.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.core.usecase.Input;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.spring.config.UseCaseInjection;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpUseCase;

@RestController
@AutoConfigureAfter({UseCaseInjection.class})
public class HelpController {
  private final HelpUseCase helpUseCase;

  @Autowired
  public HelpController(@Qualifier("webHelp") HelpUseCase helpUseCase) {
    this.helpUseCase = helpUseCase;
  }

  @GetMapping(path = "/help")
  public ResponseEntity<CqrsOutput> help() {
    try {
      var output = helpUseCase.execute(new Input.NullInput());
      if (output.getExitCode() == ExitCode.SUCCESS)
        return new ResponseEntity<>(output, HttpStatus.OK);
      return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      var output =
          CqrsOutput.create()
              .setExitCode(ExitCode.FAILURE)
              .setMessage("Cannot display help" + e.getMessage());
      return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
